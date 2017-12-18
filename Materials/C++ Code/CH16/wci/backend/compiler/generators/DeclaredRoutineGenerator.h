/**
 * <h1>DeclaredRoutineGenerator</h1>
 *
 * <p>Generate code for a declared procedure or function.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#ifndef DECLAREDROUTINEGENERATOR_H_
#define DECLAREDROUTINEGENERATOR_H_

#include <string>
#include <set>
#include "../CodeGenerator.h"
#include "../../../intermediate/SymTabEntry.h"
#include "../../../intermediate/ICodeNode.h"

namespace wci { namespace backend { namespace compiler { namespace generators {

using namespace std;
using namespace wci::intermediate;
using namespace wci::backend::compiler;

class DeclaredRoutineGenerator : public CodeGenerator
{
public:
    /**
     * Constructor.
     * @param the parent executor.
     */
    DeclaredRoutineGenerator(CodeGenerator *parent);

    /**
     * Generate code for a declared procedure or function
     * @param routine_id the symbol table entry of the routine's name.
     * @throw a string message if an error occurred.
     */
    void generate(SymTabEntry *routine_id) throw (string);

private:
    SymTabEntry *routine_id;
    string routine_name;

    int function_value_slot;  // function return value slot number

    /**
     * Generate the routine header.
     */
    void generate_routine_header();

    /**
     * Generate directives for the local variables.
     */
    void generate_routine_locals();

    /**
     * Generate code for the routine's body.
     * @throw a string message if an error occurred.
     */
    void generate_routine_code() throw (string);

    /**
     * Generate the routine's return code.
     */
    void generate_routine_return();

    /**
     * Generate the routine's epilogue.
     */
    void generate_routine_epilogue();
};

}}}}  // namespace wci::backend::compiler::generators

#endif /* DECLAREDROUTINEGENERATOR_H_ */
