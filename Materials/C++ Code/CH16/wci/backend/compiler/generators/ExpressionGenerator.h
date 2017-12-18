/**
 * <h1>ExpressionGenerator</h1>
 *
 * <p>Generate code for an expression.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#ifndef EXPRESSIONGENERATOR_H_
#define EXPRESSIONGENERATOR_H_

#include <set>
#include "StatementGenerator.h"
#include "../CodeGenerator.h"
#include "../../../intermediate/ICodeNode.h"
#include "../../../intermediate/icodeimpl/ICodeNodeImpl.h"

namespace wci { namespace backend { namespace compiler { namespace generators {

using namespace std;
using namespace wci::intermediate;
using namespace wci::intermediate::icodeimpl;
using namespace wci::backend::compiler;

class ExpressionGenerator : public StatementGenerator
{
public:
    /**
     * Constructor.
     * @param the parent executor.
     */
    ExpressionGenerator(CodeGenerator *parent);

    /**
     * Generate code to evaluate an expression.
     * @throw a string message if an error occurred.
     */
    void generate(ICodeNode *node) throw (string);

protected:
    /**
     * Generate code to load a variable's value.
     * @param variable_node the variable node.
     */
    void generate_load_value(ICodeNode *variable_node);

    /**
     * Generate code to load a variable's address (structured) or
     * value (scalar).
     * @param variable_node the variable node.
     * @return the variable's type.
     */
    TypeSpec *generate_load_variable(ICodeNode* variable_node);

private:
    // Set of arithmetic operator node types.
    static set<ICodeNodeTypeImpl> ARITH_OPS;

    /**
     * Generate code to evaluate a binary operator.
     * @param node the root node of the expression.
     * @param node_type the node type.
     */
    void generate_binary_operator(ICodeNode *node,
                                  ICodeNodeTypeImpl node_type);
};

}}}}  // namespace wci::backend::compiler::generators

#endif /* EXPRESSIONGENERATOR_H_ */
