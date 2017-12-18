/**
 * <h1>ConstantDefinitionsParser</h1>
 *
 * <p>Parse Pascal constant definitions.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#include <string>
#include <set>
#include <algorithm>
#include "ConstantDefinitionsParser.h"
#include "DeclarationsParser.h"
#include "../../../frontend/Token.h"
#include "../../../frontend/pascal/PascalToken.h"
#include "../../../frontend/pascal/PascalError.h"
#include "../../../intermediate/SymTabEntry.h"
#include "../../../intermediate/TypeFactory.h"
#include "../../../intermediate/symtabimpl/SymTabEntryImpl.h"
#include "../../../intermediate/symtabimpl/Predefined.h"
#include "../../../DataValue.h"

namespace wci { namespace frontend { namespace pascal { namespace parsers {

using namespace std;
using namespace wci;
using namespace wci::frontend;
using namespace wci::frontend::pascal;
using namespace wci::intermediate;
using namespace wci::intermediate::symtabimpl;

bool ConstantDefinitionsParser::INITIALIZED = false;

set<PascalTokenType> ConstantDefinitionsParser::CONSTANT_START_SET =
{
    PT_IDENTIFIER, PT_INTEGER, PT_REAL,
    PT_PLUS, PT_MINUS, PT_STRING, PT_SEMICOLON,
};

set<PascalTokenType> ConstantDefinitionsParser::IDENTIFIER_SET;
set<PascalTokenType> ConstantDefinitionsParser::EQUALS_SET;
set<PascalTokenType> ConstantDefinitionsParser::NEXT_START_SET;

void ConstantDefinitionsParser::initialize()
{
    if (INITIALIZED) return;
    DeclarationsParser::initialize();

    IDENTIFIER_SET = DeclarationsParser::TYPE_START_SET;
    IDENTIFIER_SET.insert(PT_IDENTIFIER);

    EQUALS_SET = CONSTANT_START_SET;
    EQUALS_SET.insert(PT_EQUALS);
    EQUALS_SET.insert(PT_SEMICOLON);

    NEXT_START_SET = DeclarationsParser::TYPE_START_SET;
    NEXT_START_SET.insert(PT_SEMICOLON);
    NEXT_START_SET.insert(PT_IDENTIFIER);

    INITIALIZED = true;
}

ConstantDefinitionsParser::ConstantDefinitionsParser(PascalParserTD *parent)
    : DeclarationsParser(parent)
{
    initialize();
}

void ConstantDefinitionsParser::parse_declaration(Token *token) throw (string)
{
    token = synchronize(IDENTIFIER_SET);

    // Loop to parse a sequence of constant definitions
    // separated by semicolons.
    while (token->get_type() == (TokenType) PT_IDENTIFIER)
    {
        string name = token->get_text();
        transform(name.begin(), name.end(), name.begin(), ::tolower);
        SymTabEntry *constant_id = symtab_stack->lookup_local(name);

        // Enter the new identifier into the symbol table
        // but don't set how it's defined yet.
        if (constant_id == nullptr)
        {
            constant_id = symtab_stack->enter_local(name);
            constant_id->append_line_number(token->get_line_number());
        }
        else
        {
            error_handler.flag(token, IDENTIFIER_REDEFINED, this);
            constant_id = nullptr;
        }

        token = next_token(token);  // consume the identifier token

        // Synchronize on the = token.
        token = synchronize(EQUALS_SET);
        if (token->get_type() == (TokenType) PT_EQUALS)
        {
            token = next_token(token);  // consume the =
        }
        else
        {
            error_handler.flag(token, MISSING_EQUALS, this);
        }

        // Parse the constant value.
        Token *constant_token = token;
        DataValue *data_value = parse_constant(token);
        if (data_value == nullptr) data_value = new DataValue(-1);

        // Set identifier to be a constant and set its value.
        if (constant_id != nullptr)
        {
            constant_id->set_definition((Definition) DF_CONSTANT);
            constant_id->set_attribute((SymTabKey) CONSTANT_VALUE,
                                       new EntryValue(data_value));

            // Set the constant's type.
            TypeSpec *constant_type =
                constant_token->get_type()
                           == (TokenType) PT_IDENTIFIER
                    ? get_constant_type(constant_token)
                    : get_constant_type(data_value);
            constant_id->set_typespec(constant_type);
        }

        token = current_token();
        TokenType token_type = token->get_type();

        // Look for one or more semicolons after a definition.
        if (token_type == (TokenType) PT_SEMICOLON)
        {
            while (token->get_type() == (TokenType) PT_SEMICOLON)
            {
                token = next_token(token);  // consume the ;
            }
        }

        // If at the start of the next definition or declaration,
        // then missing a semicolon.
        else if (NEXT_START_SET.find((PascalTokenType) token_type)
                != NEXT_START_SET.end())
        {
            error_handler.flag(token, MISSING_SEMICOLON, this);
        }

        token = synchronize(IDENTIFIER_SET);
    }
}

DataValue *ConstantDefinitionsParser::parse_constant(Token *token)
    throw (string)
{
    // Synchronize at the start of a constant.
    token = synchronize(CONSTANT_START_SET);
    TokenType token_type = token->get_type();

    bool plus_sign    = token_type == (TokenType) PT_PLUS;
    bool minus_sign   = token_type == (TokenType) PT_MINUS;
    bool leading_sign = plus_sign || minus_sign;

    // Plus or minus sign?
    if (leading_sign) token = next_token(token);  // consume sign

    // Parse the constant.
    switch ((PascalTokenType) token->get_type())
    {
        case PT_IDENTIFIER:
        {
            return parse_identifier_constant(token, minus_sign);
        }

        case PT_INTEGER:
        {
            DataValue *data_value = token->get_value();
            next_token(token);  // consume the number

            return minus_sign ? new DataValue(-data_value->i)
                              : data_value;
        }

        case PT_REAL:
        {
            DataValue *data_value = token->get_value();
            next_token(token);  // consume the number

            return minus_sign ? new DataValue(-data_value->f)
                              : data_value;
        }

        case PT_STRING:
        {
            if (leading_sign)
            {
                error_handler.flag(token, INVALID_CONSTANT, this);
            }

            DataValue *data_value = token->get_value();
            next_token(token);  // consume the string
            return data_value;
        }

        default:
        {
            error_handler.flag(token, INVALID_CONSTANT, this);
            return nullptr;
        }
    }
}

DataValue *ConstantDefinitionsParser::parse_identifier_constant(
                                     Token *token, const bool minus_sign)
    throw (string)
{
    string name = token->get_text();
    transform(name.begin(), name.end(), name.begin(), ::tolower);
    SymTabEntry *constant_id = symtab_stack->lookup(name);

    // The identifier must have already been defined
    // as an constant identifier.
    if (constant_id == nullptr)
    {
        error_handler.flag(token, IDENTIFIER_UNDEFINED, this);
        next_token(token);  // consume the identifier
        return nullptr;
    }

    Definition defn = constant_id->get_definition();
    DataValue *data_value = nullptr;

    if (defn == (Definition) DF_CONSTANT)
    {
        EntryValue *entry_value = constant_id->get_attribute(
                                             (SymTabKey) CONSTANT_VALUE);
        data_value = entry_value->value;
        constant_id->append_line_number(token->get_line_number());

        if (data_value->type == INTEGER)
        {
            if (minus_sign) data_value = new DataValue(-data_value->i);
        }
        else if (data_value->type == FLOAT)
        {
            if (minus_sign) data_value = new DataValue(-data_value->f);
        }
        else if (data_value->type == STRING)
        {
            if (minus_sign)
            {
                error_handler.flag(token, INVALID_CONSTANT, this);
            }

            data_value = new DataValue(data_value->s[0]);
        }
    }
    else if (defn == (Definition) DF_ENUMERATION_CONSTANT)
    {
        EntryValue *entry_value = constant_id->get_attribute(
                                             (SymTabKey) CONSTANT_VALUE);
        data_value = entry_value->value;
        constant_id->append_line_number(token->get_line_number());

        if (minus_sign)
        {
            error_handler.flag(token, INVALID_CONSTANT, this);
        }
    }
    else if (defn == (Definition) -1)
    {
        error_handler.flag(token, NOT_CONSTANT_IDENTIFIER, this);
    }
    else
    {
        error_handler.flag(token, INVALID_CONSTANT, this);
    }

    next_token(token);  // consume the identifier
    return data_value;
}

TypeSpec *ConstantDefinitionsParser::get_constant_type(
                                                   DataValue *data_value)
{
    TypeSpec *constant_type = nullptr;

    if (data_value->type == INTEGER)
    {
        constant_type = Predefined::integer_type;
    }
    else if (data_value->type == FLOAT)
    {
        constant_type = Predefined::real_type;
    }
    else if (data_value->type == CHAR)
    {
        constant_type = Predefined::char_type;
    }
    else if (data_value->type == STRING)
    {
        if (data_value->s.length() == 1)
        {
            constant_type = Predefined::char_type;
        }
        else {
            constant_type = TypeFactory::create_string_type(data_value->s);
        }
    }

    return constant_type;
}

/**
 * Return the type of a constant given its identifier.
 * @param identifier the constant's identifier.
 * @return the type specification.
 */
TypeSpec *ConstantDefinitionsParser::get_constant_type(Token *id_token)
{
    string name = id_token->get_text();
    transform(name.begin(), name.end(),
              name.begin(), ::tolower);
    SymTabEntry *constant_id = symtab_stack->lookup_local(name);

    if (constant_id == nullptr) return nullptr;

    Definition definition = constant_id->get_definition();

    if (   (definition == (Definition) DF_CONSTANT)
        || (definition == (Definition) DF_ENUMERATION_CONSTANT))
    {
        return constant_id->get_typespec();
    }
    else {
        return nullptr;
    }
}

}}}}  // namespace wci::frontend::pascal::parsers
