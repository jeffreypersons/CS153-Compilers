// Generated from /Users/KishikawaItaru/GitHub/CS153-Assignments/HW06/src/SimpL.g4 by ANTLR 4.7
package gen;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SimpLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SimpLVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link SimpLParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(SimpLParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpLParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat(SimpLParser.StatContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpLParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(SimpLParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpLParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(SimpLParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpLParser#while_loop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile_loop(SimpLParser.While_loopContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpLParser#conditional}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditional(SimpLParser.ConditionalContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpLParser#func_def}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunc_def(SimpLParser.Func_defContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpLParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(SimpLParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(SimpLParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpLParser#func_call}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunc_call(SimpLParser.Func_callContext ctx);
}