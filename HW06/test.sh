#!/usr/bin/env bash
# ============================ Test SimpL Code Base ============================
# Test SimpL from sources
# example usage: ./test.sh
#
# This script does the following steps:
# (1) test simpl using all the test files in the tests/dir
# ==============================================================================

# todo: add functionality (look into antlr test rig tool as well...)

# ensure working dir is HW06 and no arguments are given
if [[ $(basename $(pwd)) != HW06 ]]; then
    echo "**Error processing input for build.sh**"
    echo "  build.sh can only be run with HW06 as the working directory"
    exit 1
fi
if [ $# -ne 0 ]; then
    echo "**Error processing input for build.sh**"
    echo "  Invalid number of arguments"
    echo "  Run as $ ./build.sh"
    exit 1
fi

printf "\n--------------- Running tests/basic_compile1.simpl ----------------\n"
rm -rf tests/basic_compile1.j; rm -rf tests/basic_compile1.class
./simpl.sh tests/basic_compile1.simpl

printf "\n--------------- Running tests/basic_compile2.simpl ----------------\n"
rm -rf tests/basic_compile2.j; rm -rf tests/basic_compile2.class
./simpl.sh tests/basic_compile2.simpl

printf "\n"
