#
# Makefile for Chapter 3
#
# Copyright (c) 2017 by Ronald Mak
# For instructional purposes only.  No warranties.
#
TARGET = Chapter3cpp
SRCS = Java.cpp \
       wci/frontend/FrontendFactory.cpp \
       wci/frontend/Parser.cpp \
       wci/frontend/Scanner.cpp \
       wci/frontend/Token.cpp \
       wci/frontend/Source.cpp \
       wci/frontend/pascal/PascalParserTD.cpp \
       wci/frontend/pascal/PascalScanner.cpp \
       wci/frontend/pascal/PascalError.cpp \
       wci/frontend/pascal/PascalErrorHandler.cpp \
       wci/frontend/pascal/PascalToken.cpp \
       wci/frontend/pascal/tokens/PascalErrorToken.cpp \
       wci/frontend/pascal/tokens/PascalNumberToken.cpp \
       wci/frontend/pascal/tokens/PascalSpecialSymbolToken.cpp \
       wci/frontend/pascal/tokens/PascalStringToken.cpp \
       wci/frontend/pascal/tokens/PascalWordToken.cpp \
       wci/backend/BackendFactory.cpp \
       wci/backend/Backend.cpp \
       wci/backend/interpreter/Executor.cpp \
       wci/backend/compiler/CodeGenerator.cpp \
       wci/message/Message.cpp \
       wci/message/MessageHandler.cpp \
       wci/frontend/java/JavaError.cpp \
       wci/frontend/java/JavaScanner.cpp \
       wci/frontend/java/JavaParserTD.cpp \
       wci/frontend/java/JavaErrorHandler.cpp \
       wci/frontend/java/JavaToken.cpp \
       wci/frontend/java/tokens/JavaErrorToken.cpp \
       wci/frontend/java/tokens/JavaCharToken.cpp \
       wci/frontend/java/tokens/JavaNumberToken.cpp \
       wci/frontend/java/tokens/JavaSpecialSymbolToken.cpp \
       wci/frontend/java/tokens/JavaStringToken.cpp \
       wci/frontend/java/tokens/JavaWordToken.cpp \

OBJS = $(SRCS:.cpp=.o)
SOBJS = $(JSRCS:.cpp=.o)
CC = g++
CFLAGS = -std=c++0x -O0 -g3 -Wall
DEP = depends
RAW_DEPENDENCY_FILE = .temp_dependencies.txt
DEPENDENCY_FILE = 

.cpp.o :
	$(CC) $(CFLAGS) -c -o $@ $<

all: $(TARGET)

clean:
	\rm $(OBJS) $(TARGET) $(RAW_DEPENDENCY_FILE)

$(TARGET) : $(OBJS)
	$(CC) -o $(TARGET) $(OBJS)

# Compile Pascal program hello.pas: make compile src=hello.pas
compile: $(TARGET)
	./$(TARGET) compile $(src)

# Execute a Pascal program hello.pas: make execute src=hello.pas
execute: $(TARGET)
	./$(TARGET) execute $(src)

# Generate dependencies on the header files.
# You shouldn't run this.
dependencies:
	$(CC) -MM $(SRCS) > $(RAW_DEPENDENCY_FILE)
	$(DEP) < $(RAW_DEPENDENCY_FILE) > $(DEPENDENCY_FILE)
	\rm $(RAW_DEPENDENCY_FILE)

sinclude $(DEPENDENCY_FILE)
