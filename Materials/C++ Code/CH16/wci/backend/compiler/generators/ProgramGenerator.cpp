/**
 * <h1>ProgramGenerator</h1>
 *
 * <p>Generate code for the main program.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#include <string>
#include <vector>
#include <set>
#include "ProgramGenerator.h"
#include "StatementGenerator.h"
#include "DeclaredRoutineGenerator.h"
#include "StructuredDataGenerator.h"
#include "../CodeGenerator.h"
#include "../Directive.h"
#include "../Instruction.h"
#include "../Label.h"
#include "../LocalStack.h"
#include "../LocalVariables.h"
#include "../../../intermediate/SymTab.h"
#include "../../../intermediate/SymTabEntry.h"
#include "../../../intermediate/symtabimpl/SymTabEntryImpl.h"
#include "../../../intermediate/ICodeNode.h"

namespace wci { namespace backend { namespace compiler { namespace generators {

using namespace std;
using namespace wci::intermediate;
using namespace wci::intermediate::symtabimpl;
using namespace wci::backend::compiler;

ProgramGenerator::ProgramGenerator(CodeGenerator *parent)
    : CodeGenerator(parent), program_id(nullptr)
{
    Directive::initialize();
    Instruction::initialize();
}

void ProgramGenerator::generate(ICodeNode *node) throw (string)
{
    program_id = symtab_stack->get_program_id();
    program_name = program_id->get_name();
    local_variables = new LocalVariables(0);
    local_stack = new LocalStack();

    emit_directive(CLASS_PUBLIC, program_name);
    emit_directive(SUPER, "java/lang/Object");

    generate_fields();
    generate_constructor();
    generate_routines();
    generate_main_method();
}

/**
 * Generate directives for the fields.
 */
void ProgramGenerator::generate_fields()
{
    // Runtime timer and standard in.
    emit_blank_line();
    emit_directive(FIELD_PRIVATE_STATIC, "_runTimer",   "LRunTimer;");
    emit_directive(FIELD_PRIVATE_STATIC, "_standardIn", "LPascalTextIn;");

    SymTab *symtab =
        program_id->get_attribute((SymTabKey) ROUTINE_SYMTAB)->symtab;
    vector<SymTabEntry *> ids = symtab->sorted_entries();

    emit_blank_line();

    // Loop over all the program's identifiers and
    // emit a .field directive for each variable.
    for (SymTabEntry *id : ids)
    {
        Definition defn = id->get_definition();

        if (defn == (Definition) DF_VARIABLE)
        {
            emit_directive(FIELD_PRIVATE_STATIC, id->get_name(),
                          type_descriptor(id));
        }
    }
}

/**
 * Generate code for the main program constructor.
 */
void ProgramGenerator::generate_constructor()
{
    emit_blank_line();
    emit_directive(METHOD_PUBLIC, "<init>()V");

    emit_blank_line();
    emit(ALOAD_0);
    emit(INVOKENONVIRTUAL, "java/lang/Object/<init>()V");
    emit(RETURN);

    emit_blank_line();
    emit_directive(LIMIT_LOCALS, 1);
    emit_directive(LIMIT_STACK , 1);

    emit_directive(END_METHOD);
}

/**
 * Generate code for any nested procedures and functions.
 */
void ProgramGenerator::generate_routines() throw (string)
{
    DeclaredRoutineGenerator declared_routine_generator(this);
    vector<SymTabEntry *> routine_ids =
            program_id->get_attribute((SymTabKey) ROUTINE_ROUTINES)->v;

    // Generate code for each procedure or function.
    for (SymTabEntry *id : routine_ids)
    {
        declared_routine_generator.generate(id);
    }
}

/**
 * Generate code for the program body as the main method.
 */
void ProgramGenerator::generate_main_method() throw (string)
{
    emit_blank_line();
    emit_directive(METHOD_PUBLIC_STATIC, "main([Ljava/lang/String;)V");

    generate_main_method_prologue();

    // Generate code to allocate any arrays, records, and strings.
    StructuredDataGenerator structured_data_generator(this);
    structured_data_generator.generate(program_id);

    generate_main_method_code();
    generate_main_method_epilogue();
}

/**
 * Generate the main method prologue.
 */
void ProgramGenerator::generate_main_method_prologue()
{
    string program_name = program_id->get_name();

    // Runtime timer.
    emit_blank_line();
    emit(NEW, "RunTimer");
    emit(DUP);
    emit(INVOKENONVIRTUAL, "RunTimer/<init>()V");
    emit(PUTSTATIC, program_name + "/_runTimer", "LRunTimer;");

    // Standard in.
    emit(NEW, "PascalTextIn");
    emit(DUP);
    emit(INVOKENONVIRTUAL, "PascalTextIn/<init>()V");
    emit(PUTSTATIC, program_name + "/_standardIn LPascalTextIn;");

    local_stack->use(3);
}

/**
 * Generate code for the main method.
 */
void ProgramGenerator::generate_main_method_code() throw (string)
{
    ICode *icode =
            program_id->get_attribute((SymTabKey) ROUTINE_ICODE)->icode;
    ICodeNode *root = icode->get_root();

    emit_blank_line();

    // Generate code for the compound statement.
    StatementGenerator statement_generator(this);
    statement_generator.generate(root);
}

/**
 * Generate the main method epilogue.
 */
void ProgramGenerator::generate_main_method_epilogue()
{
    // Print the execution time.
    emit_blank_line();
    emit(GETSTATIC, program_name + "/_runTimer", "LRunTimer;");
    emit(INVOKEVIRTUAL, "RunTimer.printElapsedTime()V");

    local_stack->use(1);

    emit_blank_line();
    emit(RETURN);
    emit_blank_line();

    emit_directive(LIMIT_LOCALS, local_variables->count());
    emit_directive(LIMIT_STACK,  local_stack->capacity());
    emit_directive(END_METHOD);
}

}}}}  // namespace wci::backend::compiler::generators
