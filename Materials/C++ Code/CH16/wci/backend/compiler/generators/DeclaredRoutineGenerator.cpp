/**
 * <h1>DeclaredRoutineGenerator</h1>
 *
 * <p>Generate code for a declared procedure or function.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#include <string>
#include <set>
#include "DeclaredRoutineGenerator.h"
#include "StructuredDataGenerator.h"
#include "StatementGenerator.h"
#include "../CodeGenerator.h"
#include "../Directive.h"
#include "../../../intermediate/SymTabEntry.h"
#include "../../../intermediate/symtabimpl/SymTabEntryImpl.h"
#include "../../../intermediate/ICodeNode.h"
#include "../../../DataValue.h"

namespace wci { namespace backend { namespace compiler { namespace generators {

using namespace std;
using namespace wci;
using namespace wci::intermediate;
using namespace wci::intermediate::symtabimpl;
using namespace wci::backend::compiler;

DeclaredRoutineGenerator::DeclaredRoutineGenerator(CodeGenerator *parent)
    : CodeGenerator(parent), routine_id(nullptr), function_value_slot(-1)
{
}

/**
 * Generate code for a declared procedure or function
 * @param routine_id the symbol table entry of the routine's name.
 */
void DeclaredRoutineGenerator::generate(SymTabEntry *routine_id)
    throw (string)
{
    this->routine_id = routine_id;
    routine_name = routine_id->get_name();

    SymTab *routine_symtab =
        routine_id->get_attribute((SymTabKey) ROUTINE_SYMTAB)->symtab;
    local_variables =
            new LocalVariables(routine_symtab->get_max_slot_number());
    local_stack = new LocalStack();

    // Reserve an extra variable for the function return value.
    if (routine_id->get_definition() == (Definition) DF_FUNCTION)
    {
        function_value_slot = local_variables->reserve();
        routine_id->set_attribute(
                (SymTabKey) SLOT,
                new EntryValue(new DataValue(function_value_slot)));
    }

    generate_routine_header();
    generate_routine_locals();

    // Generate code to allocate any arrays, records, and strings.
    StructuredDataGenerator structured_data_generator(this);
    structured_data_generator.generate(routine_id);

    generate_routine_code();
    generate_routine_return();
    generate_routine_epilogue();
}

/**
 * Generate the routine header.
 */
void DeclaredRoutineGenerator::generate_routine_header()
{
    string routine_name = routine_id->get_name();
    vector<SymTabEntry *> parm_ids =
            routine_id->get_attribute((SymTabKey) ROUTINE_PARMS)->v;
    string buffer = "";

    // Procedure or function name.
    buffer += routine_name;
    buffer += "(";

    // Parameter and return type descriptors.
    if (parm_ids.size() > 0)
    {
        for (SymTabEntry *parm_id : parm_ids)
        {
            buffer += type_descriptor(parm_id);
        }
    }
    buffer += ")";
    buffer += type_descriptor(routine_id);

    emit_blank_line();
    emit_directive(METHOD_PRIVATE_STATIC, buffer);
}

/**
 * Generate directives for the local variables.
 */
void DeclaredRoutineGenerator::generate_routine_locals()
{
    SymTab *symtab =
        routine_id->get_attribute((SymTabKey) ROUTINE_SYMTAB)->symtab;
    vector<SymTabEntry *> ids = symtab->sorted_entries();

    emit_blank_line();

    // Loop over all the routine's identifiers and
    // emit a .var directive for each variable and formal parameter.
    for (SymTabEntry *id : ids)
    {
        Definition defn = id->get_definition();

        if (   (defn == (Definition) DF_VARIABLE)
            || (defn == (Definition) DF_VALUE_PARM)
            || (defn == (Definition) DF_VAR_PARM))
        {
            int slot = id->get_attribute((SymTabKey) SLOT)->value->i;
            emit_directive(VAR, to_string(slot) + " is " + id->get_name(),
                          type_descriptor(id));
        }
    }

    // Emit an extra .var directive for an implied function variable.
    if (routine_id->get_definition() == (Definition) DF_FUNCTION)
    {
        emit_directive(
                VAR, to_string(function_value_slot) + " is " + routine_name,
                type_descriptor(routine_id->get_typespec()));
    }
}

/**
 * Generate code for the routine's body.
 */
void DeclaredRoutineGenerator::generate_routine_code() throw (string)
{
    ICode *icode =
            routine_id->get_attribute((SymTabKey) ROUTINE_ICODE)->icode;
    ICodeNode *root = icode->get_root();

    emit_blank_line();

    // Generate code for the compound statement.
    StatementGenerator statement_generator(this);
    statement_generator.generate(root);
}

/**
 * Generate the routine's return code.
 */
void DeclaredRoutineGenerator::generate_routine_return()
{
    emit_blank_line();

    // Function: Return the value in the implied function variable.
    if (routine_id->get_definition() == (Definition) DF_FUNCTION)
    {
        TypeSpec *typespec = routine_id->get_typespec();

        emit_load_local(typespec, function_value_slot);
        emit_return_value(typespec);

        local_stack->use(1);
    }

    // Procedure: Just return.
    else
    {
        emit(RETURN);
    }
}

/**
 * Generate the routine's epilogue.
 */
void DeclaredRoutineGenerator::generate_routine_epilogue()
{
    emit_blank_line();
    emit_directive(LIMIT_LOCALS, local_variables->count());
    emit_directive(LIMIT_STACK,  local_stack->capacity());
    emit_directive(END_METHOD);
}

}}}}  // namespace wci::backend::compiler::generators
