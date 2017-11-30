#!/usr/bin/env bash
# ========================= Generate and Build SimpL Sources ===================
# Build SimpL from sources
# Example usage: ./build-src.sh
#
# This script does the following steps:
# (1) generates Java source code for listener, visitor, parser, and tokens
#     using antlr4
# (2) compiles all of the source directory (generates .class files adjacent to
#     .java file)
# ==============================================================================
# validate parameters and configure vars (todo: add option to gen/not gen srcs)
if [[ `basename "$PWD"` != HW06 ]]; then
  echo "  `basename "$0"` must be executed from working directory 'HW06'"
  exit 1
fi
if [ $# -ne 0 ]; then
  echo "  **Improper number of arguments**"
  echo "  Usage: . ./build-src.sh"
  exit 1
fi
export CLASSPATH=".:src:src/gen:src/utils:src/main:lib/jasmin-2.4-complete.jar:lib/antlr-4.7-complete.jar"

# emulate realpath since it doesn't exist on mac
realpath() {
    [[ $1 = /* ]] && echo "$1" || echo "$PWD/${1#./}"
}
cwd=`realpath`

# generate antlr sources using absolute paths to avoid tool conflicts
java -jar ${cwd}/lib/antlr-4.7-complete.jar \
      ${cwd}/src/SimpL.g4 \
     -long-messages       \
     -encoding utf-8      \
     -listener -visitor   \
     -package gen         \
     -o ${cwd}/src/gen    \

# generate class files in out dir
rm -rf out; mkdir out
find -name '*.java' > out/sources.txt
javac -cp ${CLASSPATH} -d out @out/sources.txt
