# files to change in _wci_ for homework #3

### wci/frontend/pascal
- PascalTokenType.java

### wci/frontend/pascal/parsers
- PascalSpecialSymbolToken.java
- StatementParser.java
- WhenStatementParser.java

### wci/intermediate/icodeimpl
- ICodeNodeImpl.java

### wci/backend/interpreter/executors
- StatementExecutor.java
- WhenExecutor.java


# building and compiling the codebase

### compile all the files and generate an executable file
    make

### remove all generated object code and build related files

    make clean

### run the Pascal interpreter to execute source file hello.pas

    make execute src=hello.pas

### run the Pascal compiler to generate object code for source file hilbert.pas
    
    make compile src=hilbert.pas


# building and running pascal statements

### test if statement compilation *_with_* AND *_without_* errors
    ./Chapter8cpp compile -ix tests/if.txt

    ./Chapter8cpp execute tests/if.txt

    ./Chapter8cpp compile -ix tests/iferrors.txt

    ./Chapter8cpp execute tests/iferrors.txt


### test when statement compilation and execution *_without_* errors
    ./Chapter8cpp compile -ix tests/when.txt > when_compiled.txt

    ./Chapter8cpp execute tests/when.txt > when_executed.txt

    printf '%b\n' "$(cat when_compiled.txt)"

    printf '%b\n' "$(cat when_executed.txt)"


### test when statement compilation and execution *_with_* errors
    ./Chapter8cpp compile -ix tests/whenerrors.txt > whenerrors_compiled.txt

    ./Chapter8cpp execute tests/whenerrors.txt > whenerrors_executed.txt

    printf '%b\n' "$(cat whenerrors_compiled.txt)"

    printf '%b\n' "$(cat whenerrors_executed.txt)"
