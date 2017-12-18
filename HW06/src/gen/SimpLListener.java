// Generated from simpL.g4 by ANTLR 4.7
package gen;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link simpLParser}.
 */
public interface simpLListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link simpLParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(simpLParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link simpLParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(simpLParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link simpLParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterStmt(simpLParser.StmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link simpLParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitStmt(simpLParser.StmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link simpLParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(simpLParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link simpLParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(simpLParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link simpLParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(simpLParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link simpLParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(simpLParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link simpLParser#while_loop}.
	 * @param ctx the parse tree
	 */
	void enterWhile_loop(simpLParser.While_loopContext ctx);
	/**
	 * Exit a parse tree produced by {@link simpLParser#while_loop}.
	 * @param ctx the parse tree
	 */
	void exitWhile_loop(simpLParser.While_loopContext ctx);
	/**
	 * Enter a parse tree produced by {@link simpLParser#if_stmt}.
	 * @param ctx the parse tree
	 */
	void enterIf_stmt(simpLParser.If_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link simpLParser#if_stmt}.
	 * @param ctx the parse tree
	 */
	void exitIf_stmt(simpLParser.If_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link simpLParser#func_def}.
	 * @param ctx the parse tree
	 */
	void enterFunc_def(simpLParser.Func_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link simpLParser#func_def}.
	 * @param ctx the parse tree
	 */
	void exitFunc_def(simpLParser.Func_defContext ctx);
	/**
	 * Enter a parse tree produced by {@link simpLParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(simpLParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link simpLParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(simpLParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link simpLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(simpLParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link simpLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(simpLParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link simpLParser#func_call}.
	 * @param ctx the parse tree
	 */
	void enterFunc_call(simpLParser.Func_callContext ctx);
	/**
	 * Exit a parse tree produced by {@link simpLParser#func_call}.
	 * @param ctx the parse tree
	 */
	void exitFunc_call(simpLParser.Func_callContext ctx);
}