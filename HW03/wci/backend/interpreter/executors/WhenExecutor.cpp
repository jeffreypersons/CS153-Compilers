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
    vector<ICodeNode *> children = node->get_children();
    vector<ICodeNode*> children_of_children = children[0]->get_children();
    ICodeNode* expr_child = children[0];
    ICodeNode* statement_child = children[1];
    ExpressionExecutor branch_expression(this);
    StatementExecutor executor(this);

    DataValue* d_val = branch_expression.execute(children_of_children[0]);
    if(d_val->b) executor.execute(children[1]);
    /*if(d_val->b){
        cout << "true" << endl;
    }*/
    
    ++execution_count;  // count the IF statement itself*/
    return nullptr;
}

}}}}  // namespace wci::backend::interpreter::executors