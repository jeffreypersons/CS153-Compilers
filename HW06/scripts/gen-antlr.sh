#!/usr/bin/env bash
# gen-antlr: generate Java source files (outputs to src/gen)

if [[ `basename "$PWD"` != scripts ]]; then
  echo "  `basename "$0"` must be executed from working directory 'scripts'"
  exit 1
fi
if [ $# -ne 0 ]; then
  echo "  **Improper number of arguments**"
  echo "  Usage: ./gen-antlr.sh"
  exit 1
fi
export CLASSPATH="../src/SimpL.g4:../lib/antlr-4.7-complete.jar:../src/:../src/main:../src/gen/:$CLASSPATH"
java -jar lib/antlr-4.7-complete.jar -long-messages -listener -visitor -encoding utf-8 -lib src -o src/gen -package gen src/SimpL.g4

java -jar ../lib/antlr-4.7-complete.jar \
      ../src/SimpL.g4   \
     -long-messages     \
     -encoding utf-8    \
     -listener -visitor \
     -lib ../src        \
     -package gen       \
     -o ../src/gen      \
