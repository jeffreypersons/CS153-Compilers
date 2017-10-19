# Assignment #2: Java Scanner


### compile all the files and generate an executable file

    make


### remove all generated object code and build related files

    make clean


### test scanner on java source

    make execute src=tests/javatest.in > tests/java_out.txt
    
    printf '%b\n' "$(cat tests/java_out.txt)"


### test scanner on pascal source

    make execute src=tests/hello.pas > tests/pascal_out.txt
    
    printf '%b\n' "$(cat tests/pascal_out.txt)"
