// Generated from /Users/KishikawaItaru/GitHub/CS153-Assignments/HW06/src/SimpL.g4 by ANTLR 4.7
package gen;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SimpLParser}.
 */
public interface SimpLListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SimpLParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(SimpLParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpLParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(SimpLParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpLParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStat(SimpLParser.StatContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpLParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStat(SimpLParser.StatContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpLParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(SimpLParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpLParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(SimpLParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpLParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(SimpLParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpLParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(SimpLParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpLParser#while_loop}.
	 * @param ctx the parse tree
	 */
	void enterWhile_loop(SimpLParser.While_loopContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpLParser#while_loop}.
	 * @param ctx the parse tree
	 */
	void exitWhile_loop(SimpLParser.While_loopContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpLParser#conditional}.
	 * @param ctx the parse tree
	 */
	void enterConditional(SimpLParser.ConditionalContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpLParser#conditional}.
	 * @param ctx the parse tree
	 */
	void exitConditional(SimpLParser.ConditionalContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpLParser#func_def}.
	 * @param ctx the parse tree
	 */
	void enterFunc_def(SimpLParser.Func_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpLParser#func_def}.
	 * @param ctx the parse tree
	 */
	void exitFunc_def(SimpLParser.Func_defContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpLParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(SimpLParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpLParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(SimpLParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(SimpLParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(SimpLParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpLParser#func_call}.
	 * @param ctx the parse tree
	 */
	void enterFunc_call(SimpLParser.Func_callContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpLParser#func_call}.
	 * @param ctx the parse tree
	 */
	void exitFunc_call(SimpLParser.Func_callContext ctx);
}