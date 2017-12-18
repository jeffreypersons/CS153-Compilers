/**
 * <h1>StatementGenerator</h1>
 *
 * <p>Generate code for a statement.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#include <string>
#include "StatementGenerator.h"
#include "CompoundGenerator.h"
#include "AssignmentGenerator.h"
#include "../CodeGenerator.h"
#include "../Directive.h"
#include "../../../intermediate/ICodeNode.h"
#include "../../../intermediate/icodeimpl/ICodeNodeImpl.h"

namespace wci { namespace backend { namespace compiler { namespace generators {

using namespace std;
using namespace wci::intermediate;
using namespace wci::intermediate::icodeimpl;
using namespace wci::backend::compiler;

StatementGenerator::StatementGenerator(CodeGenerator *parent)
    : CodeGenerator(parent)
{
}

void StatementGenerator::generate(ICodeNode *node) throw (string)
{
    ICodeNodeTypeImpl node_type = (ICodeNodeTypeImpl) node->get_type();
    int line = 0;

    if (node_type != NT_COMPOUND)
    {
        line = get_line_number(node);
        emit_directive(DirCode::LINE, line);
    }

    // Generate code for a statement according to the type of statement.
    switch (node_type)
    {
        case NT_COMPOUND:
        {
            CompoundGenerator compound_generator(this);
            compound_generator.generate(node);
            break;
        }

        case NT_ASSIGN:
        {
            AssignmentGenerator assignment_generator(this);
            assignment_generator.generate(node);
            break;
        }

        default: break;  // should never get here
    }

    // Verify that the stack height after each statement is 0.
    if (local_stack->get_size() != 0)
    {
        throw string("Stack size error: size = " +
                     to_string(local_stack->get_size()) +
                     " after line " + to_string(line));
    }
}

int StatementGenerator::get_line_number(ICodeNode *node) const
{
    DataValue *value = nullptr;

    // Go up the parent links to look for a line number.
    while (   (node != nullptr)
           && ((value = node
                           ->get_attribute((ICodeKey) ICodeKeyImpl::LINE)
                                ->value)
                   == nullptr))
    {
        node = node->get_parent();
    }

    return value->i;
}

}}}}  // namespace wci::backend::compiler::generators
