#
# Makefile for Chapter 4
#
# Copyright (c) 2017 by Ronald Mak
# For instructional purposes only.  No warranties.
#
TARGET = Chapter4cpp
SRCS =  Pascal.cpp \
        wci/backend/Backend.cpp \
        wci/backend/BackendFactory.cpp \
        wci/backend/compiler/CodeGenerator.cpp \
        wci/backend/interpreter/Executor.cpp \
        wci/frontend/FrontendFactory.cpp \
        wci/frontend/Parser.cpp \
        wci/frontend/Scanner.cpp \
        wci/frontend/Source.cpp \
        wci/frontend/Token.cpp \
        wci/frontend/pascal/PascalError.cpp \
        wci/frontend/pascal/PascalErrorHandler.cpp \
        wci/frontend/pascal/PascalParserTD.cpp \
        wci/frontend/pascal/PascalScanner.cpp \
        wci/frontend/pascal/PascalToken.cpp \
        wci/frontend/pascal/tokens/PascalErrorToken.cpp \
        wci/frontend/pascal/tokens/PascalNumberToken.cpp \
        wci/frontend/pascal/tokens/PascalSpecialSymbolToken.cpp \
        wci/frontend/pascal/tokens/PascalStringToken.cpp \
        wci/frontend/pascal/tokens/PascalWordToken.cpp \
        wci/intermediate/SymTabFactory.cpp \
        wci/intermediate/symtabimpl/SymTabEntryImpl.cpp \
        wci/intermediate/symtabimpl/SymTabImpl.cpp \
        wci/intermediate/symtabimpl/SymTabStackImpl.cpp \
        wci/message/Message.cpp \
        wci/message/MessageHandler.cpp \
        wci/util/CrossReferencer.cpp
OBJS = $(SRCS:.cpp=.o)
CC = g++
CFLAGS = -std=c++0x -O0 -g3 -Wall
PFLAGS = -x
DEP = depends
RAW_DEPENDENCY_FILE = .temp_dependencies.txt
DEPENDENCY_FILE = makefile_dependencies.txt

.cpp.o :
	$(CC) $(CFLAGS) -c -o $@ $<

all: $(TARGET)

clean:
	rm $(OBJS) $(TARGET) $(RAW_DEPENDENCY_FILE)

$(TARGET) : $(OBJS)
	$(CC) -o $(TARGET) $(OBJS)

# Compile Pascal program hello.pas: make compile src=hello.pas
compile: $(TARGET)
	./$(TARGET) compile $(PFLAGS) $(src)

# Execute a Pascal program hello.pas: make execute src=hello.pas
execute: $(TARGET)
	./$(TARGET) execute $(PFLAGS) $(src)

# Generate dependencies on the header files.
# You shouldn't run this.
dependencies:
	$(CC) -MM $(SRCS) > $(RAW_DEPENDENCY_FILE)
	$(DEP) < $(RAW_DEPENDENCY_FILE) > $(DEPENDENCY_FILE)
	rm $(RAW_DEPENDENCY_FILE)

sinclude $(DEPENDENCY_FILE)
