import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.antlr.v4.runtime.CommonToken;
public class sVisitor extends simpLBaseVisitor<TerminalNode>
{
	java.util.Map<String, Integer> memory = new java.util.HashMap<String, Integer>();
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public TerminalNode visitProgram(simpLParser.ProgramContext ctx) { return super.visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public TerminalNode visitStmt(simpLParser.StmtContext ctx)
	{ 
		return super.visitChildren(ctx);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public TerminalNode visitDeclaration(simpLParser.DeclarationContext ctx) { return super.visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public TerminalNode visitAssignment(simpLParser.AssignmentContext ctx) { return super.visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public TerminalNode visitIf_stmt(simpLParser.If_stmtContext ctx) { return super.visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public TerminalNode visitFunc_def(simpLParser.Func_defContext ctx) { return super.visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public TerminalNode visitBlock(simpLParser.BlockContext ctx) { return super.visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public TerminalNode visitExpr(simpLParser.ExprContext ctx)
	{
		// Terribly written, we should come back and review this. Just trying to get some working code in
		// would be easy to change grammar to encompass symbosl by category. e.g. POW, NUL, DIV .. belong to arithemtic_operators
		
		// Arithmetic operators
		if(ctx.POW() != null)
		{
			double loperand = new Double(visit(ctx.expr(0)).getSymbol().getText());
			double roperand = new Double(visit(ctx.expr(1)).getSymbol().getText());
			double result = java.lang.Math.pow(loperand, roperand);
			System.out.println(loperand + " ^ " + roperand + " = " + result);
			return new TerminalNodeImpl(new CommonToken(simpLParser.NUMBER, Double.toString(result)));
		}
		else if(ctx.MUL() != null)
		{
			double loperand = new Double(visit(ctx.expr(0)).getSymbol().getText());
			double roperand = new Double(visit(ctx.expr(1)).getSymbol().getText());
			double result = loperand * roperand;
			System.out.println(loperand + " * " + roperand + " = " + result);
			return new TerminalNodeImpl(new CommonToken(simpLParser.NUMBER, Double.toString(result)));
		}
		else if(ctx.DIV() != null)
		{
			double loperand = new Double(visit(ctx.expr(0)).getSymbol().getText());
			double roperand = new Double(visit(ctx.expr(1)).getSymbol().getText());
			double result = loperand / roperand;
			System.out.println(loperand + " / " + roperand + " = " + result);
			return new TerminalNodeImpl(new CommonToken(simpLParser.NUMBER, Double.toString(result)));
		}
		else if(ctx.ADD() != null)
		{
			double loperand = new Double(visit(ctx.expr(0)).getSymbol().getText());
			double roperand = new Double(visit(ctx.expr(1)).getSymbol().getText());
			double result = loperand + roperand;
			System.out.println(loperand + " + " + roperand + " = " + result);
			return new TerminalNodeImpl(new CommonToken(simpLParser.NUMBER, Double.toString(result)));
		}
		else if(ctx.SUB() != null)
		{
			double loperand = new Double(visit(ctx.expr(0)).getSymbol().getText());
			double roperand = new Double(visit(ctx.expr(1)).getSymbol().getText());
			double result = loperand - roperand;
			System.out.println(loperand + " - " + roperand + " = " + result);
			return new TerminalNodeImpl(new CommonToken(simpLParser.NUMBER, Double.toString(result)));		}
		// Boolean operators
		else if(ctx.NOT() != null)
		{
			boolean loperand = new Boolean(visit(ctx.expr(0)).getSymbol().getText());
			boolean result = !(loperand);
			System.out.println("!" + loperand + "=" + result);
			return new TerminalNodeImpl(new CommonToken(simpLParser.NUMBER, Boolean.toString(result)));
		}
		else if(ctx.AND() != null)
		{
			boolean loperand = new Boolean(visit(ctx.expr(0)).getSymbol().getText());
			boolean roperand = new Boolean(visit(ctx.expr(1)).getSymbol().getText());
			boolean result = loperand && roperand;
			System.out.println(loperand + " and " + roperand + "=" + result);
			return new TerminalNodeImpl(new CommonToken(simpLParser.NUMBER, Boolean.toString(result)));
		}
		else if(ctx.OR() != null)
		{
			boolean loperand = new Boolean(visit(ctx.expr(0)).getSymbol().getText());
			boolean roperand = new Boolean(visit(ctx.expr(1)).getSymbol().getText());
			boolean result = loperand || roperand;
			System.out.println(loperand + " or " + roperand + " = " + result);
			return new TerminalNodeImpl(new CommonToken(simpLParser.NUMBER, Boolean.toString(result)));
		}
		else if(ctx.LT() != null)
		{
			double loperand = new Double(visit(ctx.expr(0)).getSymbol().getText());
			double roperand = new Double(visit(ctx.expr(1)).getSymbol().getText());
			boolean result = loperand < roperand;
			System.out.println(loperand + " < " + roperand + " = " + result);
			return new TerminalNodeImpl(new CommonToken(simpLParser.NUMBER, Boolean.toString(result)));
		}
		else if(ctx.GT() != null)
		{
			double loperand = new Double(visit(ctx.expr(0)).getSymbol().getText());
			double roperand = new Double(visit(ctx.expr(1)).getSymbol().getText());
			boolean result = loperand > roperand;
			System.out.println(loperand + " > " + roperand + " = " + result);
			return new TerminalNodeImpl(new CommonToken(simpLParser.NUMBER, Boolean.toString(result)));
		}
		else if(ctx.GTE() != null)
		{
			double loperand = new Double(visit(ctx.expr(0)).getSymbol().getText());
			double roperand = new Double(visit(ctx.expr(1)).getSymbol().getText());
			boolean result = loperand >= roperand;
			System.out.println(loperand + " >= " + roperand + " = " + result);
			return new TerminalNodeImpl(new CommonToken(simpLParser.NUMBER, Boolean.toString(result)));
		}
		else if(ctx.LTE() != null)
		{
			double loperand = new Double(visit(ctx.expr(0)).getSymbol().getText());
			double roperand = new Double(visit(ctx.expr(1)).getSymbol().getText());
			boolean result = loperand <= roperand;
			System.out.println(loperand + " <= " + roperand + " = " + result);
			return new TerminalNodeImpl(new CommonToken(simpLParser.NUMBER, Boolean.toString(result)));
		}
		else if(ctx.EQ() != null)
		{
			double loperand = new Double(visit(ctx.expr(0)).getSymbol().getText());
			double roperand = new Double(visit(ctx.expr(1)).getSymbol().getText());
			boolean result = loperand == roperand;
			System.out.println(loperand + " == " + roperand + " = " + result);
			return new TerminalNodeImpl(new CommonToken(simpLParser.NUMBER, Boolean.toString(result)));		}
		else if(ctx.NEQ() != null)
		{
			double loperand = new Double(visit(ctx.expr(0)).getSymbol().getText());
			double roperand = new Double(visit(ctx.expr(1)).getSymbol().getText());
			boolean result = loperand != roperand;
			System.out.println(loperand + " != " + roperand + " = " + result);
			return new TerminalNodeImpl(new CommonToken(simpLParser.NUMBER, Boolean.toString(result)));
		}
		else if(ctx.LITERAL() != null)
		{
			return ctx.LITERAL(); 
		}
		else return null; // should actually throw error here
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public TerminalNode visitFunc_call(simpLParser.Func_callContext ctx) { return super.visitChildren(ctx); }
}