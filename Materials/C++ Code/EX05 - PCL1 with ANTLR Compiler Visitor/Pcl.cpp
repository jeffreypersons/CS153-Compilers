#include <iostream>
#include <fstream>

#include "antlr4-runtime.h"
#include "PclLexer.h"
#include "PclParser.h"
#include "CompilerVisitor.h"

using namespace std;
using namespace antlrcpp;
using namespace antlr4;

int main(int argc, const char *args[])
{
    ifstream ins;
    ins.open(args[1]);

    ANTLRInputStream input(ins);
    PclLexer lexer(&input);
    CommonTokenStream tokens(&lexer);

    PclParser parser(&tokens);
    tree::ParseTree *tree = parser.program();

    CompilerVisitor compiler;
    compiler.visit(tree);

    delete tree;
    return 0;
}
