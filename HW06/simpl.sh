#!/usr/bin/env bash
# ============================= Execute SimpL File =============================
# Run SimpL from sources
# Example usage: ./simpL.sh tests/basic_compile.simpl
# Note that only a single .simpl file can be compiled/run at a time
# ==============================================================================

get_extension() { echo "."$(echo $(basename $1) | cut -d '.' -f2-); }

filepath=${1}
if [ $# -ne 1 ]; then
    echo "**Error processing input for simpl.sh**"
    echo "  Invalid number of arguments"
    echo "  Run as $ ./simpl.sh <source_filepath>.simpl"
    exit 1
fi

# run compiler with antlr4.7 and jasmin2.4 libraries
export CLASSPATH="out:lib/jasmin-2.4-complete.jar:lib/antlr-4.7-complete.jar"
java -cp ${CLASSPATH} SimpLMain ${filepath}
