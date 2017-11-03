grammar simpL;

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


program : command+;

// full command or statement ends with the EOS character
command	: (declaration | statement) EOS;

// declaration
declaration : primitive (identifier | assignment);
assignment: assign_num | assign_text;

statement : simple_expr | compound_expr;
simple_expr	: boolean_expression | arithmetic_expression;
compound_expr : if_stmt | funcdef;
if_stmt : IF LPAREN simple_expr RPAREN statement (ELSE_IF LPAREN simple_expr RPAREN)* (ELSE statement)?;
funcdef:DEF ID LPAREN paramlist RPAREN LBRACKET statement RBRACKET;
paramlist: (primitive ID (',' primitive ID)*)?;
arithmetic_expression 	: value arithmetic_operator value;
boolean_expression: value boolean_operator value | identifier boolean_operator identifier;

// groupings of reserved symbols
boolean_operator 	: EQUIVILANCE | NOT | GT | LT | LTE | GTE;
arithmetic_operator : ADD | SUB | MUL | DIV | POW;

// basic labels
basic_type: text | value;
identifier: ID;
primitive : value_keyword | word_keyword ;
value_keyword: NUMBER;
word_keyword: TEXT;
assign_num: NUMBER ID ASSIGN (NUMBER | arithmetic_expression);
assign_text: TEXT ID ASSIGN TEXT;
value : NUMERICAL;
text: ID; // change to string

// Reserved Words and symbols
NUMBER 	: 	'number';
TEXT	:	'text';
EOS		: '.'; // end of statement as opposed to ;
ASSIGN	: '=';
CONV	: '"';
LPAREN  : '(';
RPAREN  : ')';
LBRACKET: '{';
RBRACKET: '}';
IF      : 'if';
ELSE    : 'else';
ELSE_IF : 'else if';
DEF     : 'def';
RETURN  : 'return';

// boolean operators
EQUIVILANCE	: 'is';
NOT			: 'not';
GT			: '>';
LT			: '<';
LTE			: '<=';
GTE			: '>=';

// arithmetic operators
ADD		: '+';
SUB		: '-';
MUL		: '*';
DIV		: '/';
POW		: '^';

ID 	: [a-zA-Z]+[0-9a-zA-Z]*;
WS 	: [ \t\r\n]+ -> skip;
NUMERICAL : ([0-9]+ | [0-9]+.[0-9]+);
