#ifndef COMPILERVISITOR_H_
#define COMPILERVISITOR_H_

#include "wci/intermediate/SymTabStack.h"
#include "wci/intermediate/SymTabEntry.h"
#include "wci/intermediate/TypeSpec.h"

#include "PclBaseVisitor.h"
#include "antlr4-runtime.h"
#include "PclVisitor.h"

using namespace wci;
using namespace wci::intermediate;

class CompilerVisitor : public PclBaseVisitor
{
private:
    SymTabStack *symtab_stack;
    SymTabEntry *program_id;
    vector<SymTabEntry *> variable_id_list;
    TypeSpec *data_type;

public:
    CompilerVisitor();
    virtual ~CompilerVisitor();

    antlrcpp::Any visitProgram(PclParser::ProgramContext *ctx) override;
    antlrcpp::Any visitHeader(PclParser::HeaderContext *ctx) override;
    antlrcpp::Any visitDecl(PclParser::DeclContext *ctx) override;
    antlrcpp::Any visitVar_list(PclParser::Var_listContext *ctx) override;
    antlrcpp::Any visitVar_id(PclParser::Var_idContext *ctx) override;
    antlrcpp::Any visitType_id(PclParser::Type_idContext *ctx) override;
};

#endif /* COMPILERVISITOR_H_ */
