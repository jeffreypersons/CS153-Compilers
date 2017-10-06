# Assignment #3: When Statement

### compile all the files and generate an executable file
    make


### remove all generated object code and build related files

    make clean


### test when statement compilation and execution *_without_* errors
    ./Chapter8cpp compile -ix tests/when.txt > docs/when_compiled.txt

    ./Chapter8cpp execute tests/when.txt > docs/when_executed.txt

    printf '%b\n' "$(cat docs/when_compiled.txt)"

    printf '%b\n' "$(cat docs/when_executed.txt)"


### test when statement compilation and execution *_with_* errors
    ./Chapter8cpp compile -ix tests/whenerrors.txt > docs/whenerrors_compiled.txt

    ./Chapter8cpp execute tests/whenerrors.txt > docs/whenerrors_executed.txt

    printf '%b\n' "$(cat docs/whenerrors_compiled.txt)"

    printf '%b\n' "$(cat docs/whenerrors_executed.txt)"
