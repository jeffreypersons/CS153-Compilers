# SimpL: The Simple Programming Language ©

## Overview
Only single programs are supported, of which consistent of multiple statements,
each of which are terminated with a line break.

Statements include function definition, declaration, assignment,
standalone expression, conditional, and while loop.

Blocks consist of a `{` <0 or more statements> `}`, with the braces on their own
lines. Blocks are expected following conditionals, function signatures, and
loops. Again, curly braces MUST be on their own separate lines - this means
egyptian/K & R style braces are NOT supported, sorry!

Expressions are any mix of enclosed parentheses, literals, identifiers,
function calls, and operations.

Datatypes currently include `Number` and `Text`.


#### Operators
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


#### Conditionals
Here the block is:
```
Reca
```
(here the block is { `<0 or more statements>`} recall that a block is
`<0 or more statements>` ending with an optional return):
Syntax for conditionals is as follows:
```
if <expression>
{
    <0 or more statements>
}
elif <expression>
{
    <0 or more statements>
}
else
{
    <0 or more statements>
}
```

#### Loops
Syntax for conditionals is as follows:
```
while <expression>
{
    <0 or more statements>
}
```
