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

enum{
    EXPRESSION = 0,
    STATEMENT = EXPRESSION + 1,
};

WhenExecutor::WhenExecutor(Executor *parent)
    : StatementExecutor(parent)
{
}

DataValue *WhenExecutor::execute(ICodeNode *node)
{
    // NOTE: compiler quirk of g++: unlike visual c++, the compiler does less inferring for us in g++
    // that means that we have to explicitly specify type name AND a 'nested' scoped expression (std::)
    // otherwise it will fail to compile as they are dependent names.
    // SEE: https://stackoverflow.com/questions/13897200/iterator-for-vector-of-pointers-error-expected?noredirect=1&lq=1
    typename std::vector<ICodeNode*> children = node->get_children();
    typename std::vector<ICodeNode*> children_of_children;

    ICodeNode* exec_node; // node to execute
    DataValue* d_val;
    ExpressionExecutor branch_expression(this);
    StatementExecutor executor(this);
    int OTHERWISE = children.size() - 1;
    bool execute_statement = false;

    // proceed through all children until a true statement is found
    for(int x = 0; x < children.size(); x++){
        // get child of children. Needed because statements are always compound - to be safe.
        children_of_children = children[x]->get_children();
        // set to exec node
        exec_node = children_of_children[0];
        if(x == OTHERWISE) executor.execute(exec_node); // means otherwise statement has been reached
        
        // test if expression or statement. Since format is expression, statement, expression etc.
        // odd is statement, even expression. Used simple bitmask for readability
        else if((x & 0x1) == EXPRESSION)
        {
            d_val = branch_expression.execute(exec_node);
            execute_statement = d_val->b;
        }
        else if((x & 0X1)  == STATEMENT)
        {
            if(execute_statement)
            {
                executor.execute(exec_node);
                break;
            }
            else delete d_val; // reassignemnt will be needed, remove old d_val - not needed anymore
        }
    }

    ++execution_count;  // count the WHEN statement itself
    return nullptr;
}

}}}}  // namespace wci::backend::interpreter::executors