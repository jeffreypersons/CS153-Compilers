grammar simpL;

/* The grammar for our language is as follows (in BNF):
<command> -> <declaration> | <simple_expr>
<declaration> -> <primitive identifier> | <primitive assignment>
<assignment>  -> <identifier> <assignment_operatior> <basic_type>
<simple_expr> -> <boolean_expression> | <arithmetic_expression>
<arithmetic_expression> -> <value> <arithmetic_operator> <basic_type>
<boolean_expression> -> <value> <boolean_operator> <value> |
                        <identifier> <boolean_operator> <identifier>
<boolean_operator> -> EQUIVILANCE | NOT | GT | LT | LTE | GTE
<arithmetic_expression> -> ADD | SUB | MUL | DIV | POW
<assignment_operator> -> ASSIGN
<basic_type> -> <word> | <value>
<identifier> -> ID
<primitive>  -> <value_keyword> | <word_keyword>
<value_keyword> -> NUMBER
<word_keyword> -> WORD
<value> -> NUMERICAL
<word> -> ID
*/

program : command+;

// full command or statement ends with the EOS character
command	: (declaration | statement) EOS;

// declaration
declaration : primitive identifier | primitive assignment;
assignment: identifier assignment_operator basic_type;

statement : simple_expr | compound_expr;
simple_expr	: boolean_expression | arithmetic_expression;
compound_expr : if_stmt;
if_stmt : IF LPAREN simple_expr RPAREN statement | ELSE_IF LPAREN simple_expr RPAREN statement | ELSE statement;
arithmetic_expression 	: value arithmetic_operator value;
boolean_expression: value boolean_operator value | identifier boolean_operator identifier;

// groupings of reserved symbols
boolean_operator 	: EQUIVILANCE | NOT | GT | LT | LTE | GTE;
arithmetic_operator : ADD | SUB | MUL | DIV | POW;
assignment_operator: ASSIGN;

// basic labels
basic_type: word | value;
identifier: ID;
primitive : value_keyword | word_keyword ;
value_keyword: NUMBER;
word_keyword: WORD;
value : NUMERICAL;
word: ID; // change to string

// Reserved Words and symbols
NUMBER 	: 	'number';
WORD	:	'word';
EOS		: '.'; // end of statement as opposed to ;
ASSIGN	: '=';
CONV	: '"';
LPAREN  : '(';
RPAREN  : ')';
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
