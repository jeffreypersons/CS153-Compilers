#!/usr/bin/env bash
# ========================= Generate and Build SimpL Sources ===================
# Build SimpL from sources
# Example usage: ./build-all.sh
#
# This script does the following steps:
# (1) generates Java source code for listener, visitor, parser, and tokens
#     using antlr4
# (2) compiles all of the source directory (generates .class files adjacent to
#     .java file)
# ==============================================================================

# validate parameters and configure vars
if [[ `basename "$PWD"` != HW06 ]]; then
  echo "  `basename "$0"` must be executed from working directory 'HW06'"
  exit 1
fi
if [ $# -ne 0 ]; then
  echo "  **Improper number of arguments**"
  echo "  Usage: ./build-all.sh"
  exit 1
fi
cwd=`realpath "$PWD"`;lib="$0/lib"
export CLASSPATH=".:$cwd/:$cwd/src/:$cwd/main/:$cwd/gen/:$lib/antlr-4.7-complete.jar:$lib/jasmin-2.4-complete.jar"

# generate antlr sources
java -jar ${cwd}/lib/antlr-4.7-complete.jar \
      ${cwd}/src/SimpL.g4 \
     -long-messages     \
     -encoding utf-8    \
     -listener -visitor \
     -package gen       \
     -o ${cwd}/src/gen \

# compile everything in src dir
find  ${cwd}/src -name '*.class' -type f -delete
javac ${cwd}/src/*.java
javac ${cwd}/SimpLMain.java
