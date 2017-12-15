# SimpL: The Simple Programming Language ©


## Getting Started

    See docs/Simpl Setup.pdf


## Language Overview

    See docs/Simpl Overview.pdf


## Project Structure

    docs
    src
        exceptions
        gen
        main
        utils
        SimpLCompiler.java
        SimpLMain.java
    
    SimpL                   # project directory
        \lib                # third party library jars
        
        \examples           # SimpL programs examples
        
        \docs               # language overview, diagrams, etc
        
        \src                # source directory
            \exceptions     # collection of exceptions specific to SimpL
            \gen            # antlr generated listener/visitor/parser/tokens files
            \main           # core code including message handling, visitor, jasmin code emitter
            \utils          # various utilities such as files
        
        \tests              # all SimpL test files go here
            \basic          # small, basic tests
            \comprehensive  # larger, more comprehensive tests
        
        simplc.sh           # script to compile a source input (.simpl) file
        
        simplr.sh           # script to run a compiled source (.class) file
        
        build.sh            # script to generate antlr sources, compile codebase, and run tests
        
        ...                 # readme/simpl scripts/etc files go here in outermost directory
