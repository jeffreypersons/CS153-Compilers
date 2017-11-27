package main;

import java.util.ArrayList;
import java.util.List;

import gen.simpLBaseVisitor;
import gen.simpLParser;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;
//import gen.simpLBaseVisitor;

public class cVisitor extends simpLBaseVisitor<TerminalNode>
{
	private static int stack_size = 0;
	private static int necessary_stack_size = 0;
	private static int locals = 1;
	private static List<String> text;
	private java.util.Map<String, Value> memory = new java.util.HashMap<String, Value>();

	public List<String> getText()
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
		CommonToken token = new CommonToken(visit(ctx.expr()).getSymbol());
		Value val = null;
		Variable var = null;
		if(ctx.ASSIGN() != null)
		{
			val = getOperandValue(token);
			var = new Variable(name, val, val.getType());
			text.add(CodeEmitter.DeclareVariable(var, locals));
			memory.put(name, var);
			//System.out.println("creating " + name + " and setting to " + val.getValue());
		}
		else memory.put(name, null); // add typing regardless of assignment or not
		IncLocals();
		if(val != null) return new TerminalNodeImpl(new CommonToken(simpLParser.NUMBER, "0"));
		//else if(parser_type == simpLParser.NUMBER) return new TerminalNodeImpl(new CommonToken(parser_type, Double.toString((double)val.getValue())));
		//else return new TerminalNodeImpl(new CommonToken(parser_type, val.getValue().toString()));
		return new TerminalNodeImpl(token);
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
		int parser_type = 0;
		if(val.getType().equals("NUMBER")) parser_type = simpLParser.NUMBER;
		else if(val.getType().equals("BOOLEAN")) parser_type = simpLParser.BOOLEAN;
		else parser_type = simpLParser.TEXT;
		incStackSize(2);
		if (memory.get(identifier) == null)
		{
			// throw error, assignment on undeclared identifier
			//throw new Exception("UNDELCARED IDENTIFIER");
		}
		Variable var = (Variable) memory.get(identifier);
		if(var.getCast().equals(val.getType())) var.setValue(val);
		else System.out.println("Improper cast!"); // throw error here - different type
		//var.setValue(val);
		memory.put(identifier, var);
		text.add(CodeEmitter.AssignVariable(var));
		decStackSize(2);
		if(parser_type == simpLParser.NUMBER) return new TerminalNodeImpl(new CommonToken(simpLParser.NUMBER, Double.toString((double)val.getValue())));
		else return new TerminalNodeImpl(new CommonToken(simpLParser.NUMBER, val.getValue().toString()));
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public TerminalNode visitIf_stmt(simpLParser.If_stmtContext ctx)
	{
		return super.visitChildren(ctx);
	}
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
				Value operand = var.getValue();
				text.add(CodeEmitter.PutVarStack(var));
				if(operand.getType().equals("BOOLEAN"))
				{
					return new TerminalNodeImpl(new CommonToken(simpLParser.BOOLEAN, operand.getValue().toString()));
				}
				else if(!operand.getType().equals("NUMBER"))
				{
					return new TerminalNodeImpl(new CommonToken(simpLParser.TEXT, (String)operand.getValue()));
				}
				else result = (double)operand.getValue();
			}
			else result = (double) val.getValue();
			return new TerminalNodeImpl(new CommonToken(simpLParser.NUMBER, Double.toString(result)));
		}
		else if(ctx.LITERAL() != null)
		{
			// check if number of text for now assuming number
			//CommonToken token = new CommonToken(ctx.LITERAL().getSymbol());
			Value operand = getOperandValue(ctx.LITERAL().getSymbol());
			if(operand.getType().equals("NUMBER"))
			{
				double result = (double)operand.getValue();
				text.add(CodeEmitter.LoadConstant(result));
				return new TerminalNodeImpl(new CommonToken(simpLParser.NUMBER, Double.toString(result)));
			}
			else if(operand.getType().equals("BOOLEAN"))
			{
				Boolean result = (Boolean) operand.getValue();
				text.add(CodeEmitter.LoadConstant(result));
				return new TerminalNodeImpl(new CommonToken(simpLParser.BOOLEAN, Boolean.toString(result)));
			}
			else
			{
				text.add(CodeEmitter.LoadConstant(operand.getValue().toString()));
				return new TerminalNodeImpl(new CommonToken(simpLParser.TEXT, (String)operand.getValue()));
			}
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
			System.out.println(loperand);
			return new TerminalNodeImpl(new CommonToken(simpLParser.BOOLEAN, loperand.toString()));
		}
		else if(ctx.AND() != null)
		{
			System.out.println(loperand + "---" + roperand);
			// add check that both are boolean?
			if(loperand.getType().equals("IDENTIFIER"))
			{
				text.add(CodeEmitter.PutVarStack((Variable)loperand));
			}
			else CodeEmitter.LoadConstant((Boolean) loperand.getValue());
			if(roperand.getType().equals("IDENTIFIER"))
			{
				text.add(CodeEmitter.PutVarStack((Variable)roperand));
			}
			else CodeEmitter.LoadConstant((Boolean)loperand.getValue());
			text.add(CodeEmitter.BooleanOperation("and"));
			return new TerminalNodeImpl(new CommonToken(simpLParser.BOOLEAN, "AND"));
		}
		else if(ctx.OR() != null)
		{
			System.out.println(loperand + "---" + roperand);
			// add check that both are boolean?
			if(loperand.getType().equals("IDENTIFIER"))
			{
				text.add(CodeEmitter.PutVarStack((Variable)loperand));
			}
			else CodeEmitter.LoadConstant((Boolean) loperand.getValue());
			if(roperand.getType().equals("IDENTIFIER"))
			{
				text.add(CodeEmitter.PutVarStack((Variable)roperand));
			}
			else CodeEmitter.LoadConstant((Boolean)loperand.getValue());
			text.add(CodeEmitter.BooleanOperation("or"));
			return new TerminalNodeImpl(new CommonToken(simpLParser.BOOLEAN, "OR"));
		}
		else if(ctx.LT() != null)
		{
			if(loperand.getType().equals("IDENTIFIER"))
			{
				text.add(CodeEmitter.PutVarStack((Variable)loperand));
			}
			//else text.add(CodeEmitter.LoadConstant((double)loperand.getValue()));
			if(roperand.getType().equals("IDENTIFIER"))
			{
				text.add(CodeEmitter.PutVarStack((Variable)roperand));
			}
			//else text.add(CodeEmitter.LoadConstant((double) roperand.getValue()));
			text.add(CodeEmitter.BooleanOperation("lt"));
			return new TerminalNodeImpl(new CommonToken(simpLParser.BOOLEAN, "LT"));
		}
		else if(ctx.GT() != null)
		{
			if(loperand.getType().equals("IDENTIFIER"))
			{
				text.add(CodeEmitter.PutVarStack((Variable)loperand));
			}
			//else text.add(CodeEmitter.LoadConstant((double)loperand.getValue()));
			if(roperand.getType().equals("IDENTIFIER"))
			{
				text.add(CodeEmitter.PutVarStack((Variable)roperand));
			}
			//else text.add(CodeEmitter.LoadConstant((double) roperand.getValue()));
			text.add(CodeEmitter.BooleanOperation("gt"));
			return new TerminalNodeImpl(new CommonToken(simpLParser.BOOLEAN, "LT"));
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
		Value val = null;
		Variable var = null;
		if(name.equals("println"))
		{
			// print ln - evaluate all expressions in function currently only evalutes the first one
			// for testing

		}
		else if(name.equals("print"))
		{
			List<simpLParser.ExprContext> expressions = ctx.expr();
			for(simpLParser.ExprContext exp : expressions)
			{
				a = visit(exp);
				val = ValueBuilder.getValue(a.getSymbol(), memory);
				if(a.getSymbol().getType() == simpLParser.NUMBER)
				{
					text.add(CodeEmitter.Print("NUMBER"));
				}
				else if(a.getSymbol().getType() == simpLParser.NAME)
				{
					var = (Variable) val;
					val = var.getValue();
					text.add(CodeEmitter.PutVarStack(var));
					text.add(CodeEmitter.Print(val.getType()));
				}
				else if(a.getSymbol().getType() == simpLParser.BOOLEAN)
				{
					text.add(CodeEmitter.Print("BOOLEAN"));
				}
				else
				{
					CodeEmitter.LoadConstant(val.getValue().toString());
					text.add(CodeEmitter.Print("TEXT"));
				}
			}
		}
		else if(name.equals("read"))
		{

		}
		return a; 
	}
}