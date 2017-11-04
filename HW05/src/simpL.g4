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

// full command or statement ends with the EOS character
command	: (declaration | statement)* EOS;
body	: (declaration | statement)*;

// declarations and assignments
declaration : primitive identifier | assignment;
assignment  : assign_num | assign_text;

// statements and expressions
statement     : simple_expr | compound_expr;
simple_expr   : bool_expr   | arith_expr;
compound_expr : if_stmt     | func_def;
if_stmt       : IF LPAREN simple_expr RPAREN body (ELSE_IF LPAREN simple_expr RPAREN)* (ELSE statement)?;
func_def      : DEF ID LPAREN param_list RPAREN LBRACKET body RBRACKET;
param_list    : (primitive ID (',' primitive ID)*)?;
arith_expr    : value (arith_operator value)+;
bool_expr     : value bool_operator value | identifier bool_operator identifier;

// groupings of reserved symbols
bool_operator  : EQUIV | NOT | GT  | LT  | LTE | GTE;
arith_operator : ADD   | SUB | MUL | DIV | POW;

// basic labels
basic_type    : text | value;
identifier    : ID;
primitive     : value_keyword | word_keyword ;
value_keyword : NUMBER;
word_keyword  : TEXT;
assign_num    : NUMBER ID ASSIGN (value | arith_expr) EOS;
assign_text   : TEXT ID ASSIGN text EOS;
value         : NUMERIC;
text          : '\'' ID '\'';

// reserved words and symbols
NUMBER   : 'number';
TEXT     : 'text';
EOS      : ';';
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
ID      : [a-zA-Z]+[0-9a-zA-Z]*;
WS 	    : [ \t\r\n]+ -> skip;
NUMERIC : ([0-9]+ | [0-9]+.[0-9]+);
