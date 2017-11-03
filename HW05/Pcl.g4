grammar Pcl;  // A tiny subset of Pascal

program : header block '.' ;
header  : PROGRAM IDENTIFIER ';' ;
block   : declarations compound_stmt ;

declarations : VAR decl_list ';' ;
decl_list    : decl ( ';' decl )* ;
decl         : var_list ':' type_id ;
var_list     : var_id ( ',' var_id )* ;
var_id       : IDENTIFIER ;
type_id      : IDENTIFIER ;

compound_stmt : BEGIN stmt_list END ;

stmt : compound_stmt    # compoundStmt
     | assignment_stmt  # assignmentStmt
     | repeat_stmt      # repeatStmt
     | if_stmt          # ifStmt
     |                  # emptyStmt
     ;

stmt_list       : stmt ( ';' stmt )* ;
assignment_stmt : variable ':=' expr ;
repeat_stmt     : REPEAT stmt_list UNTIL expr ;
if_stmt         : IF expr THEN stmt ( ELSE stmt )? ;

variable : IDENTIFIER ;

expr : expr mul_div_op expr     # mulDivExpr
     | expr add_sub_op expr     # addSubExpr
     | expr rel_op expr         # relExpr
     | number                   # numberConst
     | IDENTIFIER               # identifier
     | '(' expr ')'             # parens
     ;

number : sign? INTEGER ;
sign   : '+' | '-' ;

mul_div_op : MUL_OP | DIV_OP ;
add_sub_op : ADD_OP | SUB_OP ;
rel_op     : EQ_OP | NE_OP | LT_OP | LE_OP | GT_OP | GE_OP ;

PROGRAM : 'PROGRAM' ;
BEGIN   : 'BEGIN' ;
END     : 'END' ;
VAR     : 'VAR' ;
REPEAT  : 'REPEAT' ;
UNTIL   : 'UNTIL' ;
IF      : 'IF' ;
THEN    : 'THEN' ;
ELSE    : 'ELSE';

IDENTIFIER : [a-zA-Z][a-zA-Z0-9]* ;
INTEGER    : [0-9] ;

MUL_OP :   '*' ;
DIV_OP :   '/' ;
ADD_OP :   '+' ;
SUB_OP :   '-' ;

EQ_OP : '=' ;
NE_OP : '<>' ;
LT_OP : '<' ;
LE_OP : '<=' ;
GT_OP : '>' ;
GE_OP : '>=' ;

NEWLINE : '\r'? '\n' -> skip  ;
WS      : [ \t]+ -> skip ;
