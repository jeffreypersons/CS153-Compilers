#!/usr/bin/env bash
# ============================= Execute SimpL File =============================
# Run SimpL from sources
# Example usage: ./simpL.sh tests/basic_compile.simpl
#
# Requirements:
# (1) HW06 is currently the only supported working directory
# (2) Currently only a single file can be compiled/run at a time
# (3) File must end with single .simpl extension
# (4) No relative path dot prefixes are currently supported (except for ./)
#
# This script does the following steps:
# (1) Produce jasmin file from simpl file
# (2) Produce class file from jasmin file
# (3) Run class file
# ==============================================================================

# todo: split script into separate compile run scripts (eg simplc.sh and simpl.sh)

# get raw path, path without extensions or leading ./, all file extensions
rawpath=${1}
basepath=${rawpath#./}; basepath=${basepath%%.*}
filetype="."$(echo ${rawpath} | cut -d '.' -f2-)

# ensure working dir is HW06, and given argument is an existing simpl filepath
if [[ $(basename $(pwd)) != HW06 ]]; then
    echo "**Error processing input file for simpl.sh**"
    echo "  Script can only be run with HW06 as the working directory"
    exit 1
fi
if [ $# -ne 1 ]; then
    echo "**Error processing input for simpl.sh**"
    echo "  Invalid number of arguments"
    echo "  Use as ./simpl.sh <source_filepath>.simpl"
    exit 1
fi
if [[ $1 == ..*/ ]]; then
    echo "**Error processing input for simpl.sh**"
    echo "  Invalid prefix for filepath $rawpath"
    echo "  Only ./ is the only currently supported relative dot prefix"
    exit 1
fi
if [[ ${filetype} != .simpl ]]; then
    echo "**Error processing input for simpl.sh**"
    echo "  Invalid file extension for file $rawpath"
    echo "  Only .simpl files are supported"
    exit 1
fi

basefile=$(basename ${basepath})
# ------ produce jasmin file from simpl file
java -ea -cp "out:lib/antlr-4.7-complete.jar" SimpLMain "$basepath.simpl"
if [ $? == 0 ]; then
    echo "Successfully produced file $basefile.j"
else
    echo "**Error generating file $basefile.j**"
    exit $?
fi

# ------ produce class file from jasmin file
java -jar lib/jasmin-2.4-complete.jar "$basefile.j"
if [ $? == 0 ]; then
    echo "Successfully produced file $basefile.class"
else
    echo "**Error generating file $basefile.class**"
    exit $?
fi

# ------ run class file
java "$basepath.class"
if [ $? == 0 ]; then
    echo "Successfully ran file $basefile.class"
else
    echo "**Error running file $basefile.class**"
    exit $?
fi
