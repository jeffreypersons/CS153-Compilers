// Generated from simpL.g4 by ANTLR 4.7
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link simpLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface simpLVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link simpLParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(simpLParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link simpLParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmt(simpLParser.StmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link simpLParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(simpLParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link simpLParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(simpLParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link simpLParser#while_loop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile_loop(simpLParser.While_loopContext ctx);
	/**
	 * Visit a parse tree produced by {@link simpLParser#if_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_stmt(simpLParser.If_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link simpLParser#func_def}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunc_def(simpLParser.Func_defContext ctx);
	/**
	 * Visit a parse tree produced by {@link simpLParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(simpLParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link simpLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(simpLParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link simpLParser#func_call}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunc_call(simpLParser.Func_callContext ctx);
}