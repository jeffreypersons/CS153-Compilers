#ifndef EXTRACTINTERFACELISTENER_H_
#define EXTRACTINTERFACELISTENER_H_

#include "JavaBaseListener.h"
#include "JavaParser.h"

class ExtractInterfaceListener : public JavaBaseListener
{
public:
    ExtractInterfaceListener(JavaParser *parser);
    virtual ~ExtractInterfaceListener();

    void enterClassDeclaration(JavaParser::ClassDeclarationContext *ctx) override;
    void exitClassDeclaration(JavaParser::ClassDeclarationContext *ctx) override;
    void enterMethodDeclaration(JavaParser::MethodDeclarationContext *ctx) override;

private:
    JavaParser *parser;
};

#endif /* EXTRACTINTERFACELISTENER_H_ */
