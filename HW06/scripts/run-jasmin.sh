#!/usr/bin/env bash
# run-jasmin: produces class file from given jasmin assembly file

if [[ `basename "$PWD"` != scripts ]]; then
  echo "  `basename "$0"` must be executed from working directory 'scripts'"
  exit 1
fi
if [ $# -ne 1 ]; then
  echo "  **Improper number of arguments**"
  echo "  Usage: . ./run-jasmin.sh <jasmin_file>"
  exit 1
fi
jasmin_filename=$1
export CLASSPATH="../lib/jasmin-2.4-complete.jar:$CLASSPATH"
java -jar ../lib/jasmin-2.4-complete.jar ${jasmin_filename}
