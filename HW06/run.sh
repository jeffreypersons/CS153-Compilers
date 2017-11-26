#!/usr/bin/env bash
# =================== Download Single Directory from Github ====================
# build and run HW06
# use `chmod +x ./<file_name>.sh` before running for first time
# use `sed -i 's/\r$// ./<file>` if newline errors (eg $'\r')
# ==============================================================================

# ensure current working directory is HW06
if [[ `basename "$PWD"` != HW06 ]]; then
    echo "`basename "$0"` must be executed from working directory HW06"; exit
fi

# configure classpath from dependencies and build paths
export CLASSPATH="./lib/jasmin-2.4-complete.jar:./lib/antlr-4.7-complete.jar:./src/:./src/main:./src/gen/"

# delete any existing class files and compile source
find ./src -name '*.class' -type f -delete
javac ./src/main/*.java
javac ./src/simpLMain.java
java simpLMain tests/compile_test.txt
