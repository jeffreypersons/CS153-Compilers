/*
 * TODO: add string concatenation
 * TODO: refactor how we deal with expressions
 */
grammar simpL;

// starting rule
program : command*;
command	: (declaration | statement)* STMTEND;
body	: (declaration | statement)*;

// declarations and assignments
declaration : basic_type_name IDENTIFIER | assignment;
assignment
    : TEXT? IDENTIFIER ASSIGN (TEXT_VALUE | IDENTIFIER) STMTEND
    | NUMBER? IDENTIFIER ASSIGN (NUMBER_VALUE | arith_expr | IDENTIFIER) STMTEND
    | BOOLEAN? IDENTIFIER ASSIGN (BOOLEAN_VALUE | IDENTIFIER) STMTEND;

// statements and expressions
statement     : simple_expr | compound_expr;
simple_expr   : bool_expr   | arith_expr;
compound_expr : func_def 	| if_stmt;
if_stmt       : IF OPEN_PAREN simple_expr CLOSE_PAREN OPEN_BRACE body CLOSE_BRACE
                    (ELSE_IF OPEN_PAREN simple_expr CLOSE_PAREN OPEN_BRACE body CLOSE_BRACE)* (ELSE OPEN_BRACE body CLOSE_BRACE)?;
func_def      : DEF IDENTIFIER OPEN_PAREN param_list CLOSE_PAREN
                    OPEN_BRACE body CLOSE_BRACE;
param_list    : (basic_type_name IDENTIFIER (SEPARATOR basic_type_name IDENTIFIER)*)?;
arith_expr    : arith_expr (ADD | SUB) term | term;
term          : term (MUL | DIV) power | power;
power         : factor POW power | factor;
factor        : OPEN_PAREN arith_expr CLOSE_PAREN | IDENTIFIER | NUMBER_VALUE;
bool_expr     : BOOLEAN_VALUE bool_operator BOOLEAN_VALUE | identifier bool_operator identifier;

// groupings of reserved symbols
comp_operator  : IS_EQUAL  | NOT_EQUAL | GT  | LT  | LTE | GTE;
bool_operator  : AND | OR  | NOT;
arith_operator : ADD | SUB | MUL | DIV | POW;

// basic labels
identifier       : IDENTIFIER;
basic_type_name  : TEXT       | NUMBER       | BOOLEAN;
basic_type_value : TEXT_VALUE | NUMBER_VALUE | BOOLEAN_VALUE;

// values
TEXT_VALUE    : QUOTE ~[QUOTE]* QUOTE;
NUMBER_VALUE  : ([0-9]+ | [0-9]+.[0-9]+);
BOOLEAN_VALUE : 'true' | 'false';

// reserved words
TEXT    : 'Text';
NUMBER  : 'Number';
BOOLEAN : 'Boolean';
IF      : 'if';
ELSE    : 'else';
ELSE_IF : 'else if';
DEF     : 'def';
RETURN  : 'return';

// reserved symbols
ASSIGN      : '=';
QUOTE       : '\'';
SEPARATOR   : ',';
OPEN_PAREN  : '(';
CLOSE_PAREN : ')';
OPEN_BRACE  : '{';
CLOSE_BRACE : '}';
OPEN_BRACK  : '[';
CLOSE_BRACK : ']';

// comparison and boolean operators (note that earlier the definition, earlier the precedence)
GT        : '>';
LT        : '<';
LTE       : '<=';
GTE       : '>=';
NOT       : 'not';
IS_EQUAL  : '!=';
NOT_EQUAL : '==';
AND       : 'and';
OR        : 'or';

// arithmetic operators
ADD : '+';
SUB : '-';
MUL : '*';
DIV : '/';
POW : '^';

// text input
WHITESPACE : [ \t]+ -> skip;
COMMENT    : '#' ~[\r\n] -> skip;
// variable name (defined last so that any of the above keywords can't be used)
IDENTIFIER : [_a-zA-Z]+[_0-9a-zA-Z]*;
STMTEND : NEWLINE+;

// fragments (helper definitions)
fragment DIGIT   : [0-9];
fragment NEWLINE : '\n' | '\r\n';

