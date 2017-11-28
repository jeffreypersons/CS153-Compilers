#!/usr/bin/env bash
# run-main: generate jasmin file from SimpL file

if [[ `basename "$PWD"` != scripts ]]; then
  echo "  `basename "$0"` must be executed from working directory 'scripts'"
  exit 1
fi
if [ $# -ne 1 ]; then
  echo "  **Improper number of arguments**"
  echo "  Usage: ./run-main.sh <source_file_name>"
  exit 1
fi
source_file_name=$1
export CLASSPATH="../lib/jasmin-2.4-complete.jar:../src/:../src/main:../src/gen/:$CLASSPATH"
java SimpLMain ${source_file_name}
