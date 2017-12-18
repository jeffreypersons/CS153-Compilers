/**
 * <h1>CommandProcessor</h1>
 *
 * <p>Command processor for the interactive source-level debugger-></p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#include <string>
#include <vector>
#include "CommandProcessor.h"
#include "CellTypePair.h"
#include "NameValuePair.h"
#include "../Debugger.h"
#include "../../../intermediate/SymTab.h"
#include "../../../intermediate/SymTabEntry.h"
#include "../../../intermediate/symtabimpl/SymTabEntryImpl.h"
#include "../../../backend/interpreter/Cell.h"
#include "../../../backend/interpreter/ActivationRecord.h"
#include "../../../backend/interpreter/memoryimpl/ActivationRecordImpl.h"
#include "../../../backend/interpreter/RuntimeStack.h"
#include "../../../message/Message.h"

namespace wci { namespace backend { namespace interpreter { namespace debuggerimpl {

using namespace std;
using namespace wci::intermediate;
using namespace wci::intermediate::symtabimpl;
using namespace wci::backend::interpreter;
using namespace wci::backend::interpreter::memoryimpl;
using namespace wci::message;

CommandProcessor::CommandProcessor(Debugger *debugger)
    : debugger(debugger), stepping(true)
{
}

void CommandProcessor::process_message(Message &message)
{
    MessageType message_type = message.get_type();

    switch (message_type)
    {
        case AT_LINE:
        {
            string line_number = message[LINE_NUMBER];

            if (stepping)
            {
                debugger->at_statement(line_number);
                debugger->read_commands();
            }
            else if (debugger->is_breakpoint(stoi(line_number)))
            {
                debugger->at_breakpoint(line_number);
                debugger->read_commands();
            }

            break;
        }

        case FETCH:
        {
            string line_number   = message[LINE_NUMBER];
            string variable_name = message[VARIABLE_NAME];
            string value_str     = message[RESULT_VALUE];

            if (debugger->is_watchpoint(variable_name))
            {
                string line_number   = message[LINE_NUMBER];
                string value_str     = message[RESULT_VALUE];

                debugger->at_watchpoint_value(
                                line_number, variable_name, value_str);
            }

            break;
        }

        case ASSIGN:
        {
            string variable_name = message[VARIABLE_NAME];

            if (debugger->is_watchpoint(variable_name))
            {
                string line_number   = message[LINE_NUMBER];
                string value_str     = message[RESULT_VALUE];

                debugger->at_watchpoint_assignment(
                                line_number, variable_name, value_str);
            }

            break;
        }

        case CALL:
        {
            string line_number  = message[LINE_NUMBER];
            string routine_name = message[VARIABLE_NAME];

            debugger->call_routine(line_number, routine_name);
            break;
        }

        case RETURN:
        {
            string line_number   = message[LINE_NUMBER];
            string routine_name = message[VARIABLE_NAME];

            debugger->return_routine(line_number, routine_name);
            break;
        }

        case RUNTIME_ERROR:
        {
            string line_number   = message[LINE_NUMBER];
            string error_message = message[ERROR_MESSAGE];

            debugger->runtime_error(error_message, line_number);
            break;
        }

        default: break;  // should never get here
    }
}

bool CommandProcessor::parse_command()
{
    bool another_command = true;

    // Parse a command.
    try {
        debugger->next_token(nullptr);
        string command = debugger->get_word("Command expected.");
        another_command = execute_command(command);
    }
    catch (string &message)
    {
        debugger->command_error(message);
    }

    // Skip to the next command.
    try {
        debugger->skip_to_next_command();
    }
    catch (string &message)
    {
        debugger->command_error(message);
    }

    return another_command;
}

bool CommandProcessor::execute_command(string command) throw (string)
{
    stepping = false;

    if (command == "step")
    {
        stepping = true;
        check_for_semicolon();
        return false;
    }

    if (command == "break")
    {
        int line_number = debugger->get_integer("Line number expected.");
        check_for_semicolon();
        debugger->set_breakpoint(line_number);
        return true;
    }

    if (command == "unbreak")
    {
        int line_number = debugger->get_integer("Line number expected.");
        check_for_semicolon();
        debugger->unset_breakpoint(line_number);
        return true;
    }

    if (command == "watch")
    {
        string name = debugger->get_word("Variable name expected.");
        check_for_semicolon();
        debugger->set_watchpoint(name);
        return true;
    }

    if (command == "unwatch")
    {
        string name = debugger->get_word("Variable name expected.");
        check_for_semicolon();
        debugger->unset_watchpoint(name);
        return true;
    }

    if (command == "stack")
    {
        check_for_semicolon();
        stack();
        return true;
    }

    if (command == "show")
    {
        show();
        return true;
    }

    if (command == "assign")
    {
        assign();
        return true;
    }

    if (command == "go")
    {
        check_for_semicolon();
        return false;
    }

    if (command == "quit")
    {
        check_for_semicolon();
        debugger->quit();
    }

    throw string("Invalid command: '" + command + "'.");
}

void CommandProcessor::stack()
{
    vector<StackValue *> call_stack;
    RuntimeStack *runtime_stack = debugger->get_runtime_stack();
    vector<ActivationRecord *> ar_list = runtime_stack->records();

    // Loop over the activation records on the runtime stack
    // starting at the top of stack.
    for (int i = ar_list.size() - 1; i >= 0; --i)
    {
        ActivationRecord *ar = ar_list[i];
        ActivationRecordImpl *ar_impl = (ActivationRecordImpl *) ar;
        SymTabEntry *routine_id = ar_impl->get_routine_id();

        // Add the symbol table entry of the procedure or function.
        call_stack.push_back(new StackValue(routine_id));

        // Create and add a name-value pair for each local variable.
        for (string name : ar_impl->get_all_names())
        {
            CellValue *cell_value = ar_impl->get_cell(name)->get_value();
            call_stack.push_back(
                    new StackValue(new NameValuePair(name, cell_value)));
        }
    }

    // Display the call stack.
    debugger->display_call_stack(call_stack);
}

void CommandProcessor::show() throw (string)
{
    CellTypePair *pair = create_cell_type_pair();
    Cell *cell = pair->get_cell();

    check_for_semicolon();
    debugger->display_value(
                    NameValuePair::value_to_string(cell->get_value()));
}

void CommandProcessor::assign() throw (string)
{
    CellTypePair *pair = create_cell_type_pair();
    DataValue *new_value = debugger->get_value("Invalid value.");

    check_for_semicolon();
    pair->set_value(new CellValue(new_value));
}

CellTypePair *CommandProcessor::create_cell_type_pair() throw (string)
{
    RuntimeStack *runtime_stack = debugger->get_runtime_stack();
    int current_level = runtime_stack->current_nesting_level();
    ActivationRecordImpl *ar = nullptr;
    Cell *cell = nullptr;

    // Parse the variable name.
    string variable_name = debugger->get_word("Variable name expected.");

    // Find the variable's cell in the call stack.
    for (int level = current_level; (cell == nullptr) && (level > 0); --level)
    {
        ar = (ActivationRecordImpl *) runtime_stack->get_topmost(level);
        cell = ar->get_cell(variable_name);
    }

    if (cell == nullptr)
    {
        throw string("Undeclared variable name '" + variable_name + "'.");
    }

    // VAR parameter.
    if (cell->get_value()->cell != nullptr)
    {
        cell = cell->get_value()->cell;
    }

    // Find the variable's symbol table entry.
    SymTabEntry *routine_id = ar->get_routine_id();
    SymTab *symtab =
        routine_id->get_attribute((SymTabKey) ROUTINE_SYMTAB)->symtab;
    SymTabEntry *variable_id = symtab->lookup(variable_name);

    return new CellTypePair(variable_id->get_typespec(), cell, debugger);
}

void CommandProcessor::check_for_semicolon() throw (string)
{
    if (debugger->current_token()->get_type() != (TokenType) PT_SEMICOLON)
    {
        throw string("Invalid command syntax.");
    }
}

}}}}  // namespace wci::backend::interpreter::debuggerimpl
