/*
 * Notes:
 * - Earlier the definition, greater the precedence
 * - Identifier is defined to avoid name clashes with other keywords
 * 
 * TODO: add string concatenation
 * TODO: change how we deal with expressions and statements, breakup by type
 * TODO: add return value to function definition
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
block   : statement* return_statement?;
return_statement : 'return' expression EOS;

// declarations and assignments
declaration : type (NAME EOS | assignment EOS);
assignment  : NAME ASSIGN expression;

// statements and expressions
statement     : simple_stmt | compound_stmt;
simple_stmt   : bool_expr   | arith_expr;
compound_stmt : func_def    | if_stmt;
if_stmt       : 'if' LPAREN simple_stmt RPAREN LCURL block RCURL
                    ('else if' LPAREN simple_stmt RPAREN LCURL block RCURL)* ('else' LCURL block RCURL)?;
func_def      : 'def' NAME LPAREN param_list RPAREN LCURL block RCURL;
param_list    : (type NAME (SEPARATOR type NAME)*)?;

expression    : arith_expr | bool_expr;
arith_expr    : arith_expr (ADD | SUB) term | term;
term          : term (MUL | DIV) power | power;
power         : factor POW power | factor;
factor        : LPAREN arith_expr RPAREN | NAME | NUMBER_VALUE;
bool_expr     : BOOLEAN_VALUE bool_operator BOOLEAN_VALUE | NAME bool_operator NAME;

// token groups by category
type  : TEXT       | NUMBER       | BOOLEAN;
value : TEXT_VALUE | NUMBER_VALUE | BOOLEAN_VALUE;
bool_operator  : AND   | OR     | NOT;
arith_operator : ADD   | SUB    | MUL | DIV | POW;
comp_operator  : IS_EQ | NOT_EQ | GT  | LT  | LTE | GTE;

// data types
TEXT    : 'Text';
NUMBER  : 'Number';
BOOLEAN : 'Boolean';
TEXT_VALUE    : QUOTE ~[QUOTE]* QUOTE;
NUMBER_VALUE  : (DIGIT+ | DIGIT+.DIGIT+);
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
EOS     : NEWLINE | EOF;
SKIP    : (WHITESPACE | COMMENT | NEWLINE) -> skip;
NAME    : ('_' | LETTER) ('_' | LETTER | DIGIT)*;
COMMENT : '#' ~[NEWLINE | EOF]*;

// fragments (helper definitions)
fragment QUOTE      : '\'';
fragment DIGIT      : '0'..'9';
fragment LETTER     : 'a'..'z' | 'A'..'Z';
fragment NEWLINE    : '\n' | '\r\n';
fragment WHITESPACE : [ \t]+;

