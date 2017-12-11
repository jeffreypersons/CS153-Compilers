#!/usr/bin/env bash
# ============================= Execute SimpL File =============================
# Run a compiled SimpL file by executing given classfile
# Example usage: ./simpl.sh tests/basic_compile.simpl
# ==============================================================================

# define function for full file extension with leading dot, and get class file path
get_extension() { echo '.'$(echo $(basename $1) | cut -d '.' -f2-); }

# ensure working dir is HW06 and no arguments are given
class_file=${1}
if [[ $(basename $(pwd)) != HW06 ]]; then
    echo "**Error processing input for simplr.sh**"
    echo "  simplr.sh can only be run with HW06 as the working directory"
    exit 1
fi
if [ $# -ne 1 ]; then
    echo "**Error processing input for simplr.sh**"
    echo "  Invalid number of arguments"
    echo "  Run as $ ./simplr.sh <source_file>"
    exit 1
fi
#  || [[ ! -f ${class_file} ]]; then
if [[ $(get_extension ${class_file}) != .class ]]; then
    echo "**Error processing input for simplr.sh**"
    echo "  Invalid filepath: ${class_file}"
    echo "  Filepath be an existing .class file"
    exit 1
fi

# include parent directory of class file in java classpath
program_name=$(basename ${class_file} .class)
export CLASSPATH="$(dirname ${class_file}):${CLASSPATH}"

# run class file using jvm
echo "---- ${program_name} output ----"
java -cp ${CLASSPATH} ${program_name} ${program_name}
