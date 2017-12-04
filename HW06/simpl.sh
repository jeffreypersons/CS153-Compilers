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

# todo: split script into separate compile run scripts (eg simplc.sh and simpl.sh)

get_extension() { echo "."$(echo $(basename $1) | cut -d '.' -f2-); }

# ensure working dir is HW06, and given argument is an existing simpl filepath
rawpath=${1}
inputdir="$(dirname "${rawpath}")"
name=$(basename ${rawpath} .simpl)
if [[ $(basename $(pwd)) != HW06 ]]; then
    echo "**Error processing input file for simpl.sh**"
    echo "  Script can only be run with HW06 as the working directory"
    exit 1
fi
if [ $# -ne 1 ]; then
    echo "**Error processing input for simpl.sh**"
    echo "  Invalid number of arguments"
    echo "  Run as $ ./simpl.sh <source_filepath>.simpl"
    exit 1
fi
if [ ! -f ${rawpath} ]; then
    echo "**Error processing input for simpl.sh**"
    echo "  Invalid file path $rawpath"
    echo "  File does not exist"
    exit 1
fi
if [[ $(get_extension ${rawpath}) != .simpl ]]; then
    echo "**Error processing input for simpl.sh**"
    echo "  Invalid file extension for file $rawpath"
    echo "  Only .simpl files are supported"
    exit 1
fi

# ------ setup cache folder for simpl/jasmin/class files
source_filepath=${inputdir}/${name}.simpl
jasmin_filepath=${inputdir}/${name}.j
class_filepath=${inputdir}/${name}.class

# ------ produce jasmin file from simpl file
java -cp "out:lib/antlr-4.7-complete.jar" SimpLMain ${inputdir}/${name}.simpl
if [ $? == 0 ]; then
    echo "Successfully produced file $jasmin_filepath"
else
    echo "**Error generating file $jasmin_filepath**"
    exit $?
fi

# ------ produce class file from jasmin file
java -jar lib/jasmin-2.4-complete.jar -g ${inputdir}/${name}.j
if [ $? == 0 ]; then
    echo "Successfully produced file $class_filepath"
else
    echo "**Error generating file $class_filepath**"
    exit $?
fi

# ------ run class file
java -cp "${inputdir}" ${inputdir} ${name}
if [ $? == 0 ]; then
    echo "Successfully ran file $class_filepath"
else
    echo "**Error running file $class_filepath**"
    exit $?
fi
