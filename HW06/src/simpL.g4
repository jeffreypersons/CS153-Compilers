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

// fundamental rules
program : block;
block   : statement* return_stmt?;

// statements
statement
    : func_def
    | if_stmt
    | declaration
    | assignment
    | expression_statement
    ;
expression_statement
    : expression EOL
    ;
declaration
    : type NAME (ASSIGN expression)? EOL
    ;
assignment
    : NAME ASSIGN expression EOL
    ;
if_stmt
    : 'if' LPAREN expression RPAREN EOL
       LCURL EOL
           block
       RCURL EOL
      ('else if' LPAREN expression RPAREN EOL
       LCURL EOL
           block
       RCURL EOL
      )*
      ('else' EOL
        LCURL EOL
            block
        RCURL EOL
      )?
    ;
return_stmt : 'return' expression EOL;
func_def
    : 'def' NAME LPAREN (type NAME (SEPARATOR type NAME)*)? RPAREN EOL
      LCURL EOL
          block
      RCURL EOL
    ;

// expressions
expression
    : NAME
    | value
    | func_call
    | bool_expr
    | arith_expr
    ;
func_call  : NAME LPAREN (expression)* RPAREN;
arith_expr : arith_expr (ADD | SUB) term | term;
term       : term (MUL | DIV) power | power;
power      : factor POW power | factor;
factor     : LPAREN arith_expr RPAREN | NAME | NUMBER_VALUE;
bool_expr  : BOOLEAN_VALUE bool_operator BOOLEAN_VALUE | NAME bool_operator NAME;

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
TEXT_VALUE    : QUOTE ~[QUOTE]* QUOTE;
NUMBER_VALUE  : (DIGIT+ | DIGIT+.DIGIT+);
BOOLEAN_VALUE : 'true'  | 'false';

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
EOL          : EOF | NEWLINE+;
SKIP         : (WHITESPACE | LINE_COMMENT)+ -> skip;
NAME         : ('_' | LETTER) ('_' | LETTER | DIGIT)*;
LINE_COMMENT : '#' ~[EOL]*;

// fragments (helper definitions)
fragment QUOTE      : '\'';
fragment DIGIT      : '0'..'9';
fragment LETTER     : 'a'..'z' | 'A'..'Z';
fragment NEWLINE    : '\n' | '\r\n';
fragment WHITESPACE : [ \t];
