# SimpL Programming Language Overview

## Setup

##### Troubleshooting
- Ensure your working from the HW06 directory (this will change in future versions)
- Ensure shell script permissions are set, use `chmod +x ./<file_name>.sh` before
  running for the first time
- Ensure newlines and utf-8 are set for the scripts, use `sed -i 's/\r$//' ./<file>`
  on any shell scripts that are failing due to newlines issues such as $'\r', etc

##### Building SimpL

    ./build.sh

##### Running SimpL

    ./simpl.sh <source_file>

##### For example

    ./build-all.sh
    ./simpl.sh tests/basic_compile.txt


## Syntax

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
