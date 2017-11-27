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

# validate parameters
if [[ `basename "$PWD"` != HW06 ]]; then
  echo "  `basename "$0"` must be executed from working directory HW06"
  exit 1
fi
if [ $# -ne 1 ]; then
  echo "  **Improper number of arguments**"
  echo "  Usage: ./gen-antlr.sh <source_file_name>"
  exit 1
fi

# elevate helper script permissions
chmod +x ./scripts/run-main.sh
chmod +x ./scripts/run-jasmin.sh

# todo: change below, it's pretty fragile...
# configure filepaths
filename=`basename "$0"`
simpl_file=${filename}
jasmin_file=${filename}".j"
class_file=${filename}".class"

# produce class file (.sl -> .j -> .class) then run it using java
./scripts/run-main.sh ${simpl_file}
./scripts/run-jasmin.sh ${jasmin_file}
java ${class_file}
./simpl.sh tests/basic_compile.txt
