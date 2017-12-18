/**
 * <h1>SubrangeTypeParser</h1>
 *
 * <p>Parse a Pascal subrange type specification.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#include <set>
#include "SimpleTypeParser.h"
#include "EnumerationTypeParser.h"
#include "SubrangeTypeParser.h"
#include "TypeSpecificationParser.h"
#include "ConstantDefinitionsParser.h"
#include "../PascalParserTD.h"
#include "../../../frontend/Token.h"
#include "../../../frontend/pascal/PascalToken.h"
#include "../../../frontend/pascal/PascalError.h"
#include "../../../intermediate/SymTabEntry.h"
#include "../../../intermediate/SymTabStack.h"
#include "../../../intermediate/symtabimpl/Predefined.h"
#include "../../../intermediate/symtabimpl/SymTabEntryImpl.h"
#include "../../../intermediate/TypeFactory.h"
#include "../../../intermediate/typeimpl/TypeSpecImpl.h"
#include "../../../DataValue.h"

namespace wci { namespace frontend { namespace pascal { namespace parsers {

using namespace std;
using namespace wci;
using namespace wci::frontend;
using namespace wci::frontend::pascal;
using namespace wci::frontend::pascal::parsers;
using namespace wci::intermediate;
using namespace wci::intermediate::symtabimpl;
using namespace wci::intermediate::typeimpl;

SubrangeTypeParser::SubrangeTypeParser(PascalParserTD *parent)
    : TypeSpecificationParser(parent)
{
}

TypeSpec *SubrangeTypeParser::parse_declaration(Token *token)
    throw (string)
{
    TypeSpec *subrange_typespec =
            TypeFactory::create_type((TypeForm) TypeFormImpl::SUBRANGE);

    DataValue *min_value = nullptr;
    DataValue *max_value = nullptr;
    TypeSpec *min_typespec = nullptr;
    TypeSpec *max_typespec = nullptr;

    ConstantDefinitionsParser constant_parser(this);
    Token *constant_token = new Token(*token);

    // Parse the minimum constant and get its data type.
    if (token->get_type() == (TokenType) PT_IDENTIFIER)
    {
        min_typespec = constant_parser.get_constant_type(token);
        min_value = constant_parser.parse_constant(token);
    }
    else
    {
        min_value = constant_parser.parse_constant(token);
        min_typespec = constant_parser.get_constant_type(min_value);
    }

    min_value = check_value_type(constant_token, min_value, min_typespec);

    token = current_token();
    bool saw_dotdot = false;

    // Look for the .. token.
    if (token->get_type() == (TokenType) PT_DOT_DOT)
    {
        token = next_token(token);  // consume the .. token
        saw_dotdot = true;
    }

    // At the start of the maximum constant?
    if (ConstantDefinitionsParser::CONSTANT_START_SET.find(
            (PascalTokenType) token->get_type())
                 != ConstantDefinitionsParser::CONSTANT_START_SET.end())
    {
        if (!saw_dotdot)
        {
            error_handler.flag(token, MISSING_DOT_DOT, this);
        }

        delete constant_token;
        constant_token = new Token(*token);

        // Parse the maximum constant and get its data type.
        if (token->get_type() == (TokenType) PT_IDENTIFIER)
        {
            max_typespec = constant_parser.get_constant_type(token);
            max_value = constant_parser.parse_constant(token);
        }
        else
        {
            max_value = constant_parser.parse_constant(token);
            max_typespec = constant_parser.get_constant_type(max_value);
        }

        max_value = check_value_type(constant_token,
                                     max_value, max_typespec);

        // Are the min and max value types valid?
        if ((min_typespec == nullptr) || (max_typespec == nullptr))
        {
            error_handler.flag(constant_token, INCOMPATIBLE_TYPES, this);
        }

        // Are the min and max value types the same?
        else if (min_typespec != max_typespec)
        {
            error_handler.flag(constant_token, INCOMPATIBLE_TYPES, this);
        }

        // Min value > max value?
        else if (   (min_value != nullptr) && (max_value != nullptr)
                 && (min_value->i >= max_value->i))
        {
            error_handler.flag(constant_token, MIN_GT_MAX, this);
        }
    }
    else
    {
        error_handler.flag(constant_token, INVALID_SUBRANGE_TYPE, this);
    }

    subrange_typespec->set_attribute((TypeKey) SUBRANGE_BASE_TYPE,
                                     new TypeValue(min_typespec));
    subrange_typespec->set_attribute((TypeKey) SUBRANGE_MIN_VALUE,
                                     new TypeValue(min_value));
    subrange_typespec->set_attribute((TypeKey) SUBRANGE_MAX_VALUE,
                                     new TypeValue(max_value));

    delete constant_token;
    return subrange_typespec;
}

DataValue *SubrangeTypeParser::check_value_type(
                 Token *token, DataValue *data_value, TypeSpec *typespec)
{
    if (typespec == nullptr)
    {
        return data_value;
    }
    if (typespec == Predefined::integer_type)
    {
        return data_value;
    }
    else if (typespec == Predefined::char_type)
    {
        char ch = data_value->s[0];
        return new DataValue((int) ch);
    }
    else if (typespec->get_form() == (TypeForm) TypeFormImpl::ENUMERATION)
    {
        return data_value;
    }
    else
    {
        error_handler.flag(token, INVALID_SUBRANGE_TYPE, this);
        return data_value;
    }
}

}}}}  // namespace wci::frontend::pascal::parsers
