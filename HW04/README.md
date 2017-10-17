# Assignment #4: Builtin Complex Types


### compile all the files and generate an executable file

    make


### remove all generated object code and build related files

    make clean


### test complex data types

    make execute src=Complex.pas > complex_executed.txt
    
    printf '%b\n' "$(cat complex_executed.txt)"


### test complex builtins

    make execute src=ComplexBuiltIn.pas > complexbuiltin_executed.txt
    
    printf '%b\n' "$(cat complexbuiltin_executed.txt)"
