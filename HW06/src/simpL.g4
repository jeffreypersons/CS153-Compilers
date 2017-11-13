/*
Notes:
- Earlier the definition, greater the precedence
- Identifier is defined to avoid name clashes with other keywords
- It's important to distinguish between the lexer and parser stage. That means things like
  valid types is NOT to be distinguished here, but rather determined in the parsing stage,
  instead. This greatly promotes separation of concerns, and simplifies the lexer grammar.
 */
grammar simpL;

/*
 * types of statements:
 * - simple statements: assignment, return
 * - compound statements:
 *   - func def, loop, etc (anything with a block enclosed with {})
 * 
 * types of expressions:
 * - function calls/simple expressions
 */
// starting rule
program : block;
block   : statement* return_stmt?;

// statements
statement
    : func_def
    | if_stmt
    | declaration
    | assignment
    ;
declaration
    : type NAME (ASSIGN expression)? EOS
    ;
assignment
    : NAME ASSIGN expression EOS
    ;
if_stmt
    : 'if' LPAREN expression RPAREN EOS LCURL block RCURL EOS
      ('else if' LPAREN expression RPAREN LCURL block RCURL EOS)*
      ('else' EOS LCURL block RCURL EOS)?
    ;
return_stmt : 'return' expression EOS;
func_def
    : 'def' NAME LPAREN (type NAME (SEPARATOR type NAME)*)? RPAREN EOS
      LCURL block RCURL EOS
    ;

// expressions
expression
    : value
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
EOS          : EOL;
SKIP         : (WHITESPACE | LINE_COMMENT) -> skip;
NAME         : ('_' | LETTER) ('_' | LETTER | DIGIT)*;
LINE_COMMENT : '#' ~[EOL]*;

// fragments (helper definitions)
fragment QUOTE      : '\'';
fragment DIGIT      : '0'..'9';
fragment LETTER     : 'a'..'z' | 'A'..'Z';
fragment EOL        : '\n' | '\r\n' | EOF;
fragment WHITESPACE : [ \t]+;
