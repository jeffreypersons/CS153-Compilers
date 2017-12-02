#!/usr/bin/env bash
# ============================ Test SimpL Code Base ============================
# Test SimpL from sources
# example usage: ./simpL.sh tests/basic_compile.txt
#
# This script does the following steps:
# (1) test simpl using all the test files in the tests/dir
# ==============================================================================

# todo: add functionality (look into antlr test rig tool as well...)
printf '\n------------ Running tests/basic_compile1.simpl ------------\n'
./simpl.sh tests/basic_compile1.simpl

printf "\n------------ Running tests/basic_compile2.simpl ------------\n"
./simpl.sh tests/basic_compile2.simpl

printf '\n'
