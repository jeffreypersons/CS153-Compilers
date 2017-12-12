#!/usr/bin/env bash
# ============================= Execute SimpL File =============================
# Compile and run a single SimpL file
# Example usage: ./simplc.sh tests/basic_compile.simpl
# ==============================================================================

# ensure working dir is HW06 and single argument is given
source_file=${1}
if [[ $(basename $(pwd)) != HW06 ]]; then
    echo "**Error processing input for simplc.sh**"
    echo "  simplc.sh can only be run with HW06 as the working directory"
    exit 1
fi
if [ ${#} -ne 1 ]; then
    echo "**Error processing input for simplc.sh**"
    echo "  Invalid number of arguments"
    echo "  Run as $ ./simplc.sh <source_filepath>"
    exit 1
fi

# compile simpl file with jasmin and antlr libraries in classpath
export CLASSPATH="out:lib/jasmin-2.4-complete.jar:lib/antlr-4.7-complete.jar:${CLASSPATH}"
java -cp ${CLASSPATH} SimpLMain ${source_file}
