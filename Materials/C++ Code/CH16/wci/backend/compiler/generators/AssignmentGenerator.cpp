/**
 * <h1>AssignmentGenerator</h1>
 *
 * <p>Generate code for an assignment statement.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#include <string>
#include <vector>
#include "AssignmentGenerator.h"
#include "StatementGenerator.h"
#include "ExpressionGenerator.h"
#include "../Instruction.h"
#include "../../../frontend/Token.h"
#include "../../../frontend/pascal/PascalToken.h"
#include "../../../intermediate/SymTabEntry.h"
#include "../../../intermediate/symtabimpl/SymTabEntryImpl.h"
#include "../../../intermediate/symtabimpl/Predefined.h"
#include "../../../intermediate/ICodeNode.h"
#include "../../../intermediate/icodeimpl/ICodeNodeImpl.h"
#include "../../../intermediate/TypeSpec.h"

namespace wci { namespace backend { namespace compiler { namespace generators {

using namespace std;
using namespace wci::intermediate;
using namespace wci::intermediate::symtabimpl;
using namespace wci::intermediate::icodeimpl;
using namespace wci::backend::compiler;

AssignmentGenerator::AssignmentGenerator(CodeGenerator *parent)
    : StatementGenerator(parent)
{
}

void AssignmentGenerator::generate(ICodeNode *node) throw (string)
{
    TypeSpec *assign_typespec = node->get_typespec();

    // The ASSIGN node's children are the target variable
    // and the expression.
    vector<ICodeNode *> assign_children = node->get_children();
    ICodeNode *target_node = assign_children[0];
    ICodeNode *expr_node = assign_children[1];

    SymTabEntry *target_id = target_node->get_attribute((ICodeKey) ID)->id;
    TypeSpec *target_typespec = target_node->get_typespec();
    TypeSpec *expr_typespec = expr_node->get_typespec();
    ExpressionGenerator expr_generator(this);

    int slot;           // local variables array slot number of the target
    int nesting_level;  // nesting level of the target
    SymTab *symtab;     // symbol table that contains the target id

    // Assign a function value. Use the slot number of the function value.
    if (target_id->get_definition() == (Definition) DF_FUNCTION)
    {
        slot = target_id->get_attribute((SymTabKey) SLOT)->value->i;
        nesting_level = 2;
    }

    // Standard assignment.
    else
    {
        symtab = target_id->get_symtab();
        slot = target_id->get_attribute((SymTabKey) SLOT)->value->i;
        nesting_level = symtab->get_nesting_level();
    }

    // Generate code to do the assignment.
    generate_scalar_assignment(target_typespec, target_id,
                               slot, nesting_level, expr_node,
                               expr_typespec, expr_generator);
}

void AssignmentGenerator::generate_scalar_assignment(
                                    TypeSpec *target_typespec,
                                    SymTabEntry *target_id,
                                    int index,
                                    int nesting_level,
                                    ICodeNode *expr_node,
                                    TypeSpec *expr_typespec,
                                    ExpressionGenerator &expr_generator)
{
    // Generate code to evaluate the expression.
    // Special cases: float variable := integer constant
    //                float variable := integer expression
    //                char variable  := single-character string constant
    if (target_typespec == Predefined::real_type)
    {
        if (expr_node->get_type() == (ICodeNodeType) NT_INTEGER_CONSTANT)
        {
            int value =
                    expr_node->get_attribute((ICodeKey) VALUE)->value->i;
            emit_load_constant((float) value);
            local_stack->increase(1);
        }
        else
        {
            expr_generator.generate(expr_node);
            if (expr_typespec->base_type() == Predefined::integer_type)
            {
                emit(I2F);
            }
        }
    }
    else if (   (target_typespec == Predefined::char_type)
             && (expr_node->get_type()
                                 == (ICodeNodeType) NT_STRING_CONSTANT))
    {
        int value = expr_node->get_attribute((ICodeKey) VALUE)->value->s[0];
        emit_load_constant(value);
        local_stack->increase(1);
    }
    else
    {
        expr_generator.generate(expr_node);
    }

    // Generate code to store the expression value into the target variable.
    emit_store_variable(target_id, nesting_level, index);
    local_stack->decrease(is_wrapped(target_id) ? 2 : 1);
}

}}}}  // namespace wci::backend::compiler::generators
