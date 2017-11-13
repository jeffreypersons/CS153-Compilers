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

program : stmt*;
stmt
    : func_def
    | if_stmt
    | declaration
    | assignment
    | expr EOL
    ;
declaration
    : TYPE NAME (ASSIGN expr)? EOL
    ;
assignment
    : NAME ASSIGN expr EOL
    ;
if_stmt
    : ('if' LPAREN expr RPAREN EOL
       LCURL EOL
           stmt*
       RCURL EOL)
      (
       'else if' LPAREN expr RPAREN EOL
       LCURL EOL
           stmt*
       RCURL EOL
      )*
      (
       'else' EOL
       LCURL EOL
           stmt*
       RCURL EOL
      )?
    ;
func_def
    : 'def' NAME LPAREN (TYPE NAME (SEPARATOR TYPE NAME)*)? RPAREN EOL
      LCURL EOL
          stmt*
          ('return' expr EOL)?
      RCURL EOL
    ;

// expressions: note that as of version 4.1, ANTLR rewrites the below as unambiguous rules
// TODO: to make the evaluation rules more clear, incorporate ANTLR syntax for left/right associativity
expr
    : NAME
    | LITERAL
    | func_call
    | LPAREN expr RPAREN
    | expr POW expr
    | expr (MUL | DIV) expr
    | expr (ADD | SUB) expr
    | expr (LT  | GT | LTE | GTE) expr
    | expr (EQ  | NEQ) expr
    | NOT expr
    | expr AND expr
    | expr OR expr
    ;
func_call : NAME LPAREN (expr (SEPARATOR expr)*)? RPAREN;
TYPE      : TEXT       | NUMBER       | BOOLEAN;
LITERAL   : TEXT_VALUE | NUMBER_VALUE | BOOLEAN_VALUE;

// data types and values
NONE          : 'None';
TEXT          : 'Text';
NUMBER        : 'Number';
BOOLEAN       : 'Boolean';
TEXT_VALUE    : QUOTE .*? QUOTE;
NUMBER_VALUE  : DIGIT+ | DIGIT+.DIGIT+;
BOOLEAN_VALUE : 'true' | 'false';

// reserved symbols
SEPARATOR : ',';
LPAREN    : '(';
RPAREN    : ')';
LCURL     : '{';
RCURL     : '}';
LSQUARE   : '[';
RSQUARE   : ']';

// operators
POW    : '^';
MUL    : '*';
DIV    : '/';
ADD    : '+';
SUB    : '-';
LT     : '<';
GT     : '>';
LTE    : '<=';
GTE    : '>=';
EQ     : '==';
NEQ    : '!=';
NOT    : 'not';
AND    : 'and';
OR     : 'or';
ASSIGN : '=';

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
