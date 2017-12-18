/**
 * <h1>CompoundGenerator</h1>
 *
 * <p>Generate code for a compound statement.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#include <string>
#include <vector>
#include "CompoundGenerator.h"
#include "StatementGenerator.h"
#include "../CodeGenerator.h"
#include "../../../intermediate/ICodeNode.h"

namespace wci { namespace backend { namespace compiler { namespace generators {

using namespace std;
using namespace wci::intermediate;
using namespace wci::backend::compiler;

CompoundGenerator::CompoundGenerator(CodeGenerator *parent)
    : StatementGenerator(parent)
{
}

/**
 * Generate code for a compound statement.
 * @param node the root node of the compound statement.
 */
void CompoundGenerator::generate(ICodeNode *node) throw (string)
{
    vector<ICodeNode *> children = node->get_children();

    // Loop over the statement children of the COMPOUND node and generate
    // code for each statement. Emit a NOP is there are no statements.
    if (children.size() == 0)
    {
        emit(NOP);
    }
    else
    {
        StatementGenerator statement_generator(this);

        for (ICodeNode *child : children)
        {
            statement_generator.generate(child);
        }
    }
}

}}}}  // namespace wci::backend::compiler::generators
