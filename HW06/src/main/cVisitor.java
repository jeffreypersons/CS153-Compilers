import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;
import java.util.ArrayList;
public class cVisitor extends simpLBaseVisitor<TerminalNode>
{
	private static int stack_size = 0;
	private static int necessary_stack_size = 0;
	private static int locals = 1;
	private static ArrayList<String> text;
	private java.util.Map<String, Value> memory = new java.util.HashMap<String, Value>();

	public ArrayList<String> getText()
	{
		return text;
	}

	public int getStackSize()
	{
		return this.necessary_stack_size;
	}

	public java.util.Map<String, Value> getMemory()
	{
		return this.memory;
	}

	private void incStackSize()
	{
		stack_size++;
		if(stack_size > necessary_stack_size) necessary_stack_size = stack_size;
	}

	private void IncLocals()
	{
		locals++;
	}

	private void DecLocals()
	{
		locals--;
	}

	private void decStackSize()
	{
		if(stack_size > 0) stack_size--; 
	}

	private void incStackSize(long a)
	{
		stack_size += a;
		if(stack_size > necessary_stack_size) necessary_stack_size = stack_size;
	}

	private void decStackSize(long a)
	{
		if(a >= stack_size) stack_size = 0;
		else stack_size -= a;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public TerminalNode visitProgram(simpLParser.ProgramContext ctx)
	{
		text = new ArrayList<String>();
		text.add(CodeEmitter.GetLibraryCode("math"));
		text.add(CodeEmitter.Main());
		int stack_size_line = 0;
		stack_size_line = text.size();
		text.add(CodeEmitter.SetStack(stack_size) + CodeEmitter.SetLocals(locals));
		TerminalNode a = super.visitChildren(ctx); 
		text.set(stack_size_line, CodeEmitter.SetStack((stack_size + locals) * 2) + CodeEmitter.SetLocals(locals)); // since everything is stored as float, multiply by 2 I think
		return a;
	}
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
		Value val = null;
		if(!ctx.TYPE().getSymbol().getText().equals("Number"))
		{
			System.out.println("INVALID DECLARATION - ONLY SUPPORT OF NUMBER IMPLEMENTED");
		}
		if(ctx.ASSIGN() != null)
		{
			val = getOperandValue(visit(ctx.expr()).getSymbol());
			Variable var = new Variable(name, val, "NUMBER");
			text.add(CodeEmitter.DeclareVariable(var, locals));
			memory.put(name, var);
			//System.out.println("creating " + name + " and setting to " + val.getValue());
		}
		else memory.put(name, null);
		IncLocals();
		if(val != null) return new TerminalNodeImpl(new CommonToken(simpLParser.NUMBER, "0"));
		else return new TerminalNodeImpl(new CommonToken(simpLParser.NUMBER, Double.toString((double)val.getValue())));
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
		// TODO: check validity based on variable cast using .getCast() method
		String identifier = ctx.NAME().getSymbol().getText();
		Value val = getOperandValue(visit(ctx.expr()).getSymbol());
		incStackSize(2);
		if (memory.get(identifier) == null)
		{
			// throw error, assignment on undeclared identifier
			//throw new Exception("UNDELCARED IDENTIFIER");
		}
		Variable var = (Variable) memory.get(identifier);
		var.setValue(val);
		memory.put(identifier, var);
		text.add(CodeEmitter.AssignVariable(var));
		decStackSize(2);
		return new TerminalNodeImpl(new CommonToken(simpLParser.NUMBER, Double.toString((double)val.getValue())));
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
		TerminalNodeImpl a = null;
		if(ctx.NAME() != null)
		{
			Value val = memory.get(ctx.NAME().getSymbol().getText().toString());
			double result;
			if(val.getType().equals("IDENTIFIER"))
			{
				Variable var = (Variable) val;
				text.add(CodeEmitter.PutVarStack(var));
				Value operand = var.getValue();
				result = (double)operand.getValue();
			}
			else result = (double) val.getValue();
			return new TerminalNodeImpl(new CommonToken(simpLParser.NUMBER, Double.toString(result)));
		}
		else if(ctx.LITERAL() != null)
		{
			// check if number of text for now assuming number
			Value operand = getOperandValue(ctx.LITERAL().getSymbol());
			double result = (double)operand.getValue();
			text.add(CodeEmitter.LoadConstant(result));
			return new TerminalNodeImpl(new CommonToken(simpLParser.NUMBER, Double.toString(result)));
		}
		else if(ctx.LPAREN() != null)
		{
			return ctx.LPAREN();
		}
		else if(ctx.RPAREN() != null)
		{
			return ctx.RPAREN();
		}
		Value loperand, roperand;
		incStackSize(2);
		try
		{	
			loperand = getOperandValue(visit(ctx.expr(0)).getSymbol());
			roperand = getOperandValue(visit(ctx.expr(1)).getSymbol());
		}catch (Exception e)
		{
			loperand = ValueBuilder.getValue(0);
			roperand = ValueBuilder.getValue(0);
			return visit(ctx.func_call());
		}
		if(ctx.POW() != null)
		{
			double lvalue, rvalue = 0;
			lvalue = (double)loperand.getValue();
			rvalue = (double)roperand.getValue();
			double result = java.lang.Math.pow(lvalue,  rvalue);
			System.out.println(lvalue + " ^ " + rvalue + " = " + result); // push result onto stack
			return new TerminalNodeImpl(new CommonToken(simpLParser.NUMBER, Double.toString(result)));
		}
		else if(ctx.MUL() != null)
		{
			double lvalue, rvalue = 0;
			lvalue = (double)loperand.getValue();
			rvalue = (double)roperand.getValue();
			double result = lvalue * rvalue;
			text.add(CodeEmitter.Mul());
			decStackSize();
			a = new TerminalNodeImpl(new CommonToken(simpLParser.NUMBER, Double.toString(result)));
		}
		else if(ctx.DIV() != null)
		{
			double lvalue, rvalue = 0;
			lvalue = (double)loperand.getValue();
			rvalue = (double)roperand.getValue();
			double result = lvalue / rvalue;
			text.add(CodeEmitter.Div());
			decStackSize();
			a = new TerminalNodeImpl(new CommonToken(simpLParser.NUMBER, Double.toString(result)));
		}
		else if(ctx.ADD() != null)
		{
			// currently only supports double
			double lvalue, rvalue = 0;
			lvalue = (double)loperand.getValue();
			rvalue = (double)roperand.getValue();
			double result = lvalue + rvalue;
			text.add(CodeEmitter.Add());
			decStackSize();
			a =  new TerminalNodeImpl(new CommonToken(simpLParser.NUMBER, Double.toString(result)));
		}
		else if(ctx.SUB() != null)
		{
			double lvalue, rvalue = 0;
			lvalue = (double)loperand.getValue();
			rvalue = (double)roperand.getValue();
			double result = lvalue - rvalue;
			text.add(CodeEmitter.Sub());
			decStackSize();
			a = new TerminalNodeImpl(new CommonToken(simpLParser.NUMBER, Double.toString(result)));	
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
		return a;
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

	private Value getOperandValue(Value val)
	{
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
	@Override public TerminalNode visitFunc_call(simpLParser.Func_callContext ctx) 
	{ 
		TerminalNode a = null;
		String name = ctx.NAME().toString();
		if(name.equals("println"))
		{
			// print ln - evaluate all expressions in function currently only evalutes the first one
			// for testing

		}
		else if(name.equals("print"))
		{
			a = visit(ctx.expr(0));
			Value val = ValueBuilder.getValue(a.getSymbol(), memory);
			if(a.getSymbol().getType() == simpLParser.NUMBER)
			{
				text.add(CodeEmitter.Print("NUMBER"));
			}
			else if(a.getSymbol().getType() == simpLParser.NAME)
			{
				Variable var = (Variable) val;
				text.add(CodeEmitter.PutVarStack(var));
				text.add(CodeEmitter.Print("NUMBER"));
			}
		}
		else if(name.equals("read"))
		{

		}
		return a; 
	}
}