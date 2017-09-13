/**
 * <h1>JavaToken</h1>
 *
 * <p>Base class for Java token classes.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#include <string>
#include <vector>
#include <map>
#include "JavaToken.h"

namespace wci { namespace frontend { namespace java {

using namespace std;

map<string, JavaTokenType> JavaToken::RESERVED_WORDS;
map<string, JavaTokenType> JavaToken::SPECIAL_SYMBOLS;
map<JavaTokenType, string> JavaToken::SPECIAL_SYMBOL_NAMES;

bool JavaToken::INITIALIZED = false;

void JavaToken::initialize()
{
    if (INITIALIZED) return;

    vector<string> rw_strings =
    {
        "abstract", "double", "int", "long",
        "break", "else", "long", "switch",
        "case", "enum", "native", "super",
        "char", "extends", "return", "this",
        "class", "float", "short", "throw",
        "const", "for", "package", "void",
        "continue", "goto", "protected", "volatile",
        "do", "if", "static", "while"
    };

    vector<JavaTokenType> rw_keys =
    {
        JavaTokenType::abstract,
        JavaTokenType::double,
        JavaTokenType::int,
        JavaTokenType::long,
        
        JavaTokenType::break,
        JavaTokenType::else,
        JavaTokenType::long,
        JavaTokenType::switch,

        JavaTokenType::case,
        JavaTokenType::enum,
        JavaTokenType::native,
        JavaTokenType::super,
            
        JavaTokenType::char,
        JavaTokenType::extends,
        JavaTokenType::return,
        JavaTokenType::this,

        JavaTokenType::class,
        JavaTokenType::float,
        JavaTokenType::short,
        JavaTokenType::throw,
            
        JavaTokenType::const,
        JavaTokenType::for,
        JavaTokenType::package,
        JavaTokenType::void,

        JavaTokenType::continue,
        JavaTokenType::goto,
        JavaTokenType::protected,
        JavaTokenType::volatile,
            
        JavaTokenType::do,
        JavaTokenType::if,
        JavaTokenType::static,
        JavaTokenType::while,
    };

    for (int i = 0; i < rw_strings.size(); i++)
    {
        RESERVED_WORDS[rw_strings[i]] = rw_keys[i];
    }

    vector<string> ss_strings =
    {
        "~", "!", "@", "%", "^", "&", "*", "-", "+", "=",
        "|", "/", ":", ";", "?", "<", ">", ".", ",",
        "'", "\"", "(",  ")", "[", "]", "{", "}",
        "++", "--", "<<", ">>", "<=", ">=", "+=", "-=", "*=", "/=",
        "==", "|=", "%=", "&=", "^=", "!=", "<<=", ">>=", "||", "&&",
        "//", "/*", "*/"
    };

    vector<JavaTokenType> ss_keys =
    {
        JavaTokenType::UNARY,
        JavaTokenType::MINUS,
        JavaTokenType::STAR,
        JavaTokenType::SLASH,
        JavaTokenType::COLON_EQUALS,
        JavaTokenType::DOT,
        JavaTokenType::COMMA,
        JavaTokenType::SEMICOLON,
        JavaTokenType::COLON,
        JavaTokenType::QUOTE,
        JavaTokenType::EQUALS,
        JavaTokenType::NOT_EQUALS,

        JavaTokenType::LESS_THAN,
        JavaTokenType::LESS_EQUALS,
        JavaTokenType::GREATER_EQUALS,
        JavaTokenType::GREATER_THAN,
        JavaTokenType::LEFT_PAREN,
        JavaTokenType::RIGHT_PAREN,
        JavaTokenType::LEFT_BRACKET,
        JavaTokenType::RIGHT_BRACKET,
        JavaTokenType::LEFT_BRACE,
        JavaTokenType::RIGHT_BRACE,
        JavaTokenType::UP_ARROW,
        JavaTokenType::DOT_DOT
    };

    for (int i = 0; i < ss_strings.size(); i++)
    {
        SPECIAL_SYMBOLS[ss_strings[i]] = ss_keys[i];
    }

    vector<string> ss_names =
    {
        "UNARY", "NOT", "@", "REMIND", "BIT_EXCLUSIVE", "BIT_AND", "MULTI", "MINUS", "ADD", "ASSIGN",
        "BIT_OR", "DIVID", "COLON", "SEMICOLON", "?", "LESS_THAN","GREATER_THAN", "DOT", "COMMA",
        "QUOTE", "BACK_SLASH", "LEFT_PAREN", "RIGHT_PAREN", "LEFT_BRACKET", "RIGHT_BRACKET", "LEFT_BRACE", "RIGHT_BRACE",
        "INCREMENT", "DECREMENT", "LEFT_SHIFT", "RIGHT_SHIFT", "LESS_EQUALS", "GREATER_EQUALS", "PLUS_EQUAL", "MINUS_EQUAL","MULTI_EQUAL", "DIVIDE_EQUAL",
        "EQUAL", "|=", "%=", "&=", "^=", "NOT_EQUAL", "<<=", "OR", "AND", "ONE_LINE_COMMENT", "BEGIN_COMMENT", "END_COMMENT"

    };

    for (int i = 0; i < ss_names.size(); i++)
    {
        SPECIAL_SYMBOL_NAMES[ss_keys[i]] = ss_names[i];
    }

    INITIALIZED = true;
}

JavaToken::JavaToken(Source *source) throw (string)
    : Token(source)
{
    initialize();
}

}}}  // namespace wci::frontend::Java
