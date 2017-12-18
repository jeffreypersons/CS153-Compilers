/**
 * <h1>StatementParser</h1>
 *
 * <p>Parse a Pascal statement.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#include <string>
#include "StatementParser.h"
#include "CompoundStatementParser.h"
#include "AssignmentStatementParser.h"
#include "../PascalParserTD.h"
#include "../PascalToken.h"
#include "../PascalError.h"
#include "../../Token.h"
#include "../../../intermediate/ICodeNode.h"
#include "../../../intermediate/ICodeFactory.h"
#include "../../../intermediate/icodeimpl/ICodeNodeImpl.h"

namespace wci { namespace frontend { namespace pascal { namespace parsers {

using namespace std;
using namespace wci::frontend::pascal;
using namespace wci::intermediate;
using namespace wci::intermediate::icodeimpl;

StatementParser::StatementParser(PascalParserTD *parent)
    : PascalParserTD(parent)
{
}

ICodeNode *StatementParser::parse_statement(Token *token) throw (string)
{
    ICodeNode *statement_node = nullptr;
    int line_number = token->get_line_number();

    switch ((PascalTokenType) token->get_type())
    {
        case PT_BEGIN:
        {
            CompoundStatementParser compound_parser(this);
            statement_node = compound_parser.parse_statement(token);
            break;
        }

        // An assignment statement begins with a variable's identifier.
        case PT_IDENTIFIER:
        {
            AssignmentStatementParser assignment_parser(this);
            statement_node = assignment_parser.parse_statement(token);
            break;
        }

        default:
        {
            statement_node =
                ICodeFactory::create_icode_node(
                        (ICodeNodeType) ICodeNodeTypeImpl::NO_OP);
            break;
        }
    }

    set_line_number(statement_node, line_number);
    return statement_node;
}

void StatementParser::set_line_number(ICodeNode *node, int line_number)
{
    if (node != nullptr)
    {
        NodeValue *node_value = new NodeValue();
        node_value->value = new DataValue(line_number);
        node->set_attribute((ICodeKey) ICodeKeyImpl::LINE, node_value);
    }
}

void StatementParser::parse_list(Token *token, ICodeNode *parent_node,
                                 const PascalTokenType terminator,
                                 const PascalErrorCode error_code)
    throw (string)
{
    // Loop to parse each statement until the END token
    // or the end of the source file.
    while (   (token != nullptr)
           && (token->get_type() != (TokenType) terminator))
    {
        // Parse a statement.  The parent node adopts the statement node.
        ICodeNode *statement_node = parse_statement(token);
        parent_node->add_child(statement_node);

        token = current_token();
        TokenType token_type = token->get_type();

        // Look for the semicolon between statements.
        if (token_type == (TokenType) PascalTokenType::SEMICOLON)
        {
            token = next_token(token);  // consume the ;
        }

        // If at the start of the next assignment statement,
        // then missing a semicolon.
        else if (token_type == (TokenType) PascalTokenType::IDENTIFIER)
        {
            error_handler.flag(token, PascalErrorCode::MISSING_SEMICOLON,
                               this);
        }

        // Unexpected token.
        else if (token_type != (TokenType) terminator)
        {
            error_handler.flag(token, PascalErrorCode::UNEXPECTED_TOKEN,
                               this);
            token = next_token(token);  // consume the unexpected token
        }
    }

    // Look for the terminator token.
    if (token->get_type() == (TokenType) terminator)
    {
        token = next_token(token);  // consume the terminator token
    }
    else {
        error_handler.flag(token, error_code, this);
    }
}

}}}}  // namespace wci::frontend::pascal::parsers
