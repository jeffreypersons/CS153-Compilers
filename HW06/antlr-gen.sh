#!/usr/bin/env bash
# generate antlr sources
java -jar lib/antlr-4.7-complete.jar \
      src/SimpL.g4      \
     -long-messages     \
     -encoding utf-8    \
     -listener -visitor \
     -package gen       \
     -o src/gen         \
