grammar simpL;

// TODO: update the BNF grammar description once the rest of the grammar file is done
/* The grammar for our language is as follows (in BNF):
<command>     -> <declaration> | <simple_expr>
<declaration> -> <primitive identifier> | <primitive assignment>
<assignment>  -> <identifier> <assignment_operatior> <basic_type>
<simple_expr> -> <boolean_expression> | <arithmetic_expression>
<arithmetic_expression> -> <value> <arithmetic_operator> <basic_type>
<boolean_expression>    -> <value> <boolean_operator> <value> |
                           <identifier> <boolean_operator> <identifier>
<boolean_operator> -> EQUIVILANCE | NOT | GT | LT | LTE | GTE
<arithmetic_expression> -> ADD | SUB | MUL | DIV | POW
<assignment_operator> -> ASSIGN
<basic_type> -> <text> | <value>
<identifier> -> ID
<primitive>  -> <value_keyword> | <word_keyword>
<value_keyword> -> NUMBER
<word_keyword>  -> TEXT
<value> -> NUMERICAL
<text>  -> ID
*/

// starting rule
program : command+;

// full command or statement ends with the EOL character
command	: (declaration | statement)* NEWLINE;
body	: (declaration | statement)*;

// declarations and assignments
declaration : (NUMBER | TEXT) IDENTIFIER | assignment;
assignment  : assign_num | assign_text;

// statements and expressions
statement     : simple_expr | compound_expr;
simple_expr   : bool_expr   | arith_expr;
compound_expr : func_def 	| if_stmt;
if_stmt       : IF LPAREN simple_expr RPAREN LBRACKET body RBRACKET
                    (ELSE_IF LPAREN simple_expr RPAREN LBRACKET body RBRACKET)* (ELSE LBRACKET body RBRACKET)?;
func_def      : DEF IDENTIFIER LPAREN param_list RPAREN LBRACKET body RBRACKET;
param_list    : (primitive IDENTIFIER (',' primitive IDENTIFIER)*)?;
arith_expr    : (value | IDENTIFIER)  (arith_operator (value | IDENTIFIER))+;
bool_expr     : value bool_operator value | identifier bool_operator identifier;

// groupings of reserved symbols
bool_operator  : EQUIV | NOT | GT  | LT  | LTE | GTE;
arith_operator : ADD   | SUB | MUL | DIV | POW;

// basic labels
basic_type     : text | value;
identifier     : IDENTIFIER;
primitive      : number_keyword | text_keyword;
number_keyword : NUMBER;
text_keyword   : TEXT;
assign_num     : NUMBER? IDENTIFIER ASSIGN (value | arith_expr | IDENTIFIER) NEWLINE;
assign_text    : TEXT? IDENTIFIER ASSIGN (text | IDENTIFIER) NEWLINE;
value          : NUMERIC;
text           : '\'' IDENTIFIER '\'';

// reserved words and symbols
NUMBER   : 'number';
TEXT     : 'text';
ASSIGN   : '=';
CONV     : '\'';
LPAREN   : '(';
RPAREN   : ')';
LBRACKET : '{';
RBRACKET : '}';
IF       : 'if';
ELSE     : 'else';
ELSE_IF  : 'else if';
DEF      : 'def';
RETURN   : 'return';

// boolean operators
EQUIV  : 'is';
NOT    : 'not';
GT     : '>';
LT     : '<';
LTE    : '<=';
GTE    : '>=';

// arithmetic operators
ADD : '+';
SUB : '-';
MUL : '*';
DIV : '/';
POW : '^';

// other
IDENTIFIER : [a-zA-Z]+[0-9a-zA-Z]*;
NEWLINE    : '\n' | '\r\n';
WHITESPACE : [ \t]+ -> skip;
NUMERIC    : ([0-9]+ | [0-9]+.[0-9]+);
