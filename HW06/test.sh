#!/usr/bin/env bash
# ============================ Test SimpL Code Base ============================
# Test a single SimpL file
# Example usage: ./test.sh
# ==============================================================================

# ensure working dir is HW06 and no arguments are given
if [[ $(basename $(pwd)) != HW06 ]]; then
    echo "**Error processing input for build.sh**"
    echo "  test.sh can only be run with HW06 as the working directory"
    exit 1
fi
if [ $# -ne 1 ]; then
    echo "**Error processing input for test.sh**"
    echo "  Invalid number of arguments"
    echo "  Run as $ ./test.sh <source_file>"
    exit 1
fi

# run SimpL compiler on given test file
# todo: add more test/report functionality using antlr test rig
source_file=${1}
./simpl.sh ${source_file}
