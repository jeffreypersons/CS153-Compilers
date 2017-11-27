#!/usr/bin/env bash
# build-src: compiles all the SimpL java source files

if [[ `basename "$PWD"` != HW06 ]]; then
  echo "  `basename "$0"` must be executed from working directory HW06"
  exit 1
fi
if [ $# -ne 0 ]; then
  echo "  **Improper number of arguments**"
  echo "  Usage: ./build-src.sh"
  exit 1
fi
export CLASSPATH="/src/:../src/main:../src/gen/:$CLASSPATH"
find ./src -name '*.class' -type f -delete
javac ./src/main/*.java
javac ./src/SimpLMain.java
