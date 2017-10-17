# Assignment #2: Java Scanner

### compile all the files and generate an executable file
    make


### remove all generated object code and build related files

    make clean


### test scanner on java source

    ./Chapter3cpp compile javatest.in > java_out.txt

    printf '%b\n' "$(cat java_out.txt)"


### test scanner on pascal source
    
    ./Chapter3cpp compile hello.pas > pascal_out.txt
    
    printf '%b\n' "$(cat pascal_out.txt)"
