# Assignment #6: Final Project First Draft with code generation

Using Eclipse, we got a working version of our initial code generation setup.

## Steps to build and run

    # delete any remaining class files, then compile gen, main, and simpLMain
    find src -name '*.class' -type f -delete
    javac -cp ".:src:src/main:src/gen:lib/antlr-4.7-complete.jar" src/gen/*.java
    javac -cp ".:src:src/main:src/gen:lib/antlr-4.7-complete.jar" src/main/*.java
    javac -cp ".:src:src/main:src/gen:lib/antlr-4.7-complete.jar" src/simpLMain.java
    
    # generate jasmin file from test program, then generate class file from jasmin, then run class file
    java -cp ".:src:src/main:src/gen:lib/antlr-4.7-complete.jar" simpLMain src/test_program
    java -jar lib/jasmin-2.4-complete.jar "src/test_program.j"
    java src.test_program
