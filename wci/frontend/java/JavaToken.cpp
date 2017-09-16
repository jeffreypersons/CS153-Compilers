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
        JavaTokenType::ABSTRACT,
        JavaTokenType::DOUBLE,
        JavaTokenType::INT,
        JavaTokenType::LONG,
        
        JavaTokenType::BREAK,
        JavaTokenType::ELSE,
        JavaTokenType::LONG,
        JavaTokenType::SWITCH,

        JavaTokenType::CASE,
        JavaTokenType::ENUM,
        JavaTokenType::NATIVE,
        JavaTokenType::SUPER,
            
        JavaTokenType::CHAR,
        JavaTokenType::EXTENDS,
        JavaTokenType::RETURN,
        JavaTokenType::THIS,

        JavaTokenType::CLASS,
        JavaTokenType::FLOAT,
        JavaTokenType::SHORT,
        JavaTokenType::THROW,
            
        JavaTokenType::CONST,
        JavaTokenType::FOR,
        JavaTokenType::PACKAGE,
        JavaTokenType::VOID,

        JavaTokenType::CONTINUE,
        JavaTokenType::GOTO,
        JavaTokenType::PROTECTED,
        JavaTokenType::VOLATILE,
            
        JavaTokenType::DO,
        JavaTokenType::IF,
        JavaTokenType::STATIC,
        JavaTokenType::WHILE,
    };

    for (int i = 0; i < rw_strings.size(); i++)
    {
        RESERVED_WORDS[rw_strings[i]] = rw_keys[i];
    }

    vector<string> ss_strings =
    {
        "~", "!", "@", "%", "^", "&", "*", "-", "+", "=",
        "|", "/", ":", ";", "?", "<", ">", ".", ",",
        "\'", "\"", "(",  ")", "[", "]", "{", "}",
        "++", "--", "<<", ">>", "<=", ">=", "+=", "-=", "*=", "/=",
        "==", "|=", "%=", "&=", "^=", "!=", "<<=", ">>=", "||", "&&",
        "//", "/*", "*/"
    };

    vector<JavaTokenType> ss_keys =
    {
        JavaTokenType::UNARY,
        JavaTokenType::NOT,
        JavaTokenType::OVERRIDE,
        JavaTokenType::REMIND,
        JavaTokenType::XOR,
        JavaTokenType::BIT_AND,
        JavaTokenType::MULTI,
        JavaTokenType::MINUS,
        JavaTokenType::PLUS,
        JavaTokenType::ASSIGN,
        
        JavaTokenType::BIT_OR,
        JavaTokenType::DIVIDE,
        JavaTokenType::COLON,
        JavaTokenType::SEMICOLON,
        JavaTokenType::TERNARY,
        JavaTokenType::LESS_THAN,
        JavaTokenType::GREATER_THAN,
        JavaTokenType::DOT,
        JavaTokenType::COMMA,
        
        JavaTokenType::QUOTE,
        JavaTokenType::BACK_SLASH,

        JavaTokenType::APOSTROPHE,
        JavaTokenType::LEFT_PAREN,
        JavaTokenType::RIGHT_PAREN,
        JavaTokenType::LEFT_BRACKET,
        JavaTokenType::RIGHT_BRACKET,
        JavaTokenType::LEFT_BRACE,
        JavaTokenType::RIGHT_BRACE,
        
        JavaTokenType::INCREMENT,
        JavaTokenType::DECREMENT,
        JavaTokenType::LEFT_SHIFT,
        JavaTokenType::RIGHT_SHIFT,
        JavaTokenType::LESS_EQUALS,
        JavaTokenType::GREATER_EQUALS,
        JavaTokenType::PLUS_ASSIGN,
        JavaTokenType::MINUS_ASSIGN,
        JavaTokenType::MULTI_ASSIGN,
        JavaTokenType::DIVIDE_ASSIGN,
        
        JavaTokenType::EQUALS,
        JavaTokenType::OR_ASSIGN,
        JavaTokenType::MODULE_ASSIGN,
        JavaTokenType::AND_ASSIGN,
        JavaTokenType::XOR_ASSIGN,
        JavaTokenType::NOT_EQUALS,
        JavaTokenType::LEFTSHIFT_AND_ASSIGN,
        JavaTokenType::RIGHTSHIFT_AND_ASSIGN,
        JavaTokenType::OR,
        JavaTokenType::AND
    };

    for (int i = 0; i < ss_strings.size(); i++)
    {
        SPECIAL_SYMBOLS[ss_strings[i]] = ss_keys[i];
    }

    vector<string> ss_names =
    {
        "UNARY", "NOT", "OVERRIDE", "REMIND", "XOR", "BIT_AND", "MULTI", "MINUS", "ADD", "ASSIGN",
        "BIT_OR", "DIVIDE", "COLON", "SEMICOLON", "TERNARY", "LESS_THAN","GREATER_THAN", "DOT", "COMMA",
        "QUOTE", "BACK_SLASH", "LEFT_PAREN", "RIGHT_PAREN", "LEFT_BRACKET", "RIGHT_BRACKET", "LEFT_BRACE", "RIGHT_BRACE",
        "INCREMENT", "DECREMENT", "LEFT_SHIFT", "RIGHT_SHIFT", "LESS_EQUALS", "GREATER_EQUALS", "PLUS_ASSIGN", "MINUS_ASSIGN","MULTI_ASSIGN", "DIVIDE_ASSIGN",
        "EQUAL", "OR_ASSIGN", "MODULE_ASSIGN", "AND_ASSIGN", "XOR_ASSIGN", "NOT_EQUAL", "LEFTSHIFT_AND_ASSIGN", "RIGHTSHIFT_AND_ASSIGN" "OR", "AND"
        "APOSTROPHE", "QUOTE", "LEFT_PAREN", "RIGHT_PAREN", "LEFT_BRACKET", "RIGHT_BRACKET", "LEFT_BRACE", "RIGHT_BRACE",
        "INCREMENT", "DECREMENT", "LEFT_SHIFT", "RIGHT_SHIFT", "LESS_EQUALS", "GREATER_EQUALS", "PLUS_ASSIGN", "MINUS_ASSIGN","MULTI_ASSIGN", "DIVIDE_ASSIGN",
        "EQUAL", "OR_ASSIGN", "MODULE_ASSIGN", "AND_ASSIGN", "XOR_ASSIGN", "NOT_EQUAL", "LEFTSHIFT_AND_ASSIGN", "RIGHTSHIFT_AND_ASSIGN" "OR", "AND"
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
