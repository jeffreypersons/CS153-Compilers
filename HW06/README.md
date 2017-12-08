# SimpL Programming Language Overview

## Building SimpL Codebase from sources
    
    Build SimpL by executing `./build.sh` from project directory.
    This should generate sources from antlr, an out folder with all compiled class files.
    
    To ensure it runs on your system, execute `.test.sh` from project directory.

#### Running SimpL

    Run a .simpl file by executing `./simpl.sh <source_file>`, or by running
    `java SimpLMain <source_file>`.
    This should produce a corresponding .j (jvm assembly) and .class (jvm bytecode)
    adjacent to the given source file.

### Troubleshooting

1. If executing `java` or `javac` directly, ensure classpath is set correctly, as in
   `export CLASSPATH=out:<path-to-jasmin2.4-jar>:<path-to-antlr4.7-jar>$CLASSPATH`
2. If permission denied errors occurred running scripts, then allow access by running
   `chmod +x ./<file_name>.sh`
3. If newline issues occur after modifying the scripts on windows, use
   `sed -i 's/\r$//' ./<file>` to remove windows newline characters


## Syntax

#### Operators
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


#### Format
- requires ALL statements and files to end with a line break, and braces MUST be on
  separate line


#### DataTypes
- Number, Text
- Map, List, Struct, Func (first class function) to arrive in future


#### Conditionals
- single 'if';
- followed by any number of 'else if'
- and ending in an optional 'else'
- Note: As of current grammar, no Egyptian style brace format is supported

```
if <expression>
    <0 or more statements>
elif <expression>
    <0 or more statements>
else
    <0 or more statements>

```
