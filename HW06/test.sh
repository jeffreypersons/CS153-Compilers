#!/usr/bin/env bash
# ============================ Test SimpL Code Base ============================
# Test SimpL from sources
# example usage: ./test.sh
#
# This script does the following steps:
# (1) test simpl using all the test files in the tests/dir
# ==============================================================================

# todo: add more test cases (possibly using the antlr test rig tool)

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

echo "Testing simpl: tests/basic_compile1.class"
rm -rf tests/basic_compile1.j tests/basic_compile1.class
./simpl.sh tests/basic_compile1.simpl

echo "Testing simp2: tests/basic_compile2.class"
rm -rf tests/basic_compile2.j tests/basic_compile2.class
./simpl.sh tests/basic_compile2.simpl
