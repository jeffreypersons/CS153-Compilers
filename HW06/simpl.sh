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
cwd=`realpath "$PWD"`;lib="$0/lib"
export CLASSPATH=".:$cwd/:$cwd/src/:$cwd/main/:$cwd/gen/:$lib/antlr-4.7-complete.jar:$lib/jasmin-2.4-complete.jar"


# produce class file (.sl -> .j -> .class) then run it using java
java -cp src SimpLMain ${filename}
java -jar ${cwd}/lib/jasmin-2.4-complete.jar "$filename.j"
java      "$filename.class"
