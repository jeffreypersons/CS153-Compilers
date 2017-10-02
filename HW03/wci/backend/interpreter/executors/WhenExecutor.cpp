#include <vector>
#include "WhenExecutor.h"
#include "StatementExecutor.h"
#include "ExpressionExecutor.h"
#include "../../../DataValue.h"
#include "../../../intermediate/ICodeNode.h"
#include "../../../intermediate/icodeimpl/ICodeNodeImpl.h"

namespace wci { namespace backend { namespace interpreter { namespace executors {

using namespace std;
using namespace wci;
using namespace wci::backend::interpreter;
using namespace wci::intermediate;
using namespace wci::intermediate::icodeimpl;

WhenExecutor::WhenExecutor(Executor *parent)
    : StatementExecutor(parent)
{
}

DataValue *WhenExecutor::execute(ICodeNode *node)
{
    // Get the IF node's children.

    // NOTE: compiler quirk of g++: unlike visual c++, the compiler does less inferring for us in g++
    // that means that we have to explicitly specify type name AND a 'nested' scoped expression (std::)
    // otherwise it will fail to compile as they are dependent names.
    // SEE: https://stackoverflow.com/questions/13897200/iterator-for-vector-of-pointers-error-expected?noredirect=1&lq=1
    typename std::vector<ICodeNode*> children = node->get_children();
    typename std::vector<ICodeNode*> children_of_children = children[0]->get_children();

    ICodeNode* expr_child = children[0];
    ICodeNode* statement_child = children[1];
    ExpressionExecutor branch_expression(this);
    StatementExecutor executor(this);

    DataValue* d_val = branch_expression.execute(children_of_children[0]);
    if(d_val->b)
    {
        //cout << "true" << endl;
        executor.execute(children[1]);
    }

    ++execution_count;  // count the IF statement itself
    return nullptr;
}

}}}}  // namespace wci::backend::interpreter::executors