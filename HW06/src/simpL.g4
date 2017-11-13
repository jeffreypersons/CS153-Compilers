/*
Notes:
- All curly braces must be on their own line, and all statements must terminate with a newline
- Earlier the definition, greater the precedence
- Identifier is defined to avoid name clashes with other keywords
- It's important to distinguish between the lexer and parser stage. That means things like
  valid types is NOT to be distinguished here, but rather determined in the parsing stage,
  instead. This greatly promotes separation of concerns, and simplifies the lexer grammar.
 */
grammar simpL;

program : statement*;

statement
    : func_def
    | if_stmt
    | declaration
    | assignment
    | expression EOL
    ;
declaration
    : type NAME (ASSIGN expression)? EOL
    ;
assignment
    : NAME ASSIGN expression EOL
    ;
if_stmt
    : ('if' LPAREN expression RPAREN EOL
       LCURL EOL
           statement*
       RCURL EOL)
      (
       'else if' LPAREN expression RPAREN EOL
       LCURL EOL
           statement*
       RCURL EOL
      )*
      (
       'else' EOL
       LCURL EOL
           statement*
       RCURL EOL
      )?
    ;
func_def
    : 'def' NAME LPAREN (type NAME (SEPARATOR type NAME)*)? RPAREN EOL
      LCURL EOL
          statement*
          ('return' expression EOL)?
      RCURL EOL
    ;

// expressions: note that as of version 4.1, ANTLR rewrites the below as unambiguous rules 
expression
    : NAME
    | value
    | func_call
    | LPAREN expression RPAREN
    | expression POW expression
    | expression (MUL | DIV) expression
    | expression (ADD | SUB) expression
    | expression (LT  | GT | LTE | GTE) expression
    | expression (IS_EQ | NOT_EQ) expression
    | NOT expression
    | expression AND expression
    | expression OR expression
    ;
func_call : NAME LPAREN (expression)* RPAREN;

// token groups by category
type  : TEXT       | NUMBER       | BOOLEAN;
value : TEXT_VALUE | NUMBER_VALUE | BOOLEAN_VALUE;
bool_operator  : AND   | OR     | NOT;
arith_operator : ADD   | SUB    | MUL | DIV | POW;
comp_operator  : IS_EQ | NOT_EQ | GT  | LT  | LTE | GTE;

// data types and values
NONE          : 'None';
TEXT          : 'Text';
NUMBER        : 'Number';
BOOLEAN       : 'Boolean';
TEXT_VALUE    : QUOTE .*? QUOTE;
NUMBER_VALUE  : DIGIT+ | DIGIT+.DIGIT+;
BOOLEAN_VALUE : 'true' | 'false';

// reserved symbols
ASSIGN    : '=';
SEPARATOR : ',';
LPAREN    : '(';
RPAREN    : ')';
LCURL     : '{';
RCURL     : '}';
LSQUARE   : '[';
RSQUARE   : ']';

// operators
GT     : '>';
LT     : '<';
LTE    : '<=';
GTE    : '>=';
NOT    : 'not';
IS_EQ  : '!=';
NOT_EQ : '==';
AND    : 'and';
OR     : 'or';
ADD    : '+';
SUB    : '-';
MUL    : '*';
DIV    : '/';
POW    : '^';

// fundamental tokens
EOL          : NEWLINE+;
SKIP         : (LINE_COMMENT | WHITESPACE+) -> skip;
NAME         : ('_' | LETTER) ('_' | LETTER | DIGIT)*;
LINE_COMMENT : '#' .*? NEWLINE;

// fragments (helper definitions)
fragment QUOTE      : '\'';
fragment DIGIT      : '0'..'9';
fragment LETTER     : 'a'..'z' | 'A'..'Z';
fragment NEWLINE    : '\n' | '\r\n';
fragment WHITESPACE : [ \t];
