#ifndef EVALVISITOR_H_
#define EVALVISITOR_H_

#include <iostream>
#include <string>
#include <map>
#include "antlr4-runtime.h"
#include "LabeledExprBaseVisitor.h"

using namespace std;

class EvalVisitor : public LabeledExprBaseVisitor
{
public:
    antlrcpp::Any visitAssign(LabeledExprParser::AssignContext *ctx) override;
    antlrcpp::Any visitPrintExpr(LabeledExprParser::PrintExprContext *ctx) override;
    antlrcpp::Any visitInt(LabeledExprParser::IntContext *ctx) override;
    antlrcpp::Any visitId(LabeledExprParser::IdContext *ctx) override;
    antlrcpp::Any visitMulDiv(LabeledExprParser::MulDivContext *ctx) override;
    antlrcpp::Any visitAddSub(LabeledExprParser::AddSubContext *ctx) override;
    antlrcpp::Any visitParens(LabeledExprParser::ParensContext *ctx) override;

private:
    map<string, int> memory;
};

#endif /* EVALVISITOR_H_ */
