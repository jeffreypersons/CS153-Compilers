#include <iostream>
#include "ExtractInterfaceListener.h"
#include "antlr4-runtime.h"

using namespace std;
using namespace antlrcpp;
using namespace antlr4;

ExtractInterfaceListener::ExtractInterfaceListener(JavaParser *parser)
    : parser(parser)
{
}

ExtractInterfaceListener::~ExtractInterfaceListener()
{
}

void ExtractInterfaceListener::enterClassDeclaration(JavaParser::ClassDeclarationContext *ctx)
{
    cout << "interface I" << ctx->Identifier()->getText() << " {" << endl;
}

void ExtractInterfaceListener::exitClassDeclaration(JavaParser::ClassDeclarationContext *ctx)
{
    cout << "}" << endl;
}

void ExtractInterfaceListener::enterMethodDeclaration(JavaParser::MethodDeclarationContext *ctx)
{
    TokenStream *tokens = parser->getTokenStream();
    string type = (ctx->type() != nullptr)
                        ? tokens->getText(ctx->type())
                        : "void";
    string args = tokens->getText(ctx->formalParameters());
    cout << "\t" << type << " " << ctx->Identifier()->getText() << args << ":" << endl;
}
