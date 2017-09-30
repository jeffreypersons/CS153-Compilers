#ifndef WHENEXECUTOR_H_
#define WHENEXECUTOR_H_

#include "StatementExecutor.h"
#include "../../../DataValue.h"
#include "../../../intermediate/ICodeNode.h"

namespace wci { namespace backend { namespace interpreter { namespace executors {

using namespace std;
using namespace wci;
using namespace wci::backend::interpreter;
using namespace wci::intermediate;

class WhenExecutor : public StatementExecutor
{
public:
    /**
     * Constructor.
     * @param the parent executor.
     */
    WhenExecutor(Executor *parent);

    /**
     * Execute a compound statement.
     * @param node the root node of the compound statement.
     * @return nullptr.
     */
    DataValue *execute(ICodeNode *node);
};

}}}}  // namespace wci::backend::interpreter::executors

#endif /* IFEXECUTOR_H_ */
