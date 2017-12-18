#include <iostream>
#include <fstream>

#include "antlr4-runtime.h"
#include "LabeledExprLexer.h"
#include "LabeledExprParser.h"
#include "EvalVisitor.h"

using namespace std;
using namespace antlrcpp;
using namespace antlr4;

int main(int argc, const char *args[])
{
    ifstream ins;
    ins.open(args[1]);

    ANTLRInputStream input(ins);
    LabeledExprLexer lexer(&input);
    CommonTokenStream tokens(&lexer);

    LabeledExprParser parser(&tokens);
    tree::ParseTree *tree = parser.prog();

    cout << "Parse tree:" << endl;
    cout << tree->toStringTree(&parser) << endl;

    cout << endl << "Evaluation:" << endl;
    EvalVisitor eval;
    eval.visit(tree);

    delete tree;
    return 0;
}
