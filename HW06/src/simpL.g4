grammar simpL;

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
if_stmt       : IF OPEN_PAREN simple_expr CLOSE_PAREN OPEN_BRACE body CLOSE_BRACE
                    (ELSE_IF OPEN_PAREN simple_expr CLOSE_PAREN OPEN_BRACE body CLOSE_BRACE)* (ELSE OPEN_BRACE body CLOSE_BRACE)?;
func_def      : DEF IDENTIFIER OPEN_PAREN param_list CLOSE_PAREN OPEN_BRACE body CLOSE_BRACE;
param_list    : (primitive IDENTIFIER (',' primitive IDENTIFIER)*)?;
arith_expr    : arith_expr (ADD | SUB) term | term;
term          : term (MUL | DIV) power | power;
power         : factor POW power | factor;
factor        : OPEN_PAREN arith_expr CLOSE_PAREN | IDENTIFIER | NUMERIC;
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
OPEN_PAREN  : '(';
CLOSE_PAREN : ')';
OPEN_BRACE  : '{';
CLOSE_BRACE : '}';
OPEN_BRACK  : '[';
CLOSE_BRACK : ']';
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
IDENTIFIER : [_a-zA-Z]+[_0-9a-zA-Z]*;
NEWLINE    : '\n' | '\r\n';
WHITESPACE : [ \t]+ -> skip;
NUMERIC    : ([0-9]+ | [0-9]+.[0-9]+);
