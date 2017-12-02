#!/usr/bin/env bash
# ============================= Execute SimpL File =============================
# Run SimpL from sources
# example usage: ./simpL.sh tests/basic_compile.txt
#
# This script does the following steps:
# (1) produce jasmin file from simpl file
# (2) produce class file from jasmin file
# (3) run class file
# ==============================================================================
# emulate realpath (not on all os) and then validate input
realpath() { [[ $1 = /* ]] && echo $1 || echo "$(pwd)/${1#./}" | sed 's/\/*$//g'; }
if [[ $(basename $(pwd)) != HW06 ]]; then
    echo "  simpl.sh must be executed from HW06 as the working directory"
    exit 1
fi
if [ $# -ne 1 ]; then
    echo "  **Invalid number of arguments**"
    echo "  Usage: ./simpl.sh <source_file_name>"
    exit 1
fi
filename=$(basename $1 | cut -d'.' -f-1)
filetype=`$(echo $1 | cut -d'.' -f2-)`
if [[ ${filetype} == simpl ]]; then
    echo $(basename $1)
    echo "  Only .simpl files can be executed as SimpL code"
    exit 1
fi

# create .simpl -> .j -> .class file and then run using Java
output="$filename"
echo "Generating $filename.simpl"
java -cp "out:lib/antlr-4.7-complete.jar" SimpLMain "$filename.simpl"

echo "Generating $filename.j"
java -jar lib/jasmin-2.4-complete.jar "$filename.j"

echo "Running $filename.class"
java -cp "__simplcache" "$filename.class"
