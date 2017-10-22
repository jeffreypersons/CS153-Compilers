# Assignment #4: Builtin Complex Types


### compile all the files and generate an executable file

    make


### remove all generated object code and build related files

    make clean


### simple test of complex data types

    make execute src=tests/BaseTest.pas > tests/basetest_out.txt
    
    printf '%b\n' "$(cat tests/basetest_out.txt)"


### more elaborate test of complex data types

    make execute src=tests/ComplexType.pas > tests/complextype_out.txt
    
    printf '%b\n' "$(cat tests/complextype_out.txt)"


### test complex builtins

    make execute src=tests/ComplexBuiltIn.pas > tests/complexbuiltin_out.txt
    
    printf '%b\n' "$(cat tests/complexbuiltin_out.txt)"
