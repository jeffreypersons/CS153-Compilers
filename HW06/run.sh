#!/usr/bin/env bash
# =================== Download Single Directory from Github ====================
# build and run HW06
# example usage: ./run.sh tests/basic_compile.txt
# use `chmod +x ./<file_name>.sh` before running for first time
# use `sed -i 's/\r$//' ./<file>` if newline errors (eg $'\r')
# ==============================================================================

# todo: break this up into two or more build/run scripts...
# ensure current working directory is HW06 (this restriction is only needed for the build portion)
if [[ `basename "$PWD"` != HW06 ]]; then
  echo "  `basename "$0"` must be executed from working directory HW06"
  exit 1
fi
# ensure exactly two arguments are given
if [ $# -ne 1 ]; then
  echo "  **Improper number of arguments**"
  echo "  Usage: ./run.sh <source_file_name>"
  echo "  For example: ./run.sh tests/basic_compile.txt"
  exit 1
fi
source_file_name=$1

# configure classpath from dependencies and build paths
export CLASSPATH="./lib/jasmin-2.4-complete.jar:./lib/antlr-4.7-complete.jar:./src/:./src/main:./src/gen/:$CLASSPATH"

# generate Java code according to the SimpL grammar file
# output is written to src/gen, consisting of code for tokens/parsers/listeners/visitors
java -jar lib/antlr-4.7-complete.jar -o src/gen -package gen src/SimpL.g4 -long-messages -listener -visitor -encoding utf-8

# delete any existing class files and compile source
find ./src -name '*.class' -type f -delete
javac ./src/main/*.java
javac ./src/SimpLMain.java
java SimpLMain ${source_file_name}

# todo: incorporate the below jasmin assemble command
# java -jar lib/jasmin-2.4-complete.jar <file_name.j>
