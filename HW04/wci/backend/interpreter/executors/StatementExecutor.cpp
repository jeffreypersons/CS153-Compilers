/**
 * <h1>StatementExecutor</h1>
 *
 * <p>Execute a statement.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#include "StatementExecutor.h"
#include "CompoundExecutor.h"
#include "AssignmentExecutor.h"
#include "LoopExecutor.h"
#include "IfExecutor.h"
#include "SelectExecutor.h"
#include "CallExecutor.h"
#include "../Executor.h"
#include "../Cell.h"
#include "../MemoryFactory.h"
#include "../RuntimeError.h"
#include "../memoryimpl/MemoryMapImpl.h"
#include "../../../DataValue.h"
#include "../../../intermediate/TypeSpec.h"
#include "../../../intermediate/ICodeNode.h"
#include "../../../intermediate/symtabimpl/Predefined.h"
#include "../../../intermediate/icodeimpl/ICodeNodeImpl.h"
#include "../../../intermediate/typeimpl/TypeSpecImpl.h"
#include "../../../message/Message.h"

namespace wci { namespace backend { namespace interpreter { namespace executors {

using namespace std;
using namespace wci;
using namespace wci::backend::interpreter;
using namespace wci::backend::interpreter::memoryimpl;
using namespace wci::intermediate;
using namespace wci::intermediate::symtabimpl;
using namespace wci::intermediate::icodeimpl;
using namespace wci::intermediate::typeimpl;
using namespace wci::message;

StatementExecutor::StatementExecutor(Executor *parent)
    : parent(parent)
{
}

CellValue *StatementExecutor::execute(ICodeNode *node)
{
    ICodeNodeTypeImpl node_type = (ICodeNodeTypeImpl) node->get_type();

    // Send a message about the current source line.
    send_at_line_message(node);

    switch (node_type)
    {
        case NT_COMPOUND:
        {
            CompoundExecutor compound_executor(this);
            return compound_executor.execute(node);
        }

        case NT_ASSIGN:
        {
            AssignmentExecutor assignment_executor(this);
            return assignment_executor.execute(node);
        }

        case NT_LOOP:
        {
            LoopExecutor loop_executor(this);
            return loop_executor.execute(node);
        }

        case NT_IF:
        {
            IfExecutor if_executor(this);
            return if_executor.execute(node);
        }

        case NT_SELECT:
        {
            SelectExecutor select_executor(this);
            return select_executor.execute(node);
        }

        case NT_CALL:
        {
            CallExecutor call_executor(this);
            return call_executor.execute(node);
        }

        case NT_NO_OP: return nullptr;

        default:
        {
            error_handler.flag(node, UNIMPLEMENTED_FEATURE, this);
            return nullptr;
        }
    }
}

CellValue *StatementExecutor::to_pascal(TypeSpec *target_typespec,
                                        CellValue *java_value)
{
    DataValue *value = java_value->value;

    if (value == nullptr) return java_value;

    if (value->type == STRING)
    {
        if (target_typespec == Predefined::char_type)
        {
            // Pascal character.
            char ch = value->s[0];
            value->type = CHAR;
            value->c = ch;
        }
        else if (target_typespec->is_pascal_string())
        {
            int length = value->s.length();
            Cell **char_cells = new Cell*[length];

            // Build an array of characters.
            for (int i = 0; i < length; ++i)
            {
                char ch = java_value->value->s[i];
                DataValue *value = new DataValue(ch);
                char_cells[i] =
                    MemoryFactory::create_cell(new CellValue(value));
            }

            // Pascal string (array of characters).
            java_value->cell_array = char_cells;
            value->type = INTEGER;
            value->i = length;
        }
    }

    return java_value;
}

CellValue *StatementExecutor::to_java(TypeSpec *target_typespec,
                                      CellValue *pascal_value,
                                      ICodeNode *node)
{
    if (   (pascal_value->cell_array != nullptr)
        && (pascal_value->cell_array[0]->get_value() != nullptr)
        && (pascal_value->cell_array[0]->get_value()->value != nullptr)
        && (pascal_value->cell_array[0]->get_value()->value->type == CHAR))
    {
        DataValue *value = pascal_value->value;
        string str = "";
        int length = value->i;

        // Build a Java string.
        for (int i = 0; i < length; i++)
        {
            str += pascal_value->cell_array[i]->get_value()->value->c;
        }

        return(new CellValue(str));
    }

    return copy_of(pascal_value, node);
}

CellValue *StatementExecutor::copy_of(CellValue *cell_value, ICodeNode *node)
{
    if (cell_value->cell_array != nullptr)
    {
        return copy_array(cell_value, node);
    }
    else if (cell_value->memory_map != nullptr)
    {
        return copy_record(cell_value, node);
    }
    else
    {
        return new CellValue(cell_value->value);
    }
}

CellValue *StatementExecutor::copy_array(CellValue *cell_value,
                                         ICodeNode *node)
{
    int length = cell_value->value->i;
    Cell **copy_cells = new Cell*[length];

    for (int i = 0; i < length; ++i)
    {
        CellValue *copy_value =
            copy_of(cell_value->cell_array[i]->get_value(), node);
        copy_cells[i] = MemoryFactory::create_cell(copy_value);
    }

    return new CellValue(copy_cells, length);
}

CellValue *StatementExecutor::copy_record(CellValue *record_value,
                                          ICodeNode *node)
{
    MemoryMapImpl *copy_map = new MemoryMapImpl();

    if (   (record_value != nullptr)
        && (record_value->memory_map != nullptr))
    {
        MemoryMapImpl *orig_map =
                            (MemoryMapImpl *) record_value->memory_map;
        map<string, Cell *> *orig_contents = orig_map->get_contents();
        map<string, Cell *> *copy_contents = copy_map->get_contents();
        map<string, Cell *>::iterator it;

        for (it = orig_contents->begin(); it != orig_contents->end(); it++)
        {
            CellValue *copy_value = copy_of(it->second->get_value(), node);
            Cell *copy_cell = MemoryFactory::create_cell(copy_value);
            pair<string, Cell *> pair(it->first, copy_cell);
            copy_contents->insert(pair);
        }
    }
    else
    {
        error_handler.flag(node, UNINITIALIZED_VALUE, this);
    }

    return new CellValue(copy_map);
}

CellValue *StatementExecutor::check_range(
             ICodeNode *node, TypeSpec *typespec, CellValue *cell_value)
{
    if (typespec->get_form() == (TypeForm) TF_SUBRANGE)
    {
        TypeValue *type_value =
                   typespec->get_attribute((TypeKey) SUBRANGE_MIN_VALUE);
        int min_value = type_value->value->i;
        type_value =
                   typespec->get_attribute((TypeKey) SUBRANGE_MAX_VALUE);
        int max_value = type_value->value->i;

        if (cell_value->value->i < min_value)
        {
            error_handler.flag(node, VALUE_RANGE, this);
            cell_value->value->i = min_value;
        }
        else if (cell_value->value->i > max_value)
        {
            error_handler.flag(node, VALUE_RANGE, this);
            cell_value->value->i = max_value;
        }
    }

    return cell_value;
}

string StatementExecutor::display_value(const CellValue *cell_value) const
{
    string value_str =
          cell_value             == nullptr ? "<?>"
        : cell_value->value      != nullptr ? cell_value->value->display()
        : cell_value->cell       != nullptr ? "<reference>"
        : cell_value->cell_array != nullptr ? "<array>"
        : cell_value->memory_map != nullptr ? "<record>"
        :                                     "<?>";

    if (   (cell_value != nullptr)
        && (cell_value->value != nullptr))
    {
        if (cell_value->value->type == CHAR)
        {
            value_str = "'" + value_str + "'";
        }
        else if (cell_value->value->type == STRING)
        {
            value_str = "\"" + value_str + "\"";
        }
    }

    return value_str;
}

void StatementExecutor::send_at_line_message(ICodeNode *node)
{
    int line_number =
            node->get_attribute((ICodeKey) LINE_NUMBER)->value->i;

    // Send the SOURCE_LINE message.
    Message message(AT_LINE,
                    LINE_NUMBER, to_string(line_number));
    send_message(message);
}

void StatementExecutor::send_assign_message(ICodeNode *node,
                                            string variable_name,
                                            CellValue *cell_value)
{
    int line_number = get_line_number(node);

    // Send an ASSIGN message.
    string value_str = display_value(cell_value);
    Message message(ASSIGN,
                    LINE_NUMBER, to_string(line_number),
                    VARIABLE_NAME, variable_name,
                    RESULT_VALUE, value_str);
    send_message(message);
}

void StatementExecutor::send_fetch_message(ICodeNode *node,
                                           string variable_name,
                                           CellValue *cell_value)
{
    int line_number = get_line_number(node);

    // Send a FETCH message.
    string value_str = display_value(cell_value);
    Message message(FETCH,
                    LINE_NUMBER, to_string(line_number),
                    VARIABLE_NAME, variable_name,
                    RESULT_VALUE, value_str);
    send_message(message);
}

void StatementExecutor::send_call_message(ICodeNode *node,
                                          string routine_name)
{
    int line_number = get_line_number(node);

    // Send a CALL message.
    Message message(CALL,
                    LINE_NUMBER, to_string(line_number),
                    VARIABLE_NAME, routine_name);
    send_message(message);
}

void StatementExecutor::send_return_message(ICodeNode *node,
                                            string routine_name)
{
    int line_number = get_line_number(node);

    // Send a RETURN message.
    Message message(RETURN,
                    LINE_NUMBER, to_string(line_number),
                    VARIABLE_NAME, routine_name);
    send_message(message);
}

int StatementExecutor::get_line_number(ICodeNode *node)
{
    NodeValue *node_value;

    // Go up the parent links to look for a line number.
    while ((node != nullptr) &&
           ((node_value = node->get_attribute((ICodeKey) LINE)) == nullptr))
    {
        node = node->get_parent();
    }

    return node->get_attribute((ICodeKey) LINE)->value->i;
}

}}}}  // namespace wci::backend::interpreter::executors
