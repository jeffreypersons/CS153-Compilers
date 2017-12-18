/**
 * <h1>PascalParserTD</h1>
 *
 * <p>The top-down Pascal parser.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#include <algorithm>
#include <chrono>
#include "PascalParserTD.h"

#include "../Scanner.h"
#include "../Source.h"
#include "../Token.h"
#include "PascalToken.h"
#include "PascalError.h"
#include "../../intermediate/SymTabEntry.h"
#include "../../message/Message.h"

namespace wci { namespace frontend { namespace pascal {

using namespace std;
using namespace std::chrono;
using namespace wci::frontend;
using namespace wci::message;

PascalErrorHandler PascalParserTD::error_handler;

PascalParserTD::PascalParserTD(Scanner *scanner) : Parser(scanner)
{
    PascalError::initialize();
}

PascalParserTD::PascalParserTD(PascalParserTD *parent)
    : Parser(parent->get_scanner())
{
}

void PascalParserTD::parse() throw (string)
{
    Token *token = nullptr;
    int last_line_number;
    steady_clock::time_point start_time = steady_clock::now();

    // Loop over each token until the end of file.
    while ((token = next_token(token)) != nullptr)
    {
        TokenType token_type = token->get_type();
        last_line_number = token->get_line_number();

        string type_str;
        string value_str;

        // Cross reference only the identifiers.
        if (token_type == (TokenType) PT_IDENTIFIER)
        {
            string name = token->get_text();
            transform(name.begin(), name.end(), name.begin(), ::tolower);

            // If it's not already in the symbol table,
            // create and enter a new entry for the identifier.
            SymTabEntry *entry = symtab_stack->lookup(name);
            if (entry == nullptr) entry = symtab_stack->enter_local(name);

            // Append the current line number to the entry.
            entry->append_line_number(token->get_line_number());
        }
        else if (token_type == (TokenType) PT_ERROR)
        {
            PascalErrorCode error_code =
                                (PascalErrorCode) token->get_value()->i;
            error_handler.flag(token, error_code, this);
        }
    }

    // Send the parser summary message.
    steady_clock::time_point end_time = steady_clock::now();
    double elapsed_time =
            duration_cast<duration<double>>(end_time - start_time).count();
    Message message(PARSER_SUMMARY,
                    LINE_COUNT, to_string(last_line_number),
                    ERROR_COUNT, to_string(get_error_count()),
                    ELAPSED_TIME, to_string(elapsed_time));
    send_message(message);
}

int PascalParserTD::get_error_count() const
{
    return error_handler.get_error_count();
}

}}} // namespace wci::frontend::pascal
