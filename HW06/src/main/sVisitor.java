import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;
public class sVisitor extends simpLBaseVisitor<TerminalNode>
{
	public java.util.Map<String, Value> memory = new java.util.HashMap<String, Value>();
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
	@Override public TerminalNode visitDeclaration(simpLParser.DeclarationContext ctx)
	{
		// No check typing - need to add
		String name = ctx.NAME().toString();
		if(!ctx.TYPE().getSymbol().getText().equals("Number"))
		{
			System.out.println("INVALID DECLARATION - ONLY SUPPORT OF NUMBER IMPLEMENTED");
		}
		if(ctx.ASSIGN() != null)
		{
			Value val = getOperandValue(visit(ctx.expr()).getSymbol());
			memory.put(name, val);
			System.out.println("creating " + name + " and setting to " + val.getValue());
		}
		else memory.put(name, null);
		return super.visitChildren(ctx);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public TerminalNode visitAssignment(simpLParser.AssignmentContext ctx)
	{ 
		// check if it exists in the memory map
		String identifier = ctx.NAME().getSymbol().getText();
		Value val = getOperandValue(visit(ctx.expr()).getSymbol());
		if (memory.get(identifier) == null)
		{
			// throw error, assignment on undeclared identifier
		}
		memory.put(identifier, val);
		System.out.println("Setting " + identifier + " to " + val.getValue());
		return super.visitChildren(ctx); 
	}
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
		if(ctx.LITERAL() != null)
		{
			return ctx.LITERAL(); 
		}
		else if(ctx.NAME() != null)
		{
			return ctx.NAME();
		}

		Value loperand, roperand;
		loperand = getOperandValue(visit(ctx.expr(0)).getSymbol());
		roperand = getOperandValue(visit(ctx.expr(1)).getSymbol());

		if(ctx.POW() != null)
		{
			double lvalue, rvalue = 0;
			lvalue = (double)loperand.getValue();
			rvalue = (double)roperand.getValue();
			double result = java.lang.Math.pow(lvalue,  rvalue);
			System.out.println(lvalue + " ^ " + rvalue + " = " + result);
			return new TerminalNodeImpl(new CommonToken(simpLParser.NUMBER, Double.toString(result)));
		}
		else if(ctx.MUL() != null)
		{
			double lvalue, rvalue = 0;
			lvalue = (double)loperand.getValue();
			rvalue = (double)roperand.getValue();
			double result = lvalue * rvalue;
			System.out.println(lvalue + " * " + rvalue + " = " + result);
			return new TerminalNodeImpl(new CommonToken(simpLParser.NUMBER, Double.toString(result)));
		}
		else if(ctx.DIV() != null)
		{
			double lvalue, rvalue = 0;
			lvalue = (double)loperand.getValue();
			rvalue = (double)roperand.getValue();
			double result = lvalue / rvalue;
			System.out.println(lvalue + " / " + rvalue + " = " + result);
			return new TerminalNodeImpl(new CommonToken(simpLParser.NUMBER, Double.toString(result)));
		}
		else if(ctx.ADD() != null)
		{
			double lvalue, rvalue = 0;
			lvalue = (double)loperand.getValue();
			rvalue = (double)roperand.getValue();
			double result = lvalue + rvalue;
			System.out.println(lvalue + " + " + rvalue + " = " + result);
			return new TerminalNodeImpl(new CommonToken(simpLParser.NUMBER, Double.toString(result)));
		}
		else if(ctx.SUB() != null)
		{
			double lvalue, rvalue = 0;
			lvalue = (double)loperand.getValue();
			rvalue = (double)roperand.getValue();
			double result = lvalue - rvalue;
			System.out.println(lvalue + " - " + rvalue + " = " + result);
			return new TerminalNodeImpl(new CommonToken(simpLParser.NUMBER, Double.toString(result)));		
		}
		// Boolean operators
		else if(ctx.NOT() != null)
		{
			return null;
		}
		else if(ctx.AND() != null)
		{
			return null;
		}
		else if(ctx.OR() != null)
		{
			return null;
		}
		else if(ctx.LT() != null)
		{
			return null;
		}
		else if(ctx.GT() != null)
		{
			return null;

		}
		else if(ctx.GTE() != null)
		{
			return null;

		}
		else if(ctx.LTE() != null)
		{
			return null;

		}
		else if(ctx.EQ() != null)
		{
			return null;
	
		}
		else if(ctx.NEQ() != null)
		{
			return null;

		}
		else return null; // should actually throw error here
	}

	private Value getOperandValue(Token a)
	{
		Value val = ValueBuilder.getValue(a, memory);
		if(val.getType().compareTo("IDENTIFIER") == 0)
		{
			val = (Value) val.getValue();
		}
		return val;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public TerminalNode visitFunc_call(simpLParser.Func_callContext ctx) { return super.visitChildren(ctx); }
}