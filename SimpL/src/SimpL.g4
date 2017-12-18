grammar SimpL;

program : stat*;
stat
    : func_def
    | conditional
    | while_loop
    | declaration
    | assignment
    | expr EOL
    ;
declaration
    : TYPE NAME (ASSIGN expr)? EOL
    ;
assignment
    : NAME ASSIGN expr EOL
    ;
while_loop
    : 'while' expr block
    ;
conditional
    : ('if' expr block)
      ('elif' expr block)*
      ('else' block)?
    ;
func_def
    : (TYPE | 'Void') NAME LPAREN (TYPE NAME (SEPARATOR TYPE NAME)*)? RPAREN block
    ;
// any number of statements enclosed in braces, ending with an optional return
block
    : EOL LCURL EOL
          stat*
          ('return' expr EOL)?
      RCURL EOL  // no need for EOL here, since statement requires it
    ;
// todo: to make the evaluation rules more clear, incorporate ANTLR's syntax for left/right associativity
// note that left recursion is dealt with successfuly in ANTLR4, as it rewrites the below as unambiguous recursive rules
expr
    : NAME
    | LITERAL
    | func_call
    | LPAREN expr RPAREN
    | expr POW expr
    | expr (MUL | DIV) expr
    | expr (ADD | SUB) expr
    | expr (LT  | GT | LTE | GTE) expr
    | expr (EQ  | NEQ) expr
    | NOT expr
    | expr AND expr
    | expr OR expr
    ;
func_call : NAME LPAREN (expr (SEPARATOR expr)*)? RPAREN;
TYPE      : TEXT       | NUMBER       | BOOLEAN;
LITERAL   : TEXT_VALUE | NUMBER_VALUE | BOOLEAN_VALUE;

// data types and values
NONE          : 'None';
TEXT          : 'Text';
NUMBER        : 'Number';
BOOLEAN       : 'Boolean';
TEXT_VALUE    : QUOTE (~['\r\n] | '\\\'' | '\\\\')* QUOTE;  // one line text literal, \' and \\ is OK
NUMBER_VALUE  : DIGIT+ | DIGIT+.DIGIT+;
BOOLEAN_VALUE : 'true' | 'false';

// reserved symbols
SEPARATOR : ',';
LPAREN    : '(';
RPAREN    : ')';
LCURL     : '{';
RCURL     : '}';
LSQUARE   : '[';
RSQUARE   : ']';

// operators
POW    : '^';
MUL    : '*';
DIV    : '/';
ADD    : '+';
SUB    : '-';
LT     : '<';
GT     : '>';
LTE    : '<=';
GTE    : '>=';
EQ     : '==';
NEQ    : '!=';
NOT    : 'not';
AND    : 'and';
OR     : 'or';
ASSIGN : '=';

// fundamental tokens
EOL           : NEWLINE+;
NAME          : ('_' | LETTER) ('_' | LETTER | DIGIT)*;
WHITESPACE    : (LINE_COMMENT | [ \t]+) -> skip;
LINE_COMMENT  : ('#' .*? NEWLINE) -> skip;
BLOCK_COMMENT : ('##' .*? '##')   -> skip;

// fragments (helper definitions)
fragment QUOTE     : '\'';
fragment DIGIT     : '0'..'9';
fragment LETTER    : 'a'..'z' | 'A'..'Z';
fragment NEWLINE   : '\n' | '\r\n';
fragment BACKSLASH : '\\';
