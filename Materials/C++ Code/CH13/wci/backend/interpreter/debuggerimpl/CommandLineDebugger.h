/**
 * <h1>CommandLineDebugger</h1>
 *
 * <p>Command line version of the interactive source-level debugger.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#ifndef COMMANDLINEDEBUGGER_H_
#define COMMANDLINEDEBUGGER_H_

#include <string>
#include "CommandProcessor.h"
#include "../Debugger.h"
#include "../RuntimeStack.h"
#include "../../Backend.h"

namespace wci { namespace backend { namespace interpreter { namespace debuggerimpl {

using namespace std;
using namespace wci::backend;
using namespace wci::backend::interpreter;

class CommandLineDebugger : public Debugger
{
public:
    /**
     * Constructor.
     * @param backend the back end.
     * @param runtime_stack the runtime stack.
     */
    CommandLineDebugger(Backend *backend, RuntimeStack *runtime_stack);

    /**
     * Display a prompt for a debugger command.
     */
    void prompt_for_command();

    /**
     * Parse a debugger command.
     * @return true to parse another command immediately, else false.
     */
    bool parse_command();

    /**
     * Process a source statement.
     * @param line_number the statement line number.
     */
    void at_statement(string line_number);

    /**
     * Process a breakpoint at a statement.
     * @param line_number the statement line number.
     */
    void at_breakpoint(string line_number);

    /**
     * Process the current value of a watchpoint variable.
     * @param line_number the current statement line number.
     * @param name the variable name.
     * @param value_str the variable's value.
     */
    void at_watchpoint_value(
                    string line_number, string name, string value_str);

    /**
     * Process assigning a new value to a watchpoint variable.
     * @param line_number the current statement line number.
     * @param name the variable name.
     * @param value_str the new value.
     */
    void at_watchpoint_assignment(
                    string line_number, string name, string value_str);

    /**
     * Process calling a declared procedure or function.
     * @param line_number the current statement line number.
     * @param routine_name the routine name.
     */
    void call_routine(string line_number, string routine_name);

    /**
     * Process returning from a declared procedure or function.
     * @param line_number the current statement line number.
     * @param routine_name the routine name.
     */
    void return_routine(string line_number, string routine_name);

    /**
     * Display a value.
     * @param value_string the value string.
     */
    void display_value(string value_string);

    /**
     * Display the call stack.
     * @param stack the list of elements of the call stack.
     */
    void display_call_stack(vector<StackValue *>& stack);

    /**
     * Terminate execution of the source program.
     */
    void quit();

    /**
     * Handle a debugger command error.
     * @param error_message the error message.
     */
    void command_error(string error_message);

    /**
     * Handle a source program runtime error.
     * @param error_message the error message.
     * @param line_number the source line number where the error occurred.
     */
    void runtime_error(string error_message, string line_number);

    /**
     * Process a message from the back end.
     * @param message the message.
     */
    void process_message(Message &message);

private:
    CommandProcessor *command_processor;
};

}}}}  // namespace wci::backend::interpreter::debuggerimpl

#endif /* COMMANDLINEDEBUGGER_H_ */
