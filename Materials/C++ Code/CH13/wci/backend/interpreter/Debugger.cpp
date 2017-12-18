/**
 * <h1>Debugger</h1>
 *
 * <p>Interface for the interactive source-level debugger.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#include <iostream>
#include <string>
#include <vector>
#include <set>
#include <algorithm>
#include "Debugger.h"
#include "RuntimeStack.h"
#include "../Backend.h"
#include "../../frontend/Token.h"
#include "../../frontend/Scanner.h"
#include "../../frontend/Source.h"
#include "../../frontend/pascal/PascalScanner.h"
#include "../../frontend/pascal/PascalToken.h"
#include "../../message/MessageListener.h"
#include "../../DataValue.h"

namespace wci { namespace backend { namespace interpreter {

using namespace std;
using namespace wci;
using namespace wci::frontend;
using namespace wci::frontend::pascal;
using namespace wci::backend;

Debugger::Debugger(Backend *backend, RuntimeStack *runtime_stack)
    : runtime_stack(runtime_stack)
{
    backend->add_message_listener(this);
    cmd_in  = new PascalScanner(new Source(cin));
}

Debugger::~Debugger()
{
}

RuntimeStack *Debugger::get_runtime_stack() const { return runtime_stack; }

void Debugger::message_received(Message& message)
{
    process_message(message);
}

void Debugger::read_commands()
{
    do
    {
        prompt_for_command();
    } while (parse_command());
}

Token *Debugger::current_token() throw (string)
{
    return cmd_in->current_token();
}

Token *Debugger::next_token(Token *prev_token) throw (string)
{
    return cmd_in->next_token(prev_token);
}

string Debugger::get_word(string error_message) throw (string)
{
    Token *token = current_token();
    TokenType token_type = token->get_type();

    if (token_type == (TokenType) PT_IDENTIFIER)
    {
        string word = token->get_text();
        transform(word.begin(), word.end(), word.begin(), ::tolower);
        next_token(token);  // consume word
        return word;
    }
    else
    {
        throw error_message;
    }
}

int Debugger::get_integer(string error_message) throw (string)
{
    Token *token = current_token();
    TokenType token_type = token->get_type();

    if (token_type == (TokenType) PT_INTEGER)
    {
        int i = token->get_value()->i;
        next_token(token);  // consume number
        return i;
    }
    else
    {
        throw error_message;
    }
}

DataValue *Debugger::get_value(string error_message) throw (string)
{
    Token *token = current_token();
    TokenType token_type = token->get_type();
    bool plus_sign  = token_type == (TokenType) PT_PLUS;
    bool minus_sign = token_type == (TokenType) PT_MINUS;
    bool leading_sign = plus_sign || minus_sign;

    // Unary plus or minus sign.
    if (leading_sign)
    {
        token = next_token(token);     // consume sign
        token_type = token->get_type();
    }

    switch ((PascalTokenType) token_type)
    {
        case PT_INTEGER:
        {
            int i = token->get_value()->i;
            next_token(token);
            return new DataValue(minus_sign ? -i : i);
        }

        case PT_REAL:
        {
            float f = token->get_value()->f;
            next_token(token);
            return new DataValue(minus_sign ? -f : f);
        }

        case PT_STRING:
        {
            if (leading_sign)
            {
                throw error_message;
            }
            else
            {
                string s = token->get_value()->s;
                next_token(token);
                return new DataValue(s[0]);
            }
        }

        case PT_IDENTIFIER:
        {
            if (leading_sign)
            {
                throw error_message;
            }
            else
            {
                string text = token->get_text();
                transform(text.begin(), text.end(), text.begin(), ::tolower);
                next_token(token);

                if      (text == "true")  return new DataValue(true);
                else if (text == "false") return new DataValue(false);
                else throw error_message;
            }
        }

        default: throw error_message;
    }
}

/**
 * Skip the rest of this command input line.
 * @throws Exception if an error occurred.
 */
void Debugger::skip_to_next_command() throw (string)
{
    cmd_in->skip_to_next_line();
}

/**
 * Set a breakpoint at a source line.
 * @param line_number the source line number.
 */
void Debugger::set_breakpoint(int line_number)
{
    breakpoints.insert(line_number);
}

void Debugger::unset_breakpoint(int line_number)
{
    breakpoints.erase(line_number);
}

bool Debugger::is_breakpoint(int line_number)
{
    return breakpoints.find(line_number) != breakpoints.end();
}

void Debugger::set_watchpoint(string name)
{
    transform(name.begin(), name.end(), name.begin(), ::tolower);
    watchpoints.insert(name);
}

void Debugger::unset_watchpoint(string name)
{
    transform(name.begin(), name.end(), name.begin(), ::tolower);
    watchpoints.erase(name);
}

bool Debugger::is_watchpoint(string name)
{
    transform(name.begin(), name.end(), name.begin(), ::tolower);
    return watchpoints.find(name) != watchpoints.end();
}

}}}  // namespace wci::backend::interpreter
