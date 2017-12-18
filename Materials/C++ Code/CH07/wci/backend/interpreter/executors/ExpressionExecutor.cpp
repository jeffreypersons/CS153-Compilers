/**
 * <h1>AssignmentExecutor</h1>
 *
 * <p>Execute an assignment statement.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#include <string>
#include <vector>
#include <set>
#include "ExpressionExecutor.h"
#include "StatementExecutor.h"
#include "../RuntimeError.h"
#include "../../../DataValue.h"
#include "../../../intermediate/ICodeNode.h"
#include "../../../intermediate/icodeimpl/ICodeNodeImpl.h"
#include "../../../intermediate/symtabimpl/SymTabEntryImpl.h"
#include "../../../message/Message.h"

namespace wci { namespace backend { namespace interpreter { namespace executors {

using namespace std;
using namespace wci;
using namespace wci::backend::interpreter;
using namespace wci::intermediate;
using namespace wci::intermediate::symtabimpl;
using namespace wci::intermediate::icodeimpl;
using namespace wci::message;

set<ICodeNodeTypeImpl> ExpressionExecutor::ARITH_OPS =
{
    NT_ADD, NT_SUBTRACT, NT_MULTIPLY,
    NT_FLOAT_DIVIDE, NT_INTEGER_DIVIDE, NT_MOD,
};

ExpressionExecutor::ExpressionExecutor(Executor *parent)
    : StatementExecutor(parent)
{
}

DataValue *ExpressionExecutor::execute(ICodeNode *node)
{
    ICodeNodeTypeImpl node_type = (ICodeNodeTypeImpl) node->get_type();

    switch (node_type)
    {
        case NT_VARIABLE:
        {
            // Get the variable's symbol table entry.
            NodeValue *node_value =
                         node->get_attribute((ICodeKey) ID);
            SymTabEntry *id = node_value->id;

            // Return the variable's value.
            EntryValue *entry_value =
                               id->get_attribute((SymTabKey) DATA_VALUE);
            DataValue *data_value = entry_value->value;
            return data_value;
        }

        case NT_INTEGER_CONSTANT:
        case NT_REAL_CONSTANT:
        case NT_STRING_CONSTANT:
        {
            // Return the data value.
            NodeValue *node_value =
                 node->get_attribute((ICodeKey) VALUE);
            return node_value->value;
        }

        case NT_NEGATE:
        {
            // Get the NEGATE node's expression node child.
            vector<ICodeNode *> children = node->get_children();
            ICodeNode *expression_node = children[0];

            // Execute the expression and return the negative of its value.
            DataValue *result_value = execute(expression_node);
            return (result_value->type == INTEGER)
                                    ? new DataValue(-result_value->i)
                                    : new DataValue(-result_value->f);
        }

        case NT_NOT:
        {
            // Get the NOT node's expression node child.
            vector<ICodeNode *> children = node->get_children();
            ICodeNode *expression_node = children[0];

            // Execute the expression and return the "not" of its value.
            DataValue *result_value = execute(expression_node);
            return new DataValue(!result_value->b);
        }

        // Must be a binary operator.
        default: return execute_binary_operator(node, node_type);
    }
}

DataValue *ExpressionExecutor::execute_binary_operator(
                      ICodeNode *node, const ICodeNodeTypeImpl node_type)
{
    // Get the two operand children of the operator node.
    vector<ICodeNode *> children = node->get_children();
    ICodeNode *operand_node1 = children[0];
    ICodeNode *operand_node2 = children[1];

    // Operands.
    DataValue *operand1 = execute(operand_node1);
    DataValue *operand2 = execute(operand_node2);

    bool integer_mode = (operand1->type == INTEGER) &&
                        (operand2->type == INTEGER);

    // ====================
    // Arithmetic operators
    // ====================

    if (ARITH_OPS.find(node_type) != ARITH_OPS.end())
    {
        if (integer_mode)
        {
            int value1 = operand1->i;
            int value2 = operand2->i;

            // Integer operations.
            switch (node_type)
            {
                case NT_ADD:
                    return new DataValue(value1 + value2);

                case NT_SUBTRACT:
                    return new DataValue(value1 - value2);

                case NT_MULTIPLY:
                    return new DataValue(value1 * value2);

                case NT_FLOAT_DIVIDE:
                {
                    // Check for division by zero.
                    if (value2 != 0)
                    {
                        return new DataValue(((float) value1) /
                                             ((float) value2));
                    }
                    else
                    {
                        error_handler.flag(node, DIVISION_BY_ZERO, this);
                        return new DataValue(0);
                    }
                }

                case NT_INTEGER_DIVIDE:
                {
                    // Check for division by zero.
                    if (value2 != 0)
                    {
                        return new DataValue(value1/value2);
                    }
                    else
                    {
                        error_handler.flag(node, DIVISION_BY_ZERO, this);
                        return new DataValue(0);
                    }
                }

                case NT_MOD:
                {
                    // Check for division by zero.
                    if (value2 != 0)
                    {
                        return new DataValue(value1%value2);
                    }
                    else
                    {
                        error_handler.flag(node, DIVISION_BY_ZERO, this);
                        return new DataValue(0);
                    }
                }

                default: return nullptr;  // shouldn't get here
            }
        }
        else
        {
            float value1 = operand1->type == INTEGER ? operand1->i
                                                     : operand1->f;
            float value2 = operand2->type == INTEGER ? operand2->i
                                                     : operand2->f;

            // Float operations.
            switch (node_type)
            {
                case NT_ADD:
                    return new DataValue(value1 + value2);

                case NT_SUBTRACT:
                    return new DataValue(value1 - value2);

                case NT_MULTIPLY:
                    return new DataValue(value1 * value2);

                case NT_FLOAT_DIVIDE:
                {
                    // Check for division by zero.
                    if (value2 != 0.0f)
                    {
                        return new DataValue(value1/value2);
                    }
                    else {
                        error_handler.flag(node, DIVISION_BY_ZERO, this);
                        return new DataValue(0.0f);
                    }
                }

                default: return nullptr;  // shouldn't get here
            }
        }
    }

    // ==========
    // AND and OR
    // ==========

    else if ((node_type == NT_AND) ||
             (node_type == NT_OR))
    {
        bool value1 = operand1->b;
        bool value2 = operand2->b;

        switch (node_type)
        {
            case NT_AND:
                return new DataValue(value1 && value2);

            case NT_OR:
                return new DataValue(value1 || value2);

            default: return nullptr;  // shouldn't get here
        }
    }

    // ====================
    // Relational operators
    // ====================

    else if (integer_mode)
    {
        int value1 = operand1->i;
        int value2 = operand2->i;

        // Integer operands.
        switch (node_type)
        {
            case NT_EQ:
                return new DataValue(value1 == value2);

            case NT_NE:
                return new DataValue(value1 != value2);

            case NT_LT:
                return new DataValue(value1 <  value2);

            case NT_LE:
                return new DataValue(value1 <= value2);

            case NT_GT:
                return new DataValue(value1 >  value2);

            case NT_GE:
                return new DataValue(value1 >= value2);

            default: return nullptr;  // shouldn't get here
        }
    }
    else
    {
        float value1 = operand1->type == INTEGER ? operand1->i
                                                 : operand1->f;
        float value2 = operand2->type == INTEGER ? operand2->i
                                                 : operand2->f;

        // Float operands.
        switch (node_type)
        {
            case NT_EQ:
                return new DataValue(value1 == value2);

            case NT_NE:
                return new DataValue(value1 != value2);

            case NT_LT:
                return new DataValue(value1 <  value2);

            case NT_LE:
                return new DataValue(value1 <= value2);

            case NT_GT:
                return new DataValue(value1 >  value2);

            case NT_GE:
                return new DataValue(value1 >= value2);

            default: return nullptr;  // shouldn't get here
        }
    }
}

}}}}  // namespace wci::backend::interpreter::executors
