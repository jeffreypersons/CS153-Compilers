#!/usr/bin/env bash
# ================================ Build SimpL =================================
# Generate antlr sources, compile codebase, and run tests
# Example usage: ./build.sh
# ==============================================================================

# ensure working dir is HW06 and no arguments are given
if [[ $(basename $(pwd)) != HW06 ]]; then
    echo "**Error processing input for build.sh**"
    echo "  build.sh can only be run with HW06 as the working directory"
    exit 1
fi
if [ ${#} -ne 0 ]; then
    echo "**Error processing input for build.sh**"
    echo "  Invalid number of arguments"
    echo "  Run as $ ./build.sh"
    exit 1
fi

# define realpath command (since unavailable on OSX) to get full path of working directory
realpath() { [[ $1 = /* ]] && echo $1 || echo "$(pwd)/${1#./}" | sed 's/\/*$//g'; }
cwd=$(realpath)

# generate simpl listener/visitor/parser/tokens files, using full paths to avoid conflicts
echo "Generating antlr4 source files into ./src/gen"
java -jar lib/antlr-4.7-complete.jar \
      ${cwd}/src/SimpL.g4 \
     -long-messages       \
     -encoding utf-8      \
     -listener -visitor   \
     -package gen         \
     -o ${cwd}/src/gen    \

# compile entire SimpL compiler codebase with antlr4.7 and jasmin2.4 libraries
echo "Compiling all Java files in ./src sources into ./out"
rm -rf out; mkdir out
find . -name '*.java' > out/sources.txt
export CLASSPATH="lib/jasmin-2.4-complete.jar:lib/antlr-4.7-complete.jar"
javac -cp lib/jasmin-2.4-complete.jar:lib/antlr-4.7-complete.jar \
      -d out @out/sources.txt \

# todo: add tests here using the antlr tool
