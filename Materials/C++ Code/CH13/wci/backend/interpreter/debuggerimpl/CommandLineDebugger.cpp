/**
 * <h1>CommandLineDebugger</h1>
 *
 * <p>Command line version of the interactive source-level debugger.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#include <sstream>
#include <iomanip>
#include <string>
#include "CommandLineDebugger.h"
#include "CommandProcessor.h"
#include "NameValuePair.h"
#include "../Debugger.h"
#include "../RuntimeStack.h"
#include "../../../intermediate/SymTabEntry.h"
#include "../../../intermediate/symtabimpl/SymTabEntryImpl.h"
#include "../../Backend.h"

namespace wci { namespace backend { namespace interpreter { namespace debuggerimpl {

using namespace std;
using namespace wci::intermediate;
using namespace wci::intermediate::symtabimpl;
using namespace wci::backend;
using namespace wci::backend::interpreter;

CommandLineDebugger::CommandLineDebugger(Backend *backend,
                                         RuntimeStack *runtime_stack)
    : Debugger(backend, runtime_stack)
{
    command_processor = new CommandProcessor(this);
}

void CommandLineDebugger::prompt_for_command()
{
    cout << ">>> Command? ";
}

/**
 * Parse a debugger command.
 * @return true to parse another command immediately, else false.
 */
bool CommandLineDebugger::parse_command()
{
    return command_processor->parse_command();
}

/**
 * Process a source statement.
 * @param line_number the statement line number.
 */
void CommandLineDebugger::at_statement(string line_number)
{
    cout << endl << ">>> At line " << line_number << endl;
}

/**
 * Process a breakpoint at a statement.
 * @param line_number the statement line number.
 */
void CommandLineDebugger::at_breakpoint(string line_number)
{
    cout << endl << ">>> Breakpoint at line " << line_number << endl;
}

/**
 * Process the current value of a watchpoint variable.
 * @param line_number the current statement line number.
 * @param name the variable name.
 * @param value the variable's value.
 */
void CommandLineDebugger::at_watchpoint_value(
                    string line_number, string name, string value_str)
{
    cout << endl << ">>> At line " << line_number << ": "
         << name << ": " << value_str << endl;
}

/**
 * Process assigning a new value to a watchpoint variable.
 * @param line_number the current statement line number.
 * @param name the variable name.
 * @param value the new value.
 */
void CommandLineDebugger::at_watchpoint_assignment(
                    string line_number, string name, string value_str)
{
    cout << endl << ">>> At line " << line_number << ": "
         << name << " := " << value_str << endl;
}

/**
 * Process calling a declared procedure or function.
 * @param line_number the current statement line number.
 * @param routine_name the routine name.
 */
void CommandLineDebugger::call_routine(string line_number, string routine_name)
{
}

/**
 * Process returning from a declared procedure or function.
 * @param line_number the current statement line number.
 * @param routine_name the routine name.
 */
void CommandLineDebugger::return_routine(string line_number, string routine_name)
{
}

/**
 * Display a value.
 * @param valueString the value string.
 */
void CommandLineDebugger::display_value(string value_string)
{
    cout << value_string << endl;
}

/**
 * Display the call stack.
 * @param stack the list of elements of the call stack.
 */
void CommandLineDebugger::display_call_stack(vector<StackValue *>& stack)
{
    for (StackValue *stack_value : stack)
    {
        // Name of a procedure or function.
        if (stack_value->symtab_entry != nullptr)
        {
            SymTabEntry *routine_id = stack_value->symtab_entry;
            string routine_name = routine_id->get_name();
            int level = routine_id->get_symtab()->get_nesting_level();
            Definition defn = routine_id->get_definition();

            cout << level << ": "
                 << SymTabEntryImpl::DEFINITION_WORDS[(DefinitionImpl) defn]
                 << " " << routine_name << endl;
        }

        // Variable name-value pair.
        else if (stack_value->pair != nullptr)
        {
            NameValuePair *pair = stack_value->pair;
            cout << "  " << pair->get_variable_name() << ": ";
            display_value(pair->get_value_string());
        }
    }
}

/**
 * Terminate execution of the source program.
 */
void CommandLineDebugger::quit()
{
    cout << "Program terminated.";
    exit(-3);
}

/**
 * Handle a debugger command error.
 * @param errorMessage the error message.
 */
void CommandLineDebugger::command_error(string error_message)
{
    cout << "!!! ERROR: " << error_message << endl;
}

/**
 * Handle a source program runtime error.
 * @param errorMessage the error message.
 * @param line_number the source line number where the error occurred.
 */
void CommandLineDebugger::runtime_error(
                                string error_message, string line_number)
{
    cout << "!!! RUNTIME ERROR";

    int lnumber = stoi(line_number);
    if (lnumber > 0)
    {
        stringstream ss;
        ss << setw(3) << setfill('0') << lnumber;
        cout << " at line " << ss.str();
    }
    cout << ": " << error_message;
}

void CommandLineDebugger::process_message(Message& message)
{
    command_processor->process_message(message);
}

}}}}  // namespace wci::backend::interpreter::debuggerimpl
