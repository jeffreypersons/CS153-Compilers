#include <iostream>
#include <string>
#include <map>
#include "EvalVisitor.h"

using namespace std;

antlrcpp::Any EvalVisitor::visitAssign(LabeledExprParser::AssignContext *ctx)
{
    string id = ctx->ID()->getText();
    int value = visit(ctx->expr());
    memory[id] = value;
    return value;
}

antlrcpp::Any EvalVisitor::visitPrintExpr(LabeledExprParser::PrintExprContext *ctx)
{
    int value = visit(ctx->expr());
    cout << value << endl;
    return 0;
}

antlrcpp::Any EvalVisitor::visitInt(LabeledExprParser::IntContext *ctx)
{
    return stoi(ctx->INT()->getText());
}

antlrcpp::Any EvalVisitor::visitId(LabeledExprParser::IdContext *ctx)
{
    string id = ctx->ID()->getText();
    return (memory.find(id) != memory.end())
            ? memory[id] : 0;
}

antlrcpp::Any EvalVisitor::visitMulDiv(LabeledExprParser::MulDivContext *ctx)
{
    int left  = visit(ctx->expr(0));
    int right = visit(ctx->expr(1));
    return (ctx->op->getType() == LabeledExprParser::MUL)
            ? left*right : left/right;
}

antlrcpp::Any EvalVisitor::visitAddSub(LabeledExprParser::AddSubContext *ctx)
{
    int left  = visit(ctx->expr(0));
    int right = visit(ctx->expr(1));
    return (ctx->op->getType() == LabeledExprParser::ADD)
            ? left + right : left - right;
}

antlrcpp::Any EvalVisitor::visitParens(LabeledExprParser::ParensContext *ctx)
{
    return visit(ctx->expr());
}
