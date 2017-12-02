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
# emulate realpath (not on all os) and then validate input
realpath() { [[ $1 = /* ]] && echo $1 || echo "$(pwd)/${1#./}" | sed 's/\/*$//g'; }
if [[ $(pwd) != *HW06 ]]; then
    echo "  simpl.sh must be executed from HW06 as the working directory"
    exit 1
fi
if [ $# -ne 0 ]; then
    echo "  **Invalid number of arguments**"
    echo "  Usage: ./build.sh"
    exit 1
fi
cwd=$(realpath)

# generate antlr sources using absolute paths to avoid tool conflicts
echo "Generating antlr4 source files into ./src/gen"
java -jar ${cwd}/lib/antlr-4.7-complete.jar \
      ${cwd}/src/SimpL.g4 \
     -long-messages       \
     -encoding utf-8      \
     -listener -visitor   \
     -package gen         \
     -o ${cwd}/src/gen    \

# generate class files in out dir
echo "Compiling all Java files in ./src sources into ./out"
rm -rf out; mkdir out
find -name '*.java' > out/sources.txt
javac -cp "lib/antlr-4.7-complete.jar" -d out @out/sources.txt
