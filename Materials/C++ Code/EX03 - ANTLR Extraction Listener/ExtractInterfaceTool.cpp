#include <iostream>
#include <fstream>

#include "antlr4-runtime.h"
#include "tree/ParseTreeWalker.h"
#include "JavaLexer.h"
#include "JavaParser.h"
#include "ExtractInterfaceListener.h"

using namespace antlrcpp;
using namespace antlr4;
using namespace antlr4::tree;
using namespace std;

int main(int argc, const char *args[])
{
    ifstream ins;
    ins.open(args[1]);

    ANTLRInputStream input(ins);
    JavaLexer lexer(&input);
    CommonTokenStream tokens(&lexer);

    JavaParser *parser = new JavaParser(&tokens);
    tree::ParseTree *tree = parser->compilationUnit();

    ParseTreeWalker walker;
    ExtractInterfaceListener *extractor = new ExtractInterfaceListener(parser);
    walker.walk(extractor, tree);

    return 0;
}
