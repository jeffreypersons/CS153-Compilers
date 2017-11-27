# SimpL Programming Language Overview

## Syntax Description
- Coming Soon

### Operators
```
--------- Operator Precedence (HI to LO) ---------
| order |  operator  |           meaning         |
|   0   |    ()      |         parenthesis       |
|   1   |     ^      |        exponentiation     |
|   2   |    * /     |     multiply and divide   |
|   3   |    + -     |       add and subtract    |
|   4   | < > <= >=  |         comparison        |
|   5   |   == !=    |          equality         |
|   6   |    not     |      logical negation     |
|   7   |    and     |     logical conjunction   |
|   8   |    or      |     logical disjunction   |
--------------------------------------------------
```
### Format
- requires ALL statements and files to end with a line break, and braces MUST be on
  separate line

### DataTypes
- Number, Text
- Map, List, Struct, Func (first class function) to arrive in future

### Conditionals
- single 'if';
- followed by any number of 'else if'
- and ending in an optional 'else'
- Note: As of current grammar, no Egyptian style brace format is supported
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
