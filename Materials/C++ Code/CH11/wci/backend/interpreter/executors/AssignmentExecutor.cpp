/**
 * <h1>AssignmentExecutor</h1>
 *
 * <p>Execute an assignment statement.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#include <string>
#include "AssignmentExecutor.h"
#include "StatementExecutor.h"
#include "ExpressionExecutor.h"
#include "../../../DataValue.h"
#include "../../../intermediate/ICodeNode.h"
#include "../../../intermediate/icodeimpl/ICodeNodeImpl.h"
#include "../../../intermediate/SymTabEntry.h"
#include "../../../intermediate/symtabimpl/SymTabEntryImpl.h"
#include "../../../message/Message.h"

namespace wci { namespace backend { namespace interpreter { namespace executors {

using namespace std;
using namespace wci;
using namespace wci::backend::interpreter;
using namespace wci::intermediate;
using namespace wci::intermediate::symtabimpl;
using namespace wci::message;

AssignmentExecutor::AssignmentExecutor(Executor *parent)
    : StatementExecutor(parent)
{
}

DataValue *AssignmentExecutor::execute(ICodeNode *node)
{
    // The ASSIGN node's children are the target variable
    // and the expression.
    vector<ICodeNode *> children = node->get_children();
    ICodeNode *variable_node = children[0];
    ICodeNode *expression_node = children[1];

    // Execute the expression and get its value.
    ExpressionExecutor expression_executor(this);
    DataValue *result_value = expression_executor.execute(expression_node);

    // Set the value as an attribute of the
    // target variable's symbol table entry.
    NodeValue *node_value = variable_node->get_attribute((ICodeKey) ID);
    SymTabEntry *id = node_value->id;
    id->set_attribute((SymTabKey) DATA_VALUE, new EntryValue(result_value));

    // Send a message about the assignment.
    send_assignment_message(node, id->get_name(), result_value);

    ++execution_count;
    return nullptr;
}

void AssignmentExecutor::send_assignment_message(ICodeNode *node,
                                                 string variable_name,
                                                 DataValue *data_value)
{
    NodeValue *node_value =
                      node->get_attribute((ICodeKey) LINE);
    if (node_value != nullptr)
    {
        int line_number = node_value->value->i;

        // Send an ASSIGN message.
        string value_str = data_value->display();
        Message message(ASSIGN,
                        LINE_NUMBER, to_string(line_number),
                        VARIABLE_NAME, variable_name,
                        RESULT_VALUE, value_str);
        send_message(message);
    }
}

}}}}  // namespace wci::backend::interpreter::executors
