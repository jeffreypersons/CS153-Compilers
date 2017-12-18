/**
 * <h1>CallStandardExecutor</h1>
 *
 * <p>Execute a call a standard procedure or function.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#include <iostream>
#include <vector>
#include <math.h>
#include <algorithm>
#include "CallStandardExecutor.h"
#include "CallExecutor.h"
#include "StatementExecutor.h"
#include "AssignmentExecutor.h"
#include "ExpressionExecutor.h"
#include "../Cell.h"
#include "../MemoryFactory.h"
#include "../../BackendFactory.h"
#include "../../../frontend/Token.h"
#include "../../../frontend/pascal/PascalToken.h"
#include "../../../intermediate/ICodeNode.h"
#include "../../../intermediate/SymTabEntry.h"
#include "../../../intermediate/symtabimpl/SymTabEntryImpl.h"
#include "../../../intermediate/symtabimpl/Predefined.h"
#include "../../../intermediate/icodeimpl/ICodeNodeImpl.h"

namespace wci { namespace backend { namespace interpreter { namespace executors {

using namespace std;
using namespace wci;
using namespace wci::frontend;
using namespace wci::frontend::pascal;
using namespace wci::intermediate;
using namespace wci::intermediate::symtabimpl;
using namespace wci::intermediate::icodeimpl;
using namespace wci::backend::interpreter;

CallStandardExecutor::CallStandardExecutor(Executor *parent)
    : CallExecutor(parent)
{
}

CellValue *CallStandardExecutor::execute(ICodeNode *node)
{
    NodeValue *node_value = node->get_attribute((ICodeKey) ID);
    SymTabEntry *routine_id = node_value->id;
    EntryValue *entry_value =
                    routine_id->get_attribute((SymTabKey) ROUTINE_CODE);
    RoutineCode routine_code = entry_value->routine_code;
    TypeSpec *typespec = node->get_typespec();
    ExpressionExecutor expression_executor(this);
    ICodeNode *actual_node = nullptr;

    // Get the actual parameters of the call.
    if (node->get_children().size() > 0)
    {
        ICodeNode *parms_node = node->get_children()[0];
        actual_node = parms_node->get_children()[0];
    }

    switch ((RoutineCodeImpl) routine_code)
    {
        case RT_READ:
        case RT_READLN:  return execute_read_readln(node, routine_code);

        case RT_WRITE:
        case RT_WRITELN: return execute_write_writeln(node, routine_code);

        case RT_EOF:
        case RT_EOLN:    return execute_eof_eoln(node, routine_code);

        case RT_ABS:
        case RT_SQR:     return execute_abs_sqr(node, routine_code,
                                                actual_node);

        case RT_ARCTAN:
        case RT_COS:
        case RT_EXP:
        case RT_LN:
        case RT_SIN:
        case RT_SQRT:    return execute_arctan_cos_exp_ln_sin_sqrt(
                                        node, routine_code, actual_node);

        case RT_PRED:
        case RT_SUCC:    return execute_pred_succ(
                              node, routine_code, actual_node, typespec);

        case RT_CHR: return execute_chr(node, routine_code, actual_node);
        case RT_ODD: return execute_odd(node, routine_code, actual_node);
        case RT_ORD: return execute_ord(node, routine_code, actual_node);

        case RT_ROUND:
        case RT_TRUNC:   return execute_round_trunc(node, routine_code,
                                                    actual_node);

        default:      return nullptr;  // should never get here
    }
}

CellValue *CallStandardExecutor::execute_read_readln(
                        ICodeNode *call_node, RoutineCode routine_code)
{
    ICodeNode *parms_node = call_node->get_children().size() > 0
                              ? call_node->get_children()[0]
                              : nullptr;
    ExpressionExecutor expression_executor(this);

    if (parms_node != nullptr)
    {
        vector<ICodeNode *> actuals = parms_node->get_children();

        // Loop to process each actual parameter.
        for (ICodeNode *actual_node : actuals)
        {
            TypeSpec *typespec = actual_node->get_typespec();
            TypeSpec *base_type = typespec->base_type();
            Cell *variable_cell =
                    expression_executor.execute_variable(actual_node);
            NodeValue *node_value = actual_node->get_attribute((ICodeKey) ID);
            SymTabEntry *actual_id = node_value->id;
            CellValue *cell_value;
            Token *token;

            // Read a value of the appropriate typespec from the standard input.
            try
            {
                if (base_type == Predefined::integer_type)
                {
                    token = std_in->next_token(nullptr);
                    cell_value = parse_number(token, base_type);
                }
                else if (base_type == Predefined::real_type)
                {
                    token = std_in->next_token(nullptr);
                    cell_value = parse_number(token, base_type);
                }
                else if (base_type == Predefined::boolean_type)
                {
                    token = std_in->next_token(nullptr);
                    cell_value = parse_boolean(token);
                }
                else if (base_type == Predefined::char_type)
                {
                    char ch = std_in->next_char();
                    if (   (ch == Source::END_OF_LINE)
                        || (ch == Source::END_OF_FILE)) ch = ' ';
                    cell_value = new CellValue(ch);
                }
                else throw string("");
            }
            catch (string& str)
            {
                error_handler.flag(call_node, INVALID_INPUT, this);
                cell_value = BackendFactory::default_value(typespec);
            }

            AssignmentExecutor assignment_executor(this);
            assignment_executor.assign_value(call_node, actual_id, variable_cell,
                                             typespec, cell_value, base_type);
        }
    }

    // Skip the rest of the input line for readln.
    if (routine_code == (RoutineCode) RT_READLN)
    {
        try
        {
            std_in->skip_to_next_line();
        }
        catch (string& str)
        {
            error_handler.flag(call_node, INVALID_INPUT, this);
        }
    }

    return nullptr;
}

CellValue *CallStandardExecutor::parse_number(Token *token,
                                              TypeSpec *typespec)
    throw (string)
{
    TokenType token_type = token->get_type();
    bool plus_sign    = token_type == (TokenType) PT_PLUS;
    bool minus_sign   = token_type == (TokenType) PT_MINUS;
    bool leading_sign = plus_sign || minus_sign;

    // Leading sign?
    if (leading_sign)
    {
        token = std_in->next_token(token);  // consume sign
        token_type = token->get_type();
    }

    // Integer value.
    if (token_type == (TokenType) PT_INTEGER)
    {
        int i = token->get_value()->i;
        if (minus_sign) i = -i;

        delete token;
        return typespec == Predefined::integer_type
                                            ? new CellValue(i)
                                            : new CellValue((float) i);
    }

    // Real value.
    else if (   (token_type == (TokenType) PT_REAL)
             && (typespec == Predefined::real_type))
    {
        float f = token->get_value()->f;
        if (minus_sign) f = -f;

        delete token;
        return new CellValue(f);
    }

    // Bad input.
    else
    {
        delete token;
        throw string("");
    }
}

CellValue *CallStandardExecutor::parse_boolean(Token *token) throw (string)
{
    if (token->get_type() == (TokenType) PT_IDENTIFIER)
    {
        string text = token->get_text();
        transform(text.begin(), text.end(), text.begin(), ::tolower);
        delete token;

        if      (text == "true")  return new CellValue(true);
        else if (text == "false") return new CellValue(false);
        else throw string("");
    }
    else throw string("");
}

CellValue *CallStandardExecutor::execute_write_writeln(
                        ICodeNode *call_node, RoutineCode routine_code)
{
    ICodeNode *parms_node = call_node->get_children().size() > 0
                                          ? call_node->get_children()[0]
                                          : nullptr;
    ExpressionExecutor expression_executor(this);

    if (parms_node != nullptr)
    {
        vector<ICodeNode *> actuals = parms_node->get_children();

        // Loop to process each WRITE_PARM actual parameter node.
        for (ICodeNode *write_parm_node : actuals)
        {
            vector<ICodeNode *> children = write_parm_node->get_children();
            ICodeNode *expr_node = children[0];
            TypeSpec *typespec = expr_node->get_typespec()->base_type();
            string type_code = typespec->is_pascal_string()    ? "s"
                        : typespec == Predefined::integer_type ? "d"
                        : typespec == Predefined::real_type    ? "f"
                        : typespec == Predefined::boolean_type ? "s"
                        : typespec == Predefined::char_type    ? "c"
                        :                                        "s";

            CellValue *cell_value = expression_executor.execute(expr_node);

            if (   (typespec == Predefined::char_type)
                && (cell_value->value->type == STRING))
            {
                DataValue *value = cell_value->value;
                char ch = value->s[0];
                value->type = CHAR;
                value->c = ch;
            }

            // C++ format string.
            string format = "%";

            // Process any field width and precision values.
            if (children.size() > 1)
            {
                int w =
                    children[1]->get_attribute((ICodeKey) VALUE)->value->i;
                format += to_string(w == 0 ? 1 : w);
            }
            if (children.size() > 2)
            {
                int p =
                    children[2]->get_attribute((ICodeKey) VALUE)->value->i;
                format += "." + to_string(p == 0 ? 1 : p);
            }

            format += type_code;

            // Write the formatted value to the standard output.
            DataValue *value = cell_value->value;
            switch (type_code[0])
            {
                case 'd': printf(format.c_str(), value->i); break;
                case 'f': printf(format.c_str(), value->f); break;
                case 'c': printf(format.c_str(), value->c); break;

                case 's':
                {
                    string str;

                    if (typespec == Predefined::boolean_type)
                    {
                        str = value->b ? "true" : "false";
                    }
                    else
                    {
                        str = value->s;
                    }

                    printf(format.c_str(), str.c_str());
                    break;
                }
            }

            cout.flush();
            delete cell_value;
        }
    }

    // Line feed for writeln.
    if (routine_code == (RoutineCode) RT_WRITELN)
    {
        cout << endl;
        cout.flush();
    }

    return nullptr;
}

CellValue *CallStandardExecutor::execute_eof_eoln(
                        ICodeNode *call_node, RoutineCode routine_code)
{
    try
    {
        return new CellValue(routine_code == (RoutineCode) RT_EOF
                                    ? std_in->at_eof()
                                    : std_in->at_eol());
    }
    catch (string& str)
    {
        error_handler.flag(call_node, INVALID_INPUT, this);
        return new CellValue(true);
    }
}

CellValue *CallStandardExecutor::execute_abs_sqr(ICodeNode *call_node,
                               RoutineCode routine_code,
                               ICodeNode *actual_node)
{
    ExpressionExecutor expression_executor(this);
    CellValue *arg_cell_value = expression_executor.execute(actual_node);

    if (arg_cell_value->value->type == INTEGER)
    {
        int i = arg_cell_value->value->i;
        i = routine_code == (RoutineCode) RT_ABS ? abs(i) : i*i;
        arg_cell_value->value->i = i;
        return arg_cell_value;
    }
    else
    {
        float f = arg_cell_value->value->f;
        f = routine_code == (RoutineCode) RT_ABS ? (float) fabs(f) : f*f;
        arg_cell_value->value->f = f;
    }

    return arg_cell_value;
}

CellValue *CallStandardExecutor::execute_arctan_cos_exp_ln_sin_sqrt(
                                                ICodeNode *call_node,
                                                RoutineCode routine_code,
                                                ICodeNode *actual_node)
{
    ExpressionExecutor expression_executor(this);
    CellValue *arg_cell_value = expression_executor.execute(actual_node);
    float x = arg_cell_value->value->type == INTEGER
                                    ? (float) arg_cell_value->value->i
                                    :         arg_cell_value->value->f;

    float z;
    switch ((RoutineCodeImpl) routine_code)
    {
        case RT_ARCTAN: z = tan(x); break;
        case RT_COS:    z = cos(x); break;
        case RT_EXP:    z = exp(x); break;
        case RT_SIN:    z = sin(x); break;

        case RT_LN:
        {
            if (x > 0.0f) z = log(x);
            else
            {
                error_handler.flag(call_node,
                                   INVALID_STANDARD_FUNCTION_ARGUMENT,
                                   this);
                z = 0.0f;
            }
            break;
        }

        case RT_SQRT:
        {
            if (x >= 0.0f) z = sqrt(x);
            else
            {
                error_handler.flag(call_node,
                                   INVALID_STANDARD_FUNCTION_ARGUMENT,
                                   this);
                z = 0.0f;
            }
            break;
        }

        default: z = 0.0f;  // should never get here
    }

    arg_cell_value->value->type = FLOAT;
    arg_cell_value->value->f = x;
    return arg_cell_value;
}

CellValue *CallStandardExecutor::execute_pred_succ(ICodeNode *call_node,
                                 RoutineCode routine_code,
                                 ICodeNode *actual_node,
                                 TypeSpec *typespec)
{
    ExpressionExecutor expression_executor(this);
    CellValue *arg_cell_value = expression_executor.execute(actual_node);
    int i = arg_cell_value->value->i;
    i = routine_code == (RoutineCode) RT_PRED ? i - 1 : 1 + 1;
    arg_cell_value->value->i = i;

    arg_cell_value = check_range(call_node, typespec, arg_cell_value);
    return arg_cell_value;
}

CellValue *CallStandardExecutor::execute_chr(ICodeNode *call_node,
                                             RoutineCode routine_code,
                                             ICodeNode *actual_node)
{
    ExpressionExecutor expression_executor(this);
    CellValue *arg_cell_value = expression_executor.execute(actual_node);
    char ch = (char) arg_cell_value->value->i;

    arg_cell_value->value->type = CHAR;
    arg_cell_value->value->c = ch;
    return arg_cell_value;
}

CellValue *CallStandardExecutor::execute_odd(ICodeNode *call_node,
                                             RoutineCode routine_code,
                                             ICodeNode *actual_node)
{
    ExpressionExecutor expression_executor(this);
    CellValue *arg_cell_value = expression_executor.execute(actual_node);
    bool odd = (arg_cell_value->value->i & 1) == 1;

    arg_cell_value->value->type = BOOLEAN;
    arg_cell_value->value->b = odd;
    return arg_cell_value;
}

CellValue *CallStandardExecutor::execute_ord(ICodeNode *call_node,
                                             RoutineCode routine_code,
                                             ICodeNode *actual_node)
{
    ExpressionExecutor expression_executor(this);
    CellValue *arg_cell_value = expression_executor.execute(actual_node);

    if (arg_cell_value->value->type == CHAR)
    {
        char ch = arg_cell_value->value->c;
        arg_cell_value->value->type = INTEGER;
        arg_cell_value->value->i = (int) ch;
        return arg_cell_value;
    }
    else if (arg_cell_value->value->type == STRING)
    {
        char ch = arg_cell_value->value->s[0];
        arg_cell_value->value->type = INTEGER;
        arg_cell_value->value->i = (int) ch;
        return arg_cell_value;
    }
    else
    {
        return arg_cell_value;
    }
}

CellValue *CallStandardExecutor::execute_round_trunc(
                                                ICodeNode *call_node,
                                                RoutineCode routine_code,
                                                ICodeNode *actual_node)
{
    ExpressionExecutor expression_executor(this);
    CellValue *arg_cell_value = expression_executor.execute(actual_node);
    float f = arg_cell_value->value->f;

    if (routine_code == (RoutineCode) RT_ROUND)
    {
        int i = f >= 0.0f ? (int) (f + 0.5f) : (int) (f - 0.5f);
        arg_cell_value->value->type = INTEGER;
        arg_cell_value->value->i = i;
    }
    else
    {
        int i = (int) f;
        arg_cell_value->value->type = INTEGER;
        arg_cell_value->value->i = i;
    }

    return arg_cell_value;
}

}}}}  // namespace wci::backend::interpreter::executors
