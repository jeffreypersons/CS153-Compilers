/**
 * <h1>CodeGenerator</h1>
 *
 * <p>The code generator for a compiler back end.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#ifndef WCI_BACKEND_COMPILER_CODEGENERATOR_H_
#define WCI_BACKEND_COMPILER_CODEGENERATOR_H_

#include <fstream>
#include "Directive.h"
#include "Label.h"
#include "Instruction.h"
#include "LocalVariables.h"
#include "LocalStack.h"
#include "../Backend.h"
#include "../../intermediate/TypeSpec.h"

namespace wci { namespace backend { namespace compiler {

using namespace std;
using namespace wci::intermediate;
using namespace wci::backend;

class CodeGenerator : public Backend
{
public:
    /**
     * Constructor.
     */
    CodeGenerator();

    /**
     * Constructor for subclasses.
     * @param the parent code generator.
     */
    CodeGenerator(CodeGenerator *parent);

    /**
     * Process the intermediate code and the symbol table created by the
     * parser to generate object code.
     * @param icode the intermediate code.
     * @param symtab_stack the symbol table stack.
     * @throw a string message if an error occurred.
     */
    void process(ICode *icode, SymTabStack *symtab_stack) throw (string);

    /**
     * Generate code for a statement.
     * To be overridden by the code generator subclasses.
     * @param node the root node of the statement.
     * @throw a string message if an error occurred.
     */
    void generate(ICodeNode *node) throw (string);

    /**
     * Generate code for a routine.
     * To be overridden by the code generator subclasses.
     * @param routine_id the routine's symbol table entry.
     * @throw a string message if an error occurred.
     */
    void generate(SymTabEntry *routine_id) throw (string);

protected:
    static string program_name;

    LocalVariables *local_variables;
    LocalStack *local_stack;

    /**
     * Emit a blank line.
     */
    void emit_blank_line() const;

    /**
     * Emit a label.
     * @param label the label.
     */
    void emit_label(Label *label) const;

    /**
     * Emit a label preceded by an integer value for a switch table.
     * @param label the label.
     */
    void emit_label(int value, Label *label) const;

    /**
     * Emit a label preceded by a string value for a switch table.
     * @param label the label.
     */
    void emit_label(string value, Label *label) const;

    /**
     * Emit a directive.
     * @param dircode the directive key.
     */
    void emit_directive(DirCode dircode);

    /**
     * Emit a 1-operand directive.
     * @param directive the directive code.
     * @param operand the directive operand.
     */
    void emit_directive(DirCode dircode, string operand);

    /**
     * Emit a 1-operand directive.
     * @param dircode the directive code.
     * @param operand the directive operand.
     */
    void emit_directive(DirCode dircode, int operand);

    /**
     * Emit a 2-operand directive.
     * @param dircode the directive code.
     * @param operand1 the first operand.
     * @param operand2 the second operand.
     */
    void emit_directive(DirCode dircode,
                        string operand1, string operand2);

    /**
     * Emit a 3-operand directive.
     * @param dircode the directive code.
     * @param operand1 the first operand.
     * @param operand2 the second operand.
     * @param operand3 the third operand.
     */
    void emit_directive(DirCode dircode,
                        string operand1, string operand2, string operand3);

    /**
     * Emit a 0-operand instruction.
     * @param opcode the operation code.
     */
    void emit(OpCode opcode);

    /**
     * Emit a 1-operand instruction.
     * @param opcode the operation code.
     * @param operand the operand text.
     */
    void emit(OpCode opcode, string operand);

    /**
     * Emit a 1-operand instruction.
     * @param opcode the operation code.
     * @param operand the operand value.
     */
    void emit(OpCode opcode, int operand);

    /**
     * Emit a 1-operand instruction.
     * @param opcode the operation code.
     * @param operand the operand value.
     */
    void emit(OpCode opcode, float operand);

    /**
     * Emit a 1-operand instruction.
     * @param opcode the operation code.
     * @param label the label operand.
     */
    void emit(OpCode opcode, Label *label);

    /**
     * Emit a 2-operand instruction.
     * @param opcode the operation code.
     * @param operand1 the value of the first operand.
     * @param operand2 the value of the second operand.
     */
    void emit(OpCode opcode, int operand1, int operand2);

    /**
     * Emit a 2-operand instruction.
     * @param opcode the operation code.
     * @param operand1 the text of the first operand.
     * @param operand2 the text of the second operand.
     */
    void emit(OpCode opcode, string operand1, string operand2);

    // =====
    // Loads
    // =====

    /**
     * Emit a load of an integer constant value.
     * @param value the constant value.
     */
    void emit_load_constant(int value);

    /**
     * Emit a load of a real constant value.
     * @param value the constant value.
     */
    void emit_load_constant(float value);

    /**
     * Emit a load of a string constant value.
     * @param value the constant value.
     */
    void emit_load_constant(string value);

    /**
     * Emit a load instruction for a local variable.
     * @param typespec the variable's data type.
     * @param index the variable's index into the local variables array.
     */
    void emit_load_local(TypeSpec *typespec, int index);

    /**
     * Emit code to load the value of a variable, which can be
     * a program variable, a local variable, or a VAR parameter.
     * @param variable_id the variable's symbol table entry.
     */
    void emit_load_variable(SymTabEntry *variable_id);

    /**
     * Emit a load of an array element.
     * @param elmtType the element type if character, else null.
     */
    void emit_load_array_element(TypeSpec *elmt_typespec);

    // ======
    // Stores
    // ======

    /**
     * Emit a store instruction into a local variable.
     * @param typespec the data type of the variable.
     * @param index the local variable index.
     */
    void emit_store_local(TypeSpec *typespec, int index);

    /**
     * Emit code to store a value into a variable, which can be
     * a program variable, a local variable, or a VAR parameter.
     * @param variable_id the symbol table entry of the variable.
     */
    void emit_store_variable(SymTabEntry *variable_id);

    /**
     * Emit code to store a value into a variable, which can be
     * a program variable, a local variable, or a VAR parameter.
     * @param variable_id the symbol table entry of the variable.
     * @param nesting_level the variable's nesting level.
     * @param index the vaiable's index.
     */
    void emit_store_variable(SymTabEntry *variable_id, int nesting_level,
                             int index);

    /**
     * Emit a store of an array element.
     * @param elmtType the element type.
     */
    void emit_store_array_element(TypeSpec *elmt_typespec);

    // ======================
    // Miscellaneous emitters
    // ======================

    /**
     * Emit the CHECKCAST instruction for a scalar type.
     * @param typespec the data type.
     */
    void emit_check_cast(TypeSpec *typespec);

    /**
     * Emit the CHECKCAST instruction for a class.
     * @param typespec the data type.
     */
    void emit_check_cast_class(TypeSpec *typespec);

    /**
     * Emit a function return of a value.
     * @param typespec the type of the return value.
     */
    void emit_return_value(TypeSpec *typespec);

    /**
     * Emit code to perform a runtime range check before an assignment.
     * @param target_typespec the type of the assignment target.
     */
    void emit_range_check(TypeSpec *target_typespec);

    // =========
    // Utilities
    // =========

    /**
     * Return whether or not a data type is structured.
     * @param typespec the data type.
     * @return true if the type is a string, array, or record; else false.
     */
    bool is_structured(TypeSpec *typespec);

    /**
     * Return whether or not a variable is wrapped to pass by reference.
     * @param variable_id the symbol table entry of the variable.
     * @return true if wrapped, false if not.
     */
    bool is_wrapped(SymTabEntry *variable_id);

    /**
     * Return whether or not a value needs to be cloned to pass by value.
     * @param formal_id the symbol table entry of the formal parameter.
     * @return true if needs wrapping, false if not.
     */
    bool needs_cloning(SymTabEntry *formal_id);

    /**
     * Generate a type descriptor of an identifier's type.
     * @param id the symbol table entry of an identifier.
     * @return the type descriptor.
     */
    string type_descriptor(SymTabEntry *id);

    /**
     * Generate a type descriptor for a data type.
     * @param typespec the data type.
     * @return the type descriptor.
     */
    string type_descriptor(TypeSpec *typespec);

    /**
     * Return the valueOf() signature for a given scalar type.
     * @param typespec the scalar type.
     * @return the valueOf() signature.
     */
    string value_of_signature(TypeSpec *typespec);

    /**
     * Return the xxxValue() signature for a given scalar type.
     * @param typespec the scalar type.
     * @return the valueOf() signature.
     */
    string value_signature(TypeSpec *typespec);

    /**
     * Generate the name of the wrapper to use to pass an actual parameter
     * by reference.
     * @param typespec the parameter type..
     * @return the name of the wrapper.
     */
    string var_parm_wrapper(TypeSpec *typespec);

private:
    static ofstream asmb_out;
    static int instruction_count;

    /**
     * Generate a Java type descriptor for a data type.
     * @param typespec the data type.
     * @return the type descriptor.
     */
    string java_type_descriptor(TypeSpec *typespec);
};

}}} // namespace wci::backend::compiler

#endif /* WCI_BACKEND_COMPILER_CODEGENERATOR_H_ */
