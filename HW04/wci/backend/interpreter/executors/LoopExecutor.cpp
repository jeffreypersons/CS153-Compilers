/**
 * <h1>LoopExecutor</h1>
 *
 * <p>Execute a loop statement.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#include <vector>
#include "LoopExecutor.h"
#include "StatementExecutor.h"
#include "ExpressionExecutor.h"
#include "../Cell.h"
#include "../../../intermediate/ICodeNode.h"
#include "../../../intermediate/icodeimpl/ICodeNodeImpl.h"

namespace wci { namespace backend { namespace interpreter { namespace executors {

using namespace std;
using namespace wci;
using namespace wci::backend::interpreter;
using namespace wci::intermediate;
using namespace wci::intermediate::icodeimpl;

LoopExecutor::LoopExecutor(Executor *parent)
    : StatementExecutor(parent)
{
}

CellValue *LoopExecutor::execute(ICodeNode *node)
{
    bool exit_loop = false;
    ICodeNode *expr_node = nullptr;
    vector<ICodeNode *> loop_children = node->get_children();

    ExpressionExecutor expression_executor(this);
    StatementExecutor statement_executor(this);

    // Loop until the TEST expression value is true.
    while (!exit_loop)
    {
        ++execution_count;  // count the loop statement itself

        // Execute the children of the LOOP node.
        for (ICodeNode *child : loop_children) {
            ICodeNodeTypeImpl child_type =
                                  (ICodeNodeTypeImpl) child->get_type();

            // TEST node?
            if (child_type == NT_TEST)
            {
                if (expr_node == nullptr)
                {
                    expr_node = child->get_children()[0];
                }

                CellValue *cell_value =
                                expression_executor.execute(expr_node);
                DataValue *data_value = cell_value->value;
                exit_loop = data_value->b;
                delete cell_value;
            }

            // Statement node.
            else
            {
                statement_executor.execute(child);
            }

            // Exit if the TEST expression value is true,
            if (exit_loop)  break;
        }
    }

    return nullptr;
}

}}}}  // namespace wci::backend::interpreter::executors
