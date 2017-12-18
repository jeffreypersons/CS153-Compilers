/**
 * <h1>CompoundGenerator</h1>
 *
 * <p>Generate code for a compound statement.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#ifndef COMPOUNDGENERATOR_H_
#define COMPOUNDGENERATOR_H_

#include <string>
#include "StatementGenerator.h"
#include "../CodeGenerator.h"
#include "../../../intermediate/ICodeNode.h"

namespace wci { namespace backend { namespace compiler { namespace generators {

using namespace std;
using namespace wci::intermediate;
using namespace wci::backend::compiler;

class CompoundGenerator : public StatementGenerator
{
public:
    /**
     * Constructor.
     * @param the parent executor.
     */
    CompoundGenerator(CodeGenerator *parent);

    /**
     * Generate code for a statement.
     * To be overridden by the specialized statement executor subclasses.
     * @param node the root node of the statement.
     * @throw a string message if an error occurred.
     */
    void generate(ICodeNode *node) throw (string);
};

}}}}  // namespace wci::backend::compiler::generators

#endif /* COMPOUNDGENERATOR_H_ */
