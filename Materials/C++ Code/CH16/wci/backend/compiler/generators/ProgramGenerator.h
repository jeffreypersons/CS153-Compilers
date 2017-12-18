/**
 * <h1>ProgramGenerator</h1>
 *
 * <p>Generate code for the main program.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#ifndef PROGRAMGENERATOR_H_
#define PROGRAMGENERATOR_H_

#include <string>
#include <set>
#include "../CodeGenerator.h"
#include "../../../intermediate/SymTabEntry.h"
#include "../../../intermediate/ICodeNode.h"

namespace wci { namespace backend { namespace compiler { namespace generators {

using namespace std;
using namespace wci::intermediate;
using namespace wci::backend::compiler;

class ProgramGenerator : public CodeGenerator
{
public:
    /**
     * Constructor.
     * @param the parent executor.
     */
    ProgramGenerator(CodeGenerator *parent);

    /**
     * Generate code to evaluate an expression.
     * @throw a string message if an error occurred.
     */
    void generate(ICodeNode *node) throw (string);

private:
    SymTabEntry *program_id;
    string program_name;

    /**
     * Generate directives for the fields.
     */
    void generate_fields();

    /**
     * Generate code for the main program constructor.
     */
    void generate_constructor();

    /**
     * Generate code for any nested procedures and functions.
     * @throw a string message if an error occurred.
     */
    void generate_routines() throw (string);

    /**
     * Generate code for the program body as the main method.
     * @throw a string message if an error occurred.
     */
    void generate_main_method() throw (string);

    /**
     * Generate the main method prologue.
     */
    void generate_main_method_prologue();

    /**
     * Generate code for the main method.
     */
    void generate_main_method_code() throw (string);

    /**
     * Generate the main method epilogue.
     */
    void generate_main_method_epilogue();
};

}}}}  // namespace wci::backend::compiler::generators

#endif /* PROGRAMGENERATOR_H_ */
