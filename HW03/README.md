## Files to change in _wci_ for homework #3
wci/frontend/pascal
- PascalTokenType.java


wci/frontend/pascal/parsers
- PascalSpecialSymbolToken.java
- StatementParser.java
- WhenStatementParser.java


wci/intermediate/icodeimpl
- ICodeNodeImpl.java

wci/backend/interpreter/executors
- StatementExecutor.java
- WhenExecutor.java


## Make file commands
make clean
    
    removes all generated files to start fresh

make
    
    compiles all the files and generate an executable file

make execute src=hello.pas
    
    runs the Pascal interpreter to execute source file hello.pas

make compile src=hilbert.pas
    
    runs the Pascal compiler to generate object code for source file hilbert.pas
