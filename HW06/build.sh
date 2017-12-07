#!/usr/bin/env bash
# ========================= Generate and Build SimpL Sources ===================
# Build SimpL from sources
# Example usage: ./build.sh
#
# Requirements:
# (1) HW06 is currently the only supported working directory
# (2) No arguments are supported at this time
#
# This script does the following steps:
# (1) Generates Java source code for listener, visitor, parser, and tokens
#     using antlr4
# (2) Compiles all of the source directory (generates .class files adjacent to
#     .java file)
# ==============================================================================

# emulate realpath (not on all os) and then validate input
realpath() { [[ $1 = /* ]] && echo $1 || echo "$(pwd)/${1#./}" | sed 's/\/*$//g'; }
cwd=$(realpath)

# ensure working dir is HW06 and no arguments are given
if [[ $(basename $(pwd)) != HW06 ]]; then
    echo "**Error processing input for build.sh**"
    echo "  build.sh can only be run with HW06 as the working directory"
    exit 1
fi
if [ $# -ne 0 ]; then
    echo "**Error processing input for build.sh**"
    echo "  Invalid number of arguments"
    echo "  Run as $ ./build.sh"
    exit 1
fi

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
find . -name '*.java' > out/sources.txt
javac -cp "lib/antlr-4.7-complete.jar" -d out @out/sources.txt
