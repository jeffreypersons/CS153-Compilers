/**
 * <h1>StatementGenerator</h1>
 *
 * <p>Generate code for a statement.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#ifndef STATEMENTGENERATOR_H_
#define STATEMENTGENERATOR_H_

#include <string>
#include "../CodeGenerator.h"
#include "../../../intermediate/ICodeNode.h"

namespace wci { namespace backend { namespace compiler { namespace generators {

using namespace std;
using namespace wci::intermediate;
using namespace wci::backend::compiler;

class StatementGenerator : public CodeGenerator
{
public:
    /**
     * Constructor.
     * @param the parent executor.
     */
    StatementGenerator(CodeGenerator *parent);

    /**
     * Generate code for a statement.
     * To be overridden by the specialized statement executor subclasses.
     * @param node the root node of the statement.
     * @throw a string message if an error occurred.
     */
    void generate(ICodeNode *node) throw (string);

private:
    /**
     * Get the source line number of a parse tree node.
     * @param node the parse tree node.
     * @return the line number.
     */
    int get_line_number(ICodeNode *node) const;
};

}}}}  // namespace wci::backend::compiler::generators

#endif /* STATEMENTGENERATOR_H_ */
