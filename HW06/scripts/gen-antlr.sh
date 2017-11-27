#!/usr/bin/env bash
# gen-antlr: generate Java source files (outputs to src/gen)

if [[ `basename "$PWD"` != HW06 ]]; then
  echo "  `basename "$0"` must be executed from working directory HW06"
  exit 1
fi
if [ $# -ne 0 ]; then
  echo "  **Improper number of arguments**"
  echo "  Usage: ./gen-antlr.sh"
  exit 1
fi
export CLASSPATH="../lib/antlr-4.7-complete.jar:../src/:../src/main:../src/gen/:$CLASSPATH"
java -jar lib/antlr-4.7-complete.jar src/SimpL.g4 -o . -package gen -long-messages -listener -visitor -encoding utf-8
