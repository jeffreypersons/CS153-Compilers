#!/usr/bin/env bash
# ============================= Execute SimpL File =============================
# Compile and run a single SimpL file
# Example usage: ./simpl.sh tests/basic_compile.simpl
# ==============================================================================

# ensure working dir is HW06 and no arguments are given
if [[ $(basename $(pwd)) != HW06 ]]; then
    echo "**Error processing input for build.sh**"
    echo "  simpl.sh can only be run with HW06 as the working directory"
    exit 1
fi
if [ ${#} -ne 1 ]; then
    echo "**Error processing input for build.sh**"
    echo "  Invalid number of arguments"
    echo "  Run as $ ./simpl.sh <source_file>"
    exit 1
fi

# include jasmin and antlr libraries in classpath
source_file=${1}
export CLASSPATH="out:lib/jasmin-2.4-complete.jar:lib/antlr-4.7-complete.jar:${CLASSPATH}"

# run SimpL compiler with antlr4.7 and jasmin2.4 libraries
echo "Compiling and running: ${source_file}"
java -cp ${CLASSPATH} SimpLMain ${source_file}
