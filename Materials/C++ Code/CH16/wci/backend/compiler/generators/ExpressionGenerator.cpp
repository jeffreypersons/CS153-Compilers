/**
 * <h1>ExpressionGenerator</h1>
 *
 * <p>Generate code for an expression.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#include <set>
#include "ExpressionGenerator.h"
#include "StatementGenerator.h"
#include "../CodeGenerator.h"
#include "../../../intermediate/symtabimpl/Predefined.h"
#include "../../../intermediate/ICodeNode.h"
#include "../../../intermediate/icodeimpl/ICodeNodeImpl.h"
#include "../../../intermediate/typeimpl/TypeChecker.h"
#include "../../../intermediate/typeimpl/TypeSpecImpl.h"

namespace wci { namespace backend { namespace compiler { namespace generators {

using namespace std;
using namespace wci::intermediate;
using namespace wci::intermediate::symtabimpl;
using namespace wci::intermediate::icodeimpl;
using namespace wci::intermediate::typeimpl;
using namespace wci::backend::compiler;

set<ICodeNodeTypeImpl> ExpressionGenerator::ARITH_OPS =
{
    NT_ADD, NT_SUBTRACT, NT_MULTIPLY,
    NT_FLOAT_DIVIDE, NT_INTEGER_DIVIDE, NT_MOD
};

ExpressionGenerator::ExpressionGenerator(CodeGenerator *parent)
    : StatementGenerator(parent)
{
}

void ExpressionGenerator::generate(ICodeNode *node) throw (string)
{
    ICodeNodeTypeImpl node_type = (ICodeNodeTypeImpl) node->get_type();

    switch (node_type)
    {
        case NT_VARIABLE:
        {
            // Generate code to load a variable's value.
            generate_load_value(node);
            break;
        }

        case NT_INTEGER_CONSTANT:
        {
            TypeSpec *typespec = node->get_typespec();
            int value = node->get_attribute((ICodeKey) VALUE)->value->i;

            // Generate code to load a bool constant
            // 0 (false) or 1 (true).
            if (typespec == Predefined::boolean_type)
            {
                emit_load_constant(value == 1 ? 1 : 0);
            }

            // Generate code to load an integer constant.
            else
            {
                emit_load_constant(value);
            }

            local_stack->increase(1);
            break;
        }

        case NT_REAL_CONSTANT:
        {
            float value = node->get_attribute((ICodeKey) VALUE)->value->f;

            // Generate code to load a float constant.
            emit_load_constant(value);

            local_stack->increase(1);
            break;
        }

        case NT_STRING_CONSTANT:
        {
            string value = node->get_attribute((ICodeKey) VALUE)->value->s;

            // Generate code to load a string constant.
            if (node->get_typespec() == Predefined::char_type)
            {
                emit_load_constant(value[0]);
            }
            else
            {
                emit_load_constant(value);
            }

            local_stack->increase(1);
            break;
        }

        case NT_NEGATE:
        {
            // Get the NEGATE node's expression node child.
            vector<ICodeNode *> children = node->get_children();
            ICodeNode *expr_node = children[0];

            // Generate code to evaluate the expression and
            // negate its value.
            generate(expr_node);
            emit(expr_node->get_typespec() == Predefined::integer_type
                     ? INEG : FNEG);

            break;
        }

        case NT_NOT:
        {
            // Get the NOT node's expression node child.
            vector<ICodeNode *> children = node->get_children();
            ICodeNode *expr_node = children[0];

            // Generate code to evaluate the expression and NOT its value.
            generate(expr_node);
            emit(ICONST_1);
            emit(IXOR);

            local_stack->use(1);
            break;
        }

        // Must be a binary operator.
        default: generate_binary_operator(node, node_type);
    }
}

void ExpressionGenerator::generate_load_value(ICodeNode *variable_node)
{
    generate_load_variable(variable_node);
}

TypeSpec *ExpressionGenerator::generate_load_variable(
                                                ICodeNode* variable_node)
{
    SymTabEntry *variable_id =
                        variable_node->get_attribute((ICodeKey) ID)->id;
    TypeSpec *variable_typespec = variable_id->get_typespec();

    emit_load_variable(variable_id);
    local_stack->increase(1);

    return variable_typespec;
}

/**
 * Generate code to evaluate a binary operator.
 * @param node the root node of the expression.
 * @param node_type the node type.
 */
void ExpressionGenerator::generate_binary_operator(
                            ICodeNode *node, ICodeNodeTypeImpl node_type)
{
    // Get the two operand children of the operator node.
    vector<ICodeNode *> children = node->get_children();
    ICodeNode *operand_node1 = children[0];
    ICodeNode *operand_node2 = children[1];
    TypeSpec *typespec1 = operand_node1->get_typespec();
    TypeSpec *typespec2 = operand_node2->get_typespec();

    bool integer_mode =
                   TypeChecker::are_both_integer(typespec1, typespec2)
                || (typespec1->get_form() == (TypeForm) TF_ENUMERATION)
                || (typespec2->get_form() == (TypeForm) TF_ENUMERATION);
    bool real_mode =
                   TypeChecker::is_at_least_one_real(typespec1, typespec2)
                || (node_type == NT_FLOAT_DIVIDE);
    bool character_mode =    TypeChecker::is_char(typespec1)
                          && TypeChecker::is_char(typespec2);
    bool string_mode =    typespec1->is_pascal_string()
                       && typespec2->is_pascal_string();

    if (!string_mode)
    {
        // Emit code to evaluate the first operand.
        generate(operand_node1);
        if (real_mode && TypeChecker::is_integer(typespec1)) emit(I2F);

        // Emit code to evaluate the second operand.
        generate(operand_node2);
        if (real_mode && TypeChecker::is_integer(typespec2)) emit(I2F);
    }

    // ====================
    // Arithmetic operators
    // ====================

    if (ARITH_OPS.find(node_type) != ARITH_OPS.end())
    {
        if (integer_mode)
        {
            // Integer operations.
            switch (node_type)
            {
                case NT_ADD:            emit(IADD); break;
                case NT_SUBTRACT:       emit(ISUB); break;
                case NT_MULTIPLY:       emit(IMUL); break;
                case NT_FLOAT_DIVIDE:   emit(FDIV); break;
                case NT_INTEGER_DIVIDE: emit(IDIV); break;
                case NT_MOD:            emit(IREM); break;

                default: break;  // should never get here
            }
        }
        else
        {
            // Float operations.
            switch (node_type)
            {
                case NT_ADD:          emit(FADD); break;
                case NT_SUBTRACT:     emit(FSUB); break;
                case NT_MULTIPLY:     emit(FMUL); break;
                case NT_FLOAT_DIVIDE: emit(FDIV); break;

                default: break;  // should never get here
            }
        }

        local_stack->decrease(1);
    }

    // ==========
    // AND and OR
    // ==========

    else if (node_type == NT_AND)
    {
        emit(IAND);
        local_stack->decrease(1);
    }
    else if (node_type == NT_OR)
    {
        emit(IOR);
        local_stack->decrease(1);
    }

    // ====================
    // Relational operators
    // ====================

    else
    {
        Label *true_label = Label::new_label();
        Label *next_label = Label::new_label();

        if (integer_mode || character_mode)
        {
            switch (node_type)
            {
                case NT_EQ: emit(IF_ICMPEQ, true_label); break;
                case NT_NE: emit(IF_ICMPNE, true_label); break;
                case NT_LT: emit(IF_ICMPLT, true_label); break;
                case NT_LE: emit(IF_ICMPLE, true_label); break;
                case NT_GT: emit(IF_ICMPGT, true_label); break;
                case NT_GE: emit(IF_ICMPGE, true_label); break;

                default: break;  // should never get here
            }

            local_stack->decrease(2);
        }

        else if (real_mode)
        {
            emit(FCMPG);

            switch (node_type)
            {
                case NT_EQ: emit(IFEQ, true_label); break;
                case NT_NE: emit(IFNE, true_label); break;
                case NT_LT: emit(IFLT, true_label); break;
                case NT_LE: emit(IFLE, true_label); break;
                case NT_GT: emit(IFGT, true_label); break;
                case NT_GE: emit(IFGE, true_label); break;

                default: break;  // should never get here
            }

            local_stack->decrease(2);
        }

        emit(ICONST_0); // false
        emit(GOTO, next_label);
        emit_label(true_label);
        emit(ICONST_1); // true
        emit_label(next_label);

        local_stack->increase(1);
    }
}

}}}}  // namespace wci::backend::compiler::generators
