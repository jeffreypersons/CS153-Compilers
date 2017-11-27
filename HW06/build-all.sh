#!/usr/bin/env bash
# ========================= Generate and Build SimpL Sources ===================
# Build SimpL from sources
# Example usage: ./build-all.sh
#
# This script does the following steps:
# (1) generates Java source code for listener, visitor, parser, and tokens
#     using antlr4
# (2) compiles all of the source directory (generates .class files adjacent to
#     .java file)
# ==============================================================================

# validate parameters
if [[ `basename "$PWD"` != HW06 ]]; then
  echo "  `basename "$0"` must be executed from working directory HW06"
  exit 1
fi
if [ $# -ne 0 ]; then
  echo "  **Improper number of arguments**"
  echo "  Usage: ./build-all.sh"
  exit 1
fi

# elevate helper script permissions
chmod +x ./scripts/gen-antlr.sh
chmod +x ./scripts/build-src.sh

# generate antlr sources, and them compile everything in src dir
./scripts/gen-antlr.sh
./scripts/build-src.sh
