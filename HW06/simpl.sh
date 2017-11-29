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

# validate parameters and configure vars
if [[ `basename "$PWD"` != HW06 ]]; then
  echo "  `basename "$0"` must be executed from working directory 'HW06'"
  exit 1
fi
if [ $# -ne 1 ]; then
  echo "  **Improper number of arguments**"
  echo "  Usage: ./simpl.sh <source_file_name>"
  exit 1
fi
filename=$(basename "$1" | cut -d. -f1)
export CLASSPATH=".:src:src/main:src/gen:lib/jasmin-2.4-complete.jar:lib/antlr-4.7-complete.jar"

# produce class file (.sl -> .j -> .class) then run it using java
java -cp ${CLASSPATH} SimpLMain ${filename}
java -jar lib/jasmin-2.4-complete.jar "$filename.j"
java "$filename.class"
