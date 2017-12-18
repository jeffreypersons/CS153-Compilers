/**
 * <h1>CommandProcessor</h1>
 *
 * <p>Command processor for the interactive source-level debugger-></p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#ifndef COMMANDPROCESSOR_H_
#define COMMANDPROCESSOR_H_

#include <string>
#include "CellTypePair.h"
#include "../Debugger.h"
#include "../../../message/Message.h"

namespace wci { namespace backend { namespace interpreter { namespace debuggerimpl {

using namespace std;
using namespace wci::backend::interpreter;

class CommandProcessor
{
public:
    /**
     * Constructor.
     * @param debugger the parent debugger->
     */
    CommandProcessor(Debugger *debugger);

    /**
     * Process a message from the back end.
     * @param message the message.
     */
    void process_message(Message &message);

    /**
     * Parse a debugger command.
     * @return true to parse another command immediately, else false.
     */
    bool parse_command();

private:
    Debugger *debugger;  // the debugger
    bool stepping;       // true when single stepping

    /**
     * Execute a debugger command.
     * @param command the command string.
     * @return true to parse another command immediately, else false.
     * @throw a string message if an error occurred.
     */
    bool execute_command(string command) throw (string);

    /**
     * Create the call stack for display.
     */
    void stack();

    /**
     * Show the current value of a variable.
     * @throw a string message if an error occurred.
     */
    void show() throw (string);

    /**
     * Assign a new value to a variable.
     * @throw (string) if an error occurred.
     */
    void assign() throw (string);

    /**
     * Create a cell-data type pair.
     * @return the CellTypePair object.
     * @throw a string message if an error occurred.
     */
    CellTypePair *create_cell_type_pair() throw (string);

    /**
     * Verify that a command ends with a semicolon.
     * @throw a string message if an error occurred.
     */
    void check_for_semicolon() throw (string);
};

}}}}  // namespace wci::backend::interpreter::debuggerimpl

#endif /* COMMANDPROCESSOR_H_ */
