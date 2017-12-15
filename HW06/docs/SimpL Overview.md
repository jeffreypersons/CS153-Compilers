# SimpL: The Simple Programming Language ©


## Overview
Only single programs are supported, of which consistent of multiple statements,
each of which are terminated with a line break. Any curly braces must be on
their own separate lines -- no egyptian style braces, sorry!

Statements include function definition, declaration, assignment,
standalone expression, conditional, and while loop.

Expressions are any mix of enclosed parentheses, literals, identifiers,
function calls, and operations. Literals and identifier datatypes are
limited to `Boolean`, `Number` (internally casted to a double) and `Text`
(string).

Conditionals, loops, and function definitions are supported.
All of them have the following general structure:
```
<token> <expression>
{
    <0 or more statements>
    <optional return statement>
}
```
Note that the above braces and their enclosed statements form a block.
Thus, blocks follow all function signatures, as well as all while, if,
else if, else conditions.


## Operators
Support for parenthetical, arithmetic, boolean, comparison operations
```
-------- Operator Precedence (HI to LO) --------
| order |  operator  |         meaning         |
|   0   |     ()     |       parenthesis       |
|   1   |     ^      |      exponentiation     |
|   2   |    * /     |   multiply and divide   |
|   3   |    + -     |     add and subtract    |
|   4   | < > <= >=  |       comparison        |
|   5   |   == !=    |        equality         |
|   6   |    not     |    logical negation     |
|   7   |    and     |   logical conjunction   |
|   8   |    or      |   logical disjunction   |
------------------------------------------------
```
It's important to note that not/and/or is supported only for booleans


#### Control flow

Syntax for conditionals are as follows:
```
if <expression>
{
    <any number of statements>
}
elif <expression>
{
    <any number of statements>
}
else
{
    <any number of statements>
}
```

Syntax for loops are as follows:
```
while <expression>
{
    <0 or more statements>
}
```
