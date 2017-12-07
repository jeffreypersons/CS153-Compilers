#!/usr/bin/env bash
# ============================= Execute SimpL File =============================
# Run SimpL from sources
# Example usage: ./simpL.sh tests/basic_compile.simpl
#
# Requirements:
# (1) HW06 is currently the only supported working directory
# (2) Currently only a single file can be compiled/run at a time
# (3) File must end with single .simpl extension
#
# This script does the following steps:
# (1) Produce jasmin file from simpl file
# (2) Produce class file from jasmin file
# (3) Run class file
# ==============================================================================

get_extension() { echo "."$(echo $(basename $1) | cut -d '.' -f2-); }

# ensure working dir is HW06, and given argument is an existing simpl filepath
simplpath=${1}
if [[ $(basename $(pwd)) != HW06 ]]; then
    echo "**Error processing input file for simpl.sh**"
    echo "  simpl.sh can only be run with HW06 as the working directory"
    exit 1
fi
if [ $# -ne 1 ]; then
    echo "**Error processing input for simpl.sh**"
    echo "  Invalid number of arguments"
    echo "  Run as $ ./simpl.sh <source_filepath>.simpl"
    exit 1
fi
if [ ! -f ${simplpath} ]; then
    echo "**Error processing input for simpl.sh**"
    echo "  Invalid file path $simplpath"
    echo "  File does not exist"
    exit 1
fi
if [[ $(get_extension ${simplpath}) != .simpl ]]; then
    echo "**Error processing input for simpl.sh**"
    echo "  Invalid file extension for file $simplpath"
    echo "  Only .simpl files are supported"
    exit 1
fi

# ------ produce jasmin file from simpl file (todo: split up compile/run using commandline arguments)
export CLASSPATH="out:lib/jasmin-2.4-complete.jar:lib/antlr-4.7-complete.jar"
java -cp ${CLASSPATH} SimpLMain ${simplpath}
