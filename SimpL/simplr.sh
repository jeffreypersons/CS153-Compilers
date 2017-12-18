#!/usr/bin/env bash
# ============================= Execute SimpL File =============================
# Run a compiled SimpL file by executing given classfile
# Example usage: ./simplc.sh tests/basic/operator.class
# ==============================================================================

# define function for full file extension with leading dot, and get class file path
get_extension() { echo '.'$(echo $(basename $1) | cut -d '.' -f2-); }

# ensure working dir is SimpL and no arguments are given
class_file=${1}
if [[ $(basename $(pwd)) != SimpL ]]; then
    echo "**Error processing input for simplr.sh**"
    echo "  simplr.sh can only be run with SimpL as the working directory"
    exit 1
fi
if [ $# -ne 1 ]; then
    echo "**Error processing input for simplr.sh**"
    echo "  Invalid number of arguments"
    echo "  Run as $ ./simplr.sh <source_file>"
    exit 1
fi
if [[ $(get_extension ${class_file}) != .class ]] || [[ ! -f ${class_file} ]]; then
    echo "**Error processing input for simplr.sh**"
    echo "  Invalid filepath: ${class_file}"
    echo "  Filepath be an existing .class file"
    exit 1
fi

# run compiled simpl file with its parent directory in classpath
program_name=$(basename ${class_file} .class)
java -cp "$(dirname ${class_file})" ${program_name} ${program_name}
