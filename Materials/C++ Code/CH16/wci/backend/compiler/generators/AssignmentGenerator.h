/**
 * <h1>AssignmentGenerator</h1>
 *
 * <p>Generate code for an assignment statement.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#ifndef ASSIGNMENTGENERATOR_H_
#define ASSIGNMENTGENERATOR_H_

#include <string>
#include "StatementGenerator.h"
#include "ExpressionGenerator.h"
#include "../../../intermediate/SymTabEntry.h"
#include "../../../intermediate/ICodeNode.h"

namespace wci { namespace backend { namespace compiler { namespace generators {

using namespace std;
using namespace wci::intermediate;
using namespace wci::backend::compiler;

class AssignmentGenerator : public StatementGenerator
{
public:
    /**
     * Constructor.
     * @param the parent executor.
     */
    AssignmentGenerator(CodeGenerator *parent);

    /**
     * Generate code for a statement.
     * To be overridden by the specialized statement executor subclasses.
     * @param node the root node of the statement.
     * @throw a string message if an error occurred.
     */
    void generate(ICodeNode *node) throw (string);

private:
    /**
     * Generate code to assign a scalar value.
     * @param target_typespec the data type of the target.
     * @param target_id the symbol table entry of the target variable.
     * @param index the index of the target variable.
     * @param nesting_level the nesting level of the target variable.
     * @param expr_node the expression tree node.
     * @param expr_typespec the expression data type.
     * @param exprGenerator the expression generator.
     */
    void generate_scalar_assignment(TypeSpec *target_typespec,
                                    SymTabEntry *target_id,
                                    int index,
                                    int nesting_level,
                                    ICodeNode *expr_node,
                                    TypeSpec *expr_typespec,
                                    ExpressionGenerator &expr_generator);
};

}}}}  // namespace wci::backend::compiler::generators

#endif /* ASSIGNMENTGENERATOR_H_ */
