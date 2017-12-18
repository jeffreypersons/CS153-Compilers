# SimpL ©: Setup Guide

## Building SimpL Source
To build SimpL, navigate to the SimpL directory and run:

    ./build.sh

The above will generate antlr sources, compile all java into an `out` folder,
and run various tests. The project is built with the only dependencies being
the JVM assembler `Jasmin` and the parser generator `Antlr`.


## Compiling a SimpL program
To compile a `.simpl` file, navigate to the SimpL directory and run:

    ./simplc.sh <source_filepath>

The above will generate .j (JVM assembly) and a .class (JVM bytecode) files of
the same name. The output location defaults to the parent directory of the given
sourcefile, but can be specified with the command line option `-d`
(to be added in the near future).


#### Executing a SimpL program
To execute a program, run it by specifying its compiled class filepath by using:

    ./simplr.sh <class_filepath>

The above will run the compiled SimpL program with any output printed to
console.


#### Troubleshooting and Tips

If executing `java` or `javac` directly, ensure classpath is set correctly by
using:
    
    export CLASSPATH="out:<jasmin2.4-jar-path>:<antlr4.7-jar-path>:$CLASSPATH"

If a `permission denied` error occurred while running a script, grant access by
using:

    chmod +x ./<script_filepath>.sh

If newline issues occur after modifying the scripts on windows, remove excess
new line characters by using:

    sed -i 's/\r$//' ./<script_filepath>

Delete any generated jasmin and class files by using (optionally limit depth by
adding `-maxdepth 1`):

    find <output_directorypath> -regex ".*\.\(j\|class\)" -type f -delete

