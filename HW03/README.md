# Assignment #3: When Statement


### compile all the files and generate an executable file

    make


### remove all generated object code and build related files

    make clean


### test when statement execution

    make execute src=tests/when.txt > tests/when_out.txt
    
    printf '%b\n' "$(cat tests/when_out.txt)"


### test when statement execution with errors

    make execute src=tests/whenerrors.txt > tests/whenerrors_out.txt
    
    printf '%b\n' "$(cat tests/whenerrors_out.txt)"
