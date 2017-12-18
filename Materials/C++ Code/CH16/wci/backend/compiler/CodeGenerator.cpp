/**
 * <h1>CodeGenerator</h1>
 *
 * <p>The code generator for a compiler back end.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#include <fstream>
#include <iomanip>
#include <chrono>
#include "CodeGenerator.h"
#include "Directive.h"
#include "Label.h"
#include "Instruction.h"
#include "LocalVariables.h"
#include "LocalStack.h"
#include "generators/ProgramGenerator.h"
#include "../Backend.h"
#include "../../intermediate/TypeSpec.h"
#include "../../intermediate/SymTabEntry.h"
#include "../../intermediate/symtabimpl/SymTabEntryImpl.h"
#include "../../intermediate/symtabimpl/Predefined.h"
#include "../../intermediate/typeimpl/TypeSpecImpl.h"
#include "../../message/Message.h"

namespace wci { namespace backend { namespace compiler {

using namespace std;
using namespace std::chrono;
using namespace wci::intermediate;
using namespace wci::intermediate::symtabimpl;
using namespace wci::intermediate::typeimpl;
using namespace wci::backend;
using namespace wci::backend::compiler::generators;
using namespace wci::message;

ofstream CodeGenerator::asmb_out;
string CodeGenerator::program_name;
int CodeGenerator::instruction_count = 0;

CodeGenerator::CodeGenerator()
    : local_variables(nullptr), local_stack(nullptr)
{
}

/**
 * Constructor for subclasses.
 * @param the parent code generator.
 */
CodeGenerator::CodeGenerator(CodeGenerator *parent)
    : Backend(), local_variables(parent->local_variables),
      local_stack(parent->local_stack)
{
}

void CodeGenerator::process(ICode *icode, SymTabStack *symtab_stack) throw (string)
{
    this->symtab_stack = symtab_stack;
    steady_clock::time_point start_time = steady_clock::now();

    SymTabEntry *program_id = symtab_stack->get_program_id();
    program_name = program_id->get_name();
    string asmb_file_name = program_name + ".j";

    // Open a new assembly file for writing.
    asmb_out.open(asmb_file_name);
    if (asmb_out.fail())
    {
        throw string("Failed to create assembly file " + asmb_file_name);
    }

    // Generate code for the main program.
    ProgramGenerator program_generator(this);
    program_generator.generate(icode->get_root());
    asmb_out.close();

    // Send the compiler summary message.
    steady_clock::time_point end_time = steady_clock::now();
    double elapsed_time =
            duration_cast<duration<double>>(end_time - start_time).count();
    Message message(COMPILER_SUMMARY,
                    INSTRUCTION_COUNT, to_string(instruction_count),
                    ELAPSED_TIME, to_string(elapsed_time));
    send_message(message);
}

void CodeGenerator::generate(ICodeNode *node) throw (string)
{
}

void CodeGenerator::generate(SymTabEntry *routine_id) throw (string)
{
}

// =====================
// General code emitters
// =====================

ostream& operator << (ostream& ofs, const DirCode& dircode)
{
    ofs << Directive::TEXT[dircode];
    return ofs;
}

ostream& operator << (ostream& ofs, const Label *label)
{
    ofs << label->get_string();
    return ofs;
}

ostream& operator << (ostream& ofs, const OpCode& opcode)
{
    ofs << Instruction::TEXT[opcode];
    return ofs;
}

/**
 * Emit a blank line.
 */
void CodeGenerator::emit_blank_line() const
{
    asmb_out << endl;
    flush(asmb_out);
}

void CodeGenerator::emit_label(Label *label) const
{
    asmb_out << label << ":" << endl;
    flush(asmb_out);
}

void CodeGenerator::emit_label(int value, Label *label) const
{
    asmb_out << "\t  " << value << ": " << label << endl;
    flush(asmb_out);
}

void CodeGenerator::emit_label(string value, Label *label) const
{
    asmb_out << "\t  " << value << ": " << label << endl;
    flush(asmb_out);
}

void CodeGenerator::emit_directive(DirCode dircode)
{
    asmb_out << dircode << endl;
    flush(asmb_out);
    ++instruction_count;
}

void CodeGenerator::emit_directive(DirCode dircode, string operand)
{
    asmb_out << dircode << " " << operand << endl;
    flush(asmb_out);
    ++instruction_count;
}

void CodeGenerator::emit_directive(DirCode dircode, int operand)
{
    asmb_out << dircode << " " << operand << endl;
    flush(asmb_out);
    ++instruction_count;
}

void CodeGenerator::emit_directive(DirCode dircode,
                                   string operand1, string operand2)
{
    asmb_out << dircode
             << " " << operand1 << " " << operand2 << endl;
    flush(asmb_out);
    ++instruction_count;
}

void CodeGenerator::emit_directive(DirCode dircode,
                                   string operand1, string operand2,
                                   string operand3)
{
    asmb_out << dircode << " " << operand1
             << " " << operand2 << " " << operand3 << endl;
    flush(asmb_out);
    ++instruction_count;
}

void CodeGenerator::emit(OpCode opcode)
{
    asmb_out << "\t" << opcode << endl;
    flush(asmb_out);
    ++instruction_count;
}

void CodeGenerator::emit(OpCode opcode, string operand)
{
    asmb_out << "\t" << opcode << "\t" << operand << endl;
    flush(asmb_out);
    ++instruction_count;
}

void CodeGenerator::emit(OpCode opcode, int operand)
{
    asmb_out << "\t" << opcode << "\t" << operand << endl;
    flush(asmb_out);
    ++instruction_count;
}

void CodeGenerator::emit(OpCode opcode, float operand)
{
    asmb_out << "\t" << opcode << "\t"
             << fixed << setprecision(10) << operand << endl;
    flush(asmb_out);
    ++instruction_count;
}

void CodeGenerator::emit(OpCode opcode, Label *label)
{
    asmb_out << "\t" << opcode << "\t" << label << endl;
    flush(asmb_out);
    ++instruction_count;
}

void CodeGenerator::emit(OpCode opcode, int operand1, int operand2)
{
    asmb_out << "\t" << opcode << "\t" << operand1
             << " " << operand2 << endl;
    flush(asmb_out);
    ++instruction_count;
}

void CodeGenerator::emit(OpCode opcode, string operand1, string operand2)
{
    asmb_out << "\t" << opcode << "\t" << operand1
             << " " << operand2 << endl;
    flush(asmb_out);
    ++instruction_count;
}

// =====
// Loads
// =====

void CodeGenerator::emit_load_constant(int value)
{
    switch (value)
    {
        case -1: emit(ICONST_M1); break;
        case  0: emit(ICONST_0);  break;
        case  1: emit(ICONST_1);  break;
        case  2: emit(ICONST_2);  break;
        case  3: emit(ICONST_3);  break;
        case  4: emit(ICONST_4);  break;
        case  5: emit(ICONST_5);  break;

        default:
        {
            if ((-128 <= value) && (value <= 127))
            {
                emit(BIPUSH, value);
            }
            else if ((-32768 <= value) && (value <= 32767))
            {
                emit(SIPUSH, value);
            }
            else
            {
                emit(LDC, value);
            }
        }
    }
}

void CodeGenerator::emit_load_constant(float value)
{
    if (value == 0.0f)
    {
        emit(FCONST_0);
    }
    else if (value == 1.0f)
    {
        emit(FCONST_1);
    }
    else if (value == 2.0f)
    {
        emit(FCONST_2);
    }
    else
    {
        emit(LDC, value);
    }
}

void CodeGenerator::emit_load_constant(string value)
{
    emit(LDC, "\"" + value + "\"");
}

void CodeGenerator::emit_load_local(TypeSpec *typespec, int index)
{
    TypeForm form = (TypeForm) -1;

    if (typespec != nullptr)
    {
        typespec = typespec->base_type();
        form = typespec->get_form();
    }

    if (   (typespec == Predefined::integer_type)
        || (typespec == Predefined::boolean_type)
        || (typespec == Predefined::char_type)
        || (form == (TypeForm) TF_ENUMERATION))
    {
        switch (index)
        {
            case 0:  emit(ILOAD_0); break;
            case 1:  emit(ILOAD_1); break;
            case 2:  emit(ILOAD_2); break;
            case 3:  emit(ILOAD_3); break;
            default: emit(ILOAD, index);
        }
    }
    else if (typespec == Predefined::real_type)
    {
        switch (index)
        {
            case 0:  emit(FLOAD_0); break;
            case 1:  emit(FLOAD_1); break;
            case 2:  emit(FLOAD_2); break;
            case 3:  emit(FLOAD_3); break;
            default: emit(FLOAD, index);
        }
    }
    else
    {
        switch (index)
        {
            case 0:  emit(ALOAD_0); break;
            case 1:  emit(ALOAD_1); break;
            case 2:  emit(ALOAD_2); break;
            case 3:  emit(ALOAD_3); break;
            default: emit(ALOAD, index);
        }
    }
}

void CodeGenerator::emit_load_variable(SymTabEntry *variable_id)
{
    TypeSpec *variable_typespec = variable_id->get_typespec()->base_type();
    int nesting_level = variable_id->get_symtab()->get_nesting_level();

    // Program variable.
    if (nesting_level == 1)
    {
        string program_name = symtab_stack->get_program_id()->get_name();
        string variableName = variable_id->get_name();
        string name = program_name + "/" + variableName;

        emit(GETSTATIC, name, type_descriptor(variable_typespec));
    }

    // Wrapped variable.
    else if (is_wrapped(variable_id))
    {
        int slot = variable_id->get_attribute((SymTabKey) SLOT)->value->i;
        emit_load_local(nullptr, slot);
        emit(GETFIELD, var_parm_wrapper(variable_typespec) + "/value",
                       type_descriptor(variable_typespec));
    }

    // Local variable.
    else
    {
        int slot = variable_id->get_attribute((SymTabKey) SLOT)->value->i;
        emit_load_local(variable_typespec, slot);
    }
}

void CodeGenerator::emit_load_array_element(TypeSpec *elmt_typespec)
{
    TypeForm form = (TypeForm) -1;

    if (elmt_typespec != nullptr)
    {
        elmt_typespec = elmt_typespec->base_type();
        form = elmt_typespec->get_form();
    }

    // Load a character from a string.
    if (elmt_typespec == Predefined::char_type)
    {
        emit(INVOKEVIRTUAL, "java/lang/StringBuilder.charAt(I)C");
    }

    // Load an array element.
    else
    {
        emit(  elmt_typespec == Predefined::integer_type ? IALOAD
             : elmt_typespec == Predefined::real_type    ? FALOAD
             : elmt_typespec == Predefined::boolean_type ? BALOAD
             : elmt_typespec == Predefined::char_type    ? CALOAD
             : form == (TypeForm) TF_ENUMERATION         ? IALOAD
             :                                             AALOAD);
    }
}

// ======
// Stores
// ======

void CodeGenerator::emit_store_local(TypeSpec *typespec, int index)
{
    TypeForm form = (TypeForm) -1;

    if (typespec != nullptr)
    {
        typespec = typespec->base_type();
        form = typespec->get_form();
    }

    if (   (typespec == Predefined::integer_type)
        || (typespec == Predefined::boolean_type)
        || (typespec == Predefined::char_type)
        || (form == (TypeForm) TF_ENUMERATION))
    {
        switch (index)
        {
            case 0:  emit(ISTORE_0); break;
            case 1:  emit(ISTORE_1); break;
            case 2:  emit(ISTORE_2); break;
            case 3:  emit(ISTORE_3); break;
            default: emit(ISTORE, index);
        }
    }
    else if (typespec == Predefined::real_type)
    {
        switch (index)
        {
            case 0:  emit(FSTORE_0); break;
            case 1:  emit(FSTORE_1); break;
            case 2:  emit(FSTORE_2); break;
            case 3:  emit(FSTORE_3); break;
            default: emit(FSTORE, index);
        }
    }
    else
    {
        switch (index)
        {
            case 0:  emit(ASTORE_0); break;
            case 1:  emit(ASTORE_1); break;
            case 2:  emit(ASTORE_2); break;
            case 3:  emit(ASTORE_3); break;
            default: emit(ASTORE, index);
        }
    }
}

void CodeGenerator::emit_store_variable(SymTabEntry *variable_id)
{
    int nesting_level = variable_id->get_symtab()->get_nesting_level();
    int slot = variable_id->get_attribute((SymTabKey) SLOT)->value->i;

    emit_store_variable(variable_id, nesting_level, slot);
}

void CodeGenerator::emit_store_variable(
                SymTabEntry *variable_id, int nesting_level, int index)
{
    TypeSpec *variable_typespec = variable_id->get_typespec();

    // Program variable.
    if (nesting_level == 1)
    {
        string target_name = variable_id->get_name();
        string program_name = symtab_stack->get_program_id()->get_name();
        string name = program_name + "/" + target_name;

        emit_range_check(variable_typespec);
        emit(PUTSTATIC, name,
             type_descriptor(variable_typespec->base_type()));
    }

    // Wrapped parameter: Set the wrapper's value field.
    else if (is_wrapped(variable_id))
    {
        emit_range_check(variable_typespec);
        emit(PUTFIELD,
             var_parm_wrapper(variable_typespec->base_type()) + "/value",
             type_descriptor(variable_typespec->base_type()));
    }

    // Local variable.
    else
    {
        emit_range_check(variable_typespec);
        emit_store_local(variable_typespec->base_type(), index);
    }
}

void CodeGenerator::emit_store_array_element(TypeSpec *elmt_typespec)
{
    TypeForm form = (TypeForm) -1;

    if (elmt_typespec != nullptr)
    {
        elmt_typespec = elmt_typespec->base_type();
        form = elmt_typespec->get_form();
    }

    if (elmt_typespec == Predefined::char_type)
    {
        emit(INVOKEVIRTUAL, "java/lang/StringBuilder.setCharAt(IC)V");
    }
    else
    {
        emit(  elmt_typespec == Predefined::integer_type ? IASTORE
             : elmt_typespec == Predefined::real_type    ? FASTORE
             : elmt_typespec == Predefined::boolean_type ? BASTORE
             : elmt_typespec == Predefined::char_type    ? CASTORE
             : form == (TypeForm) TF_ENUMERATION         ? IASTORE
             :                                             AASTORE);
    }
}

// ======================
// Miscellaneous emitters
// ======================

void CodeGenerator::emit_check_cast(TypeSpec *typespec)
{
    string descriptor = type_descriptor(typespec);

    // Don't bracket the typespec with L; if it's not an array.
    if (descriptor[0] == 'L')
    {
        descriptor = descriptor.substr(1, descriptor.length() - 1);
    }

    emit(CHECKCAST, descriptor);
}

void CodeGenerator::emit_check_cast_class(TypeSpec *typespec)
{
    string descriptor = java_type_descriptor(typespec);

    // Don't bracket the typespec with L; if it's not an array.
    if (descriptor[0] == 'L')
    {
        descriptor = descriptor.substr(1, descriptor.length() - 1);
    }

    emit(CHECKCAST, descriptor);
}

void CodeGenerator::emit_return_value(TypeSpec *typespec)
{
    TypeForm form = (TypeForm) -1;

    if (typespec != nullptr)
    {
        typespec = typespec->base_type();
        form = typespec->get_form();
    }

    if (   (typespec == Predefined::integer_type)
        || (typespec == Predefined::boolean_type)
        || (typespec == Predefined::char_type)
        || (form == (TypeForm) TF_ENUMERATION))
    {
        emit(IRETURN);
    }
    else if (typespec == Predefined::real_type)
    {
        emit(FRETURN);
    }
    else
    {
        emit(ARETURN);
    }
}

void CodeGenerator::emit_range_check(TypeSpec *target_typespec)
{
    if (target_typespec->get_form() == (TypeForm) TF_SUBRANGE)
    {
        int min =
            target_typespec->get_attribute((TypeKey) SUBRANGE_MIN_VALUE)
                ->value->i;
        int max =
            target_typespec->get_attribute((TypeKey) SUBRANGE_MAX_VALUE)
                ->value->i;

        emit(DUP);
        emit_load_constant(min);
        emit_load_constant(max);
        emit(INVOKESTATIC, "RangeChecker/check(III)V");

        local_stack->use(3);
    }
}

// =========
// Utilities
// =========

bool CodeGenerator::is_structured(TypeSpec *typespec)
{
    TypeForm form = typespec->get_form();

    return    typespec->is_pascal_string()
           || (form == (TypeForm) TF_ARRAY)
           || (form == (TypeForm) TF_RECORD);
}

bool CodeGenerator::is_wrapped(SymTabEntry *variable_id)
{
    TypeSpec *typespec = variable_id->get_typespec();
    TypeForm form = typespec->get_form();
    Definition defn = variable_id->get_definition();

    // Arrays and records are not wrapped.
    return    (defn == (Definition) DF_VAR_PARM)
           && (form != (TypeForm) TF_ARRAY)
           && (form != (TypeForm) TF_RECORD);
}

bool CodeGenerator::needs_cloning(SymTabEntry *formal_id)
{
    TypeSpec *typespec = formal_id->get_typespec();
    TypeForm form = typespec->get_form();
    Definition defn = formal_id->get_definition();

    // Arrays and records are normally passed by reference
    // and so must be cloned to be passed by value.
    return    (defn == (Definition) DF_VALUE_PARM)
           && (   (form == (TypeForm) TF_ARRAY)
               || (form == (TypeForm) TF_RECORD));
}

string CodeGenerator::type_descriptor(SymTabEntry *id)
{
    TypeSpec *typespec = id->get_typespec();

    if (typespec != nullptr)
    {
        if (is_wrapped(id))
        {
            return "L" + var_parm_wrapper(typespec->base_type()) + ";";
        }
        else
        {
            return type_descriptor(id->get_typespec());
        }
    }
    else
    {
        return "V";
    }
}

string CodeGenerator::type_descriptor(TypeSpec *typespec)
{
    TypeForm form = typespec->get_form();
    string buffer = "";

    while (   (form == (TypeForm) TF_ARRAY)
           && !typespec->is_pascal_string())
    {
        buffer += "[";
        typespec =
            typespec->get_attribute((TypeKey) ARRAY_ELEMENT_TYPE)->typespec;
        form = typespec->get_form();
    }

    typespec = typespec->base_type();

    if (typespec == Predefined::integer_type)
    {
        buffer += "I";
    }
    else if (typespec == Predefined::real_type)
    {
        buffer += "F";
    }
    else if (typespec == Predefined::boolean_type)
    {
        buffer += "Z";
    }
    else if (typespec == Predefined::char_type)
    {
        buffer += "C";
    }
    else if (typespec->is_pascal_string())
    {
        buffer += "Ljava/lang/StringBuilder;";
    }
    else if (form == (TypeForm) TF_ENUMERATION)
    {
        buffer += "I";
    }
    else /* (form == RECORD) */
    {
        buffer += "Ljava/util/HashMap;";
    }

    return buffer;
}

string CodeGenerator::java_type_descriptor(TypeSpec *typespec)
{
    TypeForm form = typespec->get_form();
    string buffer = "";
    bool isArray = false;

    while (   (form == (TypeForm) TF_ARRAY)
           && !typespec->is_pascal_string())
    {
        buffer += "[";
        typespec =
            typespec->get_attribute((TypeKey) ARRAY_ELEMENT_TYPE)->typespec;
        form = typespec->get_form();
        isArray = true;
    }

    if (isArray)
    {
        buffer += "L";
    }

    typespec = typespec->base_type();

    if (typespec == Predefined::integer_type)
    {
        buffer += "java/lang/Integer";
    }
    else if (typespec == Predefined::real_type)
    {
        buffer += "java/lang/Float";
    }
    else if (typespec == Predefined::boolean_type)
    {
        buffer += "java/lang/Boolean";
    }
    else if (typespec == Predefined::char_type)
    {
        buffer += "java/lang/Character";
    }
    else if (typespec->is_pascal_string())
    {
        buffer += "java/lang/StringBuilder";
    }
    else if (form == (TypeForm) TF_ENUMERATION)
    {
        buffer += "java/lang/Integer";
    }
    else /* (form == RECORD) */
    {
        buffer += "java/util/HashMap";
    }

    if (isArray)
    {
        buffer += ";";
    }

    return buffer;
}

string CodeGenerator::value_of_signature(TypeSpec *typespec)
{
    string java_type = java_type_descriptor(typespec);
    string type_code = type_descriptor(typespec);

    return java_type + ".valueOf(" + type_code + ")L" + java_type + ";";
}

string CodeGenerator::value_signature(TypeSpec *typespec)
{
    string java_type = java_type_descriptor(typespec);
    string type_code = type_descriptor(typespec);
    string type_name = typespec == Predefined::integer_type ? "int"
                     : typespec == Predefined::real_type    ? "float"
                     : typespec == Predefined::boolean_type ? "bool"
                     : typespec == Predefined::char_type    ? "char"
                     :                                        "int";

    return java_type + "." + type_name + "Value()" + type_code;
}

string CodeGenerator::var_parm_wrapper(TypeSpec *typespec)
{
    typespec = typespec->base_type();

    TypeForm form = typespec->get_form();

    return typespec == Predefined::integer_type ? "IWrap"
         : typespec == Predefined::real_type    ? "RWrap"
         : typespec == Predefined::boolean_type ? "BWrap"
         : form == (TypeForm) TF_ENUMERATION    ? "IWrap"
         :                                        "CWrap";
}

}}} // namespace wci::backend::compiler
