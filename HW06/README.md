# SimpL: The Simple Programming Language ©
## Setup

#### Building SimpL Codebase from sources
Build SimpL by executing `./build.sh` from project directory.
This should generate sources from antlr, an out folder with all compiled class files.

To ensure it runs on your system, execute `./test.sh` from project directory.


#### Running SimpL
Run a .simpl file by executing `./simpl.sh <source_file>`, or by running
`java SimpLMain <source_file>`.
This should produce a corresponding .j (jvm assembly) and .class (jvm bytecode)
adjacent to the given source file.

Tip: a helpful command for cleaning up class or jasmin files, use
`find <dir_name> -name '*.class' -type -delete` and `find <dir_name> -name '*.j' -type -delete`


#### Troubleshooting

If executing `java` or `javac` directly, ensure classpath is set correctly, as in
`export CLASSPATH=out:<jasmin2.4-jar-path>:<antlr4.7-jar-path>$CLASSPATH`

If permission denied errors occurred running scripts, then allow access by running
`chmod +x ./<file_name>.sh`

If newline issues occur after modifying the scripts on windows, use
`sed -i 's/\r$//' ./<file>` to remove windows newline characters


## Syntax
#### Overview
Only single programs are supported, of which consistent of multiple statements, each of which
are terminated with a line break.

Statements include function definition, declaration, assignment, standalone expression, conditional, and while loop.
Expressions are any mix of enclosed parentheses, literals, identifiers, function calls, and operations.

Datatypes currently include `Number` and `Text`.
The more elaborate `Map`, `List`, `Struct`, `Func` are planned for the future.


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
Syntax for conditionals is as follows:
```
if <expression>
    <0 or more statements>
elif <expression>
    <0 or more statements>
else
    <0 or more statements>
```
