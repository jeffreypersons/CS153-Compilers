/**
 * <h1>Debugger</h1>
 *
 * <p>Interface for the interactive source-level debugger.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#ifndef WCI_BACKEND_INTERPRETER_DEBUGGER_H_
#define WCI_BACKEND_INTERPRETER_DEBUGGER_H_

#include <string>
#include <vector>
#include <set>
#include "RuntimeStack.h"
#include "../Backend.h"
#include "../../frontend/Token.h"
#include "../../frontend/pascal/PascalScanner.h"
#include "../../intermediate/SymTabEntry.h"
#include "../../backend/interpreter/debuggerimpl/NameValuePair.h"
#include "../../message/MessageListener.h"

namespace wci { namespace backend { namespace interpreter {

using namespace std;
using namespace wci;
using namespace wci::frontend;
using namespace wci::frontend::pascal;
using namespace wci::intermediate;
using namespace wci::backend;
using namespace wci::backend::interpreter;
using namespace wci::backend::interpreter::debuggerimpl;

struct StackValue
{
    wci::intermediate::SymTabEntry *symtab_entry;
    wci::backend::interpreter::debuggerimpl::NameValuePair *pair;

    StackValue(wci::intermediate::SymTabEntry *symtab_entry)
        : symtab_entry(symtab_entry), pair(nullptr)
    {}

    StackValue(wci::backend::interpreter::debuggerimpl::NameValuePair *pair)
        : symtab_entry(nullptr), pair(pair)
    {}

    ~StackValue()
    {
        if (symtab_entry != nullptr) delete symtab_entry;
        if (pair != nullptr) delete pair;
    }
};

enum class DebuggerType
{
    COMMAND_LINE, GUI,
};

constexpr DebuggerType COMMAND_LINE = DebuggerType::COMMAND_LINE;
constexpr DebuggerType GUI = DebuggerType::GUI;

class Debugger : MessageListener
{
public:
    /**
     * Constructor.
     * @param backend the back end.
     * @param runtimeStack the runtime stack.
     */
    Debugger(Backend *backend, RuntimeStack *runtime_stack);

    /**
     * Destructor.
     */
    virtual ~Debugger();

    /**
     * Getter.
     * @return the runtime stack.
     */
    RuntimeStack *get_runtime_stack() const;

    /**
     * Receive a message sent by a message producer.
     * @param message the message that was received.
     */
    void message_received(Message& message);

    /**
     * Read the debugger commands.
     */
    void read_commands();

    /**
     * Return the current token from the command input.
     * @return the token.
     * @throw a string message if an error occurred.
     */
    Token *current_token() throw (string);

    /**
     * Return the next token from the command input.
     * @param prev_token the previous token.
     * @return the token.
     * @throw a string message if an error occurred.
     */
    Token *next_token(Token *prev_token) throw (string);

    /**
     * Get the next word token from the command input.
     * @param error_message the error message if an exception is thrown.
     * @return the text of the word token.
     * @throw a string message if an error occurred.
     */
    string get_word(string error_message) throw (string);

    /**
     * Get the next int constant token from the command input.
     * @param error_message the error message if an exception is thrown.
     * @return the constant int value.
     * @throw a string message if an error occurred.
     */
    int get_integer(string error_message) throw (string);

    /**
     * Get the next constant value token from the command input.
     * @param error_message the error message if an exception is thrown.
     * @return the constant value.
     * @throw a string message if an error occurred.
     */
    DataValue *get_value(string error_message) throw (string);

    /**
     * Skip the rest of this command input line.
     * @throw a string message if an error occurred.
     */
    void skip_to_next_command() throw (string);

    /**
     * Set a breakpoint at a source line.
     * @param line_number the source line number.
     */
    void set_breakpoint(int line_number);

    /**
     * Remove a breakpoint at a source line.
     * @param line_number the source line number.
     */
    void unset_breakpoint(int line_number);

    /**
     * Check if a source line is at a breakpoint.
     * @param line_number the source line number
     * @return true if at a breakpoint, else false.
     */
    bool is_breakpoint(int line_number);

    /**
     * Set a watchpoint on a variable.
     * @param name the variable name.
     */
    void set_watchpoint(string name);

    /**
     * Remove a watchpoint on a variable.
     * @param name the variable name.
     */
    void unset_watchpoint(string name);

    /**
     * Check if a variable is a watchpoint.
     * @param name the variable name.
     * @return true if a watchpoint, else false.
     */
    bool is_watchpoint(string name);

    /**
     * Display a prompt for a debugger command.
     */
    virtual void prompt_for_command() = 0;

    /**
     * Parse a debugger command.
     * @return true to parse another command immediately, else false.
     */
    virtual bool parse_command() = 0;

    /**
     * Process a source statement.
     * @param line_number the statement line number.
     */
    virtual void at_statement(string line_number) = 0;

    /**
     * Process a breakpoint at a statement.
     * @param line_number the statement line number.
     */
    virtual void at_breakpoint(string line_number) = 0;

    /**
     * Process the current value of a watchpoint variable.
     * @param line_number the current statement line number.
     * @param name the variable name.
     * @param value_str the variable's value.
     */
    virtual void at_watchpoint_value(
                string line_number, string name, string value_str) = 0;

    /**
     * Process the assigning a new value to a watchpoint variable.
     * @param line_number the current statement line number.
     * @param name the variable name.
     * @param value_str the new value.
     */
    virtual void at_watchpoint_assignment(
                string line_number, string name, string value_str) = 0;

    /**
     * Process calling a declared procedure or function.
     * @param line_number the current statement line number.
     * @param name the routine name.
     */
    virtual void call_routine(string line_number, string routine_name) = 0;

    /**
     * Process returning from a declared procedure or function.
     * @param line_number the current statement line number.
     * @param name the routine name.
     */
    virtual void return_routine(string line_number, string routine_name) = 0;

    /**
     * Display a value.
     * @param value_string the value string.
     */
    virtual void display_value(string value_string) = 0;

    /**
     * Display the call stack.
     * @param stack the list of elements of the call stack.
     */
    virtual void display_call_stack(vector<StackValue *>& stack) = 0;

    /**
     * Terminate execution of the source program.
     */
    virtual void quit() = 0;

    /**
     * Handle a debugger command error.
     * @param error_message the error message.
     */
    virtual void command_error(string error_message) = 0;

    /**
     * Handle a source program runtime error.
     * @param error_message the error message.
     * @param line_number the source line number where the error occurred.
     */
    virtual void runtime_error(string error_message, string line_number) = 0;

    /**
     * Process a message from the back end.
     * @param message the message.
     */
    virtual void process_message(Message& message) = 0;

private:
    RuntimeStack *runtime_stack;    // runtime stack
    set<int> breakpoints;           // set of breakpoints
    set<string> watchpoints;        // set of watchpoints

    Scanner *cmd_in;  // input source for commands
};

}}}  // namespace wci::backend::interpreter

#endif /* WCI_BACKEND_INTERPRETER_DEBUGGER_H_ */
