package main;

import gen.SimpLBaseVisitor;
import gen.SimpLParser;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.List;


public class CVisitor extends SimpLBaseVisitor<TerminalNode>
{
	private static int stack_size = 0;
	private static int necessary_stack_size = 0;
	private static int locals = 1;
	private static ArrayList<String> text;
	private static int label_count = 0;
	private static int cond_label_count = 0;
	private java.util.Map<String, Value> memory = new java.util.HashMap<String, Value>();
	private java.util.Map<String, Integer> if_memory = new java.util.HashMap<String, Integer>();

	public CVisitor()
	{
		super();
		CodeEmitter.initialize();
	}

	public void incLabelCount()
	{
		label_count++;
	}
	public void incCondLabelCount()
	{
		cond_label_count++;
	}

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
	private void incLocals()
	{
		locals++;
	}
	private void decLocals()
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
	@Override public TerminalNode visitProgram(SimpLParser.ProgramContext ctx)
	{
		text = new ArrayList<String>();
		text.add(CodeEmitter.getLibraryCode("math"));
		text.add(CodeEmitter.main());
		int stack_size_line = 0;
		stack_size_line = text.size();
		text.add(CodeEmitter.setStack(stack_size) + CodeEmitter.setlocals(locals));
		TerminalNode a = super.visitChildren(ctx);
		text.set(stack_size_line, CodeEmitter.setStack((stack_size + locals) * 2) + CodeEmitter.setlocals(locals)); // since everything is stored as float, multiply by 2 I think
		return a;
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public TerminalNode visitStmt(SimpLParser.StmtContext ctx)
	{
		return super.visitChildren(ctx);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public TerminalNode visitDeclaration(SimpLParser.DeclarationContext ctx)
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
			text.add(CodeEmitter.declareVariable(var, locals));
			memory.put(name, var);
			//System.out.println("creating " + name + " and setting to " + val.getValue());
		}
		else memory.put(name, null); // add typing regardless of assignment or not
		incLocals();
		if(val != null) return new TerminalNodeImpl(new CommonToken(SimpLParser.NUMBER, "0"));
		//else if(parser_type == SimpLParser.NUMBER) return new TerminalNodeImpl(new CommonToken(parser_type, Double.toString((double)val.getValue())));
		//else return new TerminalNodeImpl(new CommonToken(parser_type, val.getValue().toString()));
		return new TerminalNodeImpl(token);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public TerminalNode visitAssignment(SimpLParser.AssignmentContext ctx)
	{
		// check if it exists in the memory map
		// TODO: check validity based on variable cast using .getCast() method
		String identifier = ctx.NAME().getSymbol().getText();
		Value val = getOperandValue(visit(ctx.expr()).getSymbol());
		int parser_type = 0;
		if(val.getType().equals("NUMBER")) parser_type = SimpLParser.NUMBER;
		else if(val.getType().equals("BOOLEAN")) parser_type = SimpLParser.BOOLEAN;
		else parser_type = SimpLParser.TEXT;
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
		text.add(CodeEmitter.assignVariable(var));
		decStackSize(2);
		if(parser_type == SimpLParser.NUMBER) return new TerminalNodeImpl(new CommonToken(SimpLParser.NUMBER, Double.toString((double)val.getValue())));
		else return new TerminalNodeImpl(new CommonToken(SimpLParser.NUMBER, val.getValue().toString()));
	}

	@Override public TerminalNode visitWhile_loop(SimpLParser.While_loopContext ctx)
	{
		String label = CodeEmitter.getLabel(label_count);
		incLabelCount();
		String exit_label = CodeEmitter.getLabel(label_count);
		incLabelCount();

		text.add(label);
		String expr_type = visit(ctx.expr()).getSymbol().getText().toString();
		text.add(CodeEmitter.ifOperation(expr_type, exit_label));
		visit(ctx.block());
		text.add(CodeEmitter.getGoTo(label));
		text.add(exit_label);
		return new TerminalNodeImpl(new CommonToken(SimpLParser.BOOLEAN, "loop"));
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public TerminalNode visitIf_stmt(SimpLParser.If_stmtContext ctx)
	{
		List<SimpLParser.ExprContext> expressions = ctx.expr();
		int block_count = 0;
		String label = CodeEmitter.getLabel(label_count);
		text.add(label);
		incLabelCount();
		String evaluate_type, cond_label = null;
		ArrayList<Integer> last_label_skip = new ArrayList<Integer>();
		for(SimpLParser.ExprContext exp : expressions)
		{
			evaluate_type = visit(exp).getSymbol().getText();
			if_memory.put(label, countLines());
			cond_label = CodeEmitter.getCondLabel(cond_label_count);
			text.add(CodeEmitter.ifOperation(evaluate_type, cond_label));
			visit(ctx.block(block_count));
			last_label_skip.add(text.size());

			text.add(CodeEmitter.getGoTo("temp"));
			text.add(cond_label);
			incCondLabelCount();
			block_count++;
		}

		// else block
		if(block_count < ctx.block().size())
		{
			visit(ctx.block(block_count));
			cond_label = CodeEmitter.getCondLabel(cond_label_count);
			text.add(cond_label);
			incCondLabelCount();
		}
		for(int a : last_label_skip)
		{
			text.set(a, CodeEmitter.getGoTo(cond_label));
		}

		//System.out.print("resulting node: " + a);
		// if then body. if condition is not met, the label sends it outside if statement, otherwise continue normally
		return new TerminalNodeImpl(new CommonToken(SimpLParser.BOOLEAN, "true"));
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public TerminalNode visitFunc_def(SimpLParser.Func_defContext ctx) { return super.visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public TerminalNode visitBlock(SimpLParser.BlockContext ctx)
	{
		// don't execute expr yet. this would be the return statement
		List<SimpLParser.StmtContext> stmts = ctx.stmt();
		for(SimpLParser.StmtContext stmt : stmts) visit(stmt);
		return new TerminalNodeImpl(new CommonToken(SimpLParser.LITERAL, "block"));
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public TerminalNode visitExpr(SimpLParser.ExprContext ctx)
	{
		// Terribly written, we should come back and review this. Just trying to get some working code in
		// would be easy to change grammar to encompass symbosl by category. e.g. POW, NUL, DIV .. belong to arithemtic_operators
		TerminalNodeImpl a = null;
		if(ctx.NAME() != null)
		{
			Value val = memory.get(ctx.NAME().getSymbol().getText().toString()); // if undeclared throw error
			if(val == null) return new TerminalNodeImpl(new CommonToken(SimpLParser.BOOLEAN, "true"));
			double result;
			if(val.getType().equals("IDENTIFIER"))
			{
				Variable var = (Variable) val;
				Value operand = var.getValue();
				text.add(CodeEmitter.putVarStack(var));
				if(operand.getType().equals("BOOLEAN"))
				{
					return new TerminalNodeImpl(new CommonToken(SimpLParser.BOOLEAN, operand.getValue().toString()));
				}
				else if(!operand.getType().equals("NUMBER"))
				{
					return new TerminalNodeImpl(new CommonToken(SimpLParser.TEXT, (String)operand.getValue()));
				}
				else result = (double)operand.getValue();
			}
			else result = (double) val.getValue();
			return new TerminalNodeImpl(new CommonToken(SimpLParser.NUMBER, Double.toString(result)));
		}
		else if(ctx.LITERAL() != null)
		{
			// check if number of text for now assuming number
			//CommonToken token = new CommonToken(ctx.LITERAL().getSymbol());
			Value operand = getOperandValue(ctx.LITERAL().getSymbol());
			if(operand.getType().equals("NUMBER"))
			{
				double result = (double)operand.getValue();
				text.add(CodeEmitter.loadConstant(result));
				return new TerminalNodeImpl(new CommonToken(SimpLParser.NUMBER, Double.toString(result)));
			}
			else if(operand.getType().equals("BOOLEAN"))
			{
				Boolean result = (Boolean) operand.getValue();
				text.add(CodeEmitter.loadConstant(result));
				return new TerminalNodeImpl(new CommonToken(SimpLParser.BOOLEAN, Boolean.toString(result)));
			}
			else
			{
				text.add(CodeEmitter.loadConstant(operand.getValue().toString()));
				return new TerminalNodeImpl(new CommonToken(SimpLParser.TEXT, (String)operand.getValue()));
			}
		}
		else if(ctx.LPAREN() != null)
		{
			if(ctx.RPAREN() == null) System.out.println("no matching paren");
			TerminalNodeImpl result = new TerminalNodeImpl(visit(ctx.expr(0)).getSymbol());
			return result;
		}
		/*else if(ctx.RPAREN() != null)
		{
			return ctx.RPAREN();
		}*/
		Value loperand, roperand;
		incStackSize(2);
		// try for single operator such as not
		if(ctx.NOT() != null)
		{
			loperand = getOperandValue(visit(ctx.expr(0)).getSymbol());
			if(loperand.getType().equals("IDENTIFIER"))
			{
				text.add(CodeEmitter.putVarStack((Variable)loperand));
			}
			//else text.add(CodeEmitter.loadConstant((Boolean) loperand.getValue()));
			text.add(CodeEmitter.booleanOperation("NOT"));
			return new TerminalNodeImpl(new CommonToken(SimpLParser.BOOLEAN, "NOT"));
		}
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
			return new TerminalNodeImpl(new CommonToken(SimpLParser.NUMBER, Double.toString(result)));
		}
		else if(ctx.MUL() != null)
		{
			double lvalue, rvalue = 0;
			lvalue = (double)loperand.getValue();
			rvalue = (double)roperand.getValue();
			double result = lvalue * rvalue;
			text.add(CodeEmitter.mul());
			decStackSize();
			a = new TerminalNodeImpl(new CommonToken(SimpLParser.NUMBER, Double.toString(result)));
		}
		else if(ctx.DIV() != null)
		{
			double lvalue, rvalue = 0;
			lvalue = (double)loperand.getValue();
			rvalue = (double)roperand.getValue();
			double result = lvalue / rvalue;
			text.add(CodeEmitter.div());
			decStackSize();
			a = new TerminalNodeImpl(new CommonToken(SimpLParser.NUMBER, Double.toString(result)));
		}
		else if(ctx.ADD() != null)
		{
			// currently only supports double
			double lvalue, rvalue = 0;
			lvalue = (double)loperand.getValue();
			rvalue = (double)roperand.getValue();
			double result = lvalue + rvalue;
			text.add(CodeEmitter.add());
			decStackSize();
			a =  new TerminalNodeImpl(new CommonToken(SimpLParser.NUMBER, Double.toString(result)));
		}
		else if(ctx.SUB() != null)
		{
			double lvalue, rvalue = 0;
			lvalue = (double)loperand.getValue();
			rvalue = (double)roperand.getValue();
			double result = lvalue - rvalue;
			text.add(CodeEmitter.sub());
			decStackSize();
			a = new TerminalNodeImpl(new CommonToken(SimpLParser.NUMBER, Double.toString(result)));
		}
		// Boolean operators
		else if(ctx.AND() != null)
		{
			System.out.println(loperand + "---" + roperand);
			// add check that both are boolean?
			if(loperand.getType().equals("IDENTIFIER"))
			{
				text.add(CodeEmitter.putVarStack((Variable)loperand));
			}
			else CodeEmitter.loadConstant((Boolean) loperand.getValue());
			if(roperand.getType().equals("IDENTIFIER"))
			{
				text.add(CodeEmitter.putVarStack((Variable)roperand));
			}
			else CodeEmitter.loadConstant((Boolean)loperand.getValue());
			text.add(CodeEmitter.booleanOperation("and"));
			return new TerminalNodeImpl(new CommonToken(SimpLParser.BOOLEAN, "AND"));
		}
		else if(ctx.OR() != null)
		{
			System.out.println(loperand + "---" + roperand);
			// add check that both are boolean?
			if(loperand.getType().equals("IDENTIFIER"))
			{
				text.add(CodeEmitter.putVarStack((Variable)loperand));
			}
			else CodeEmitter.loadConstant((Boolean) loperand.getValue());
			if(roperand.getType().equals("IDENTIFIER"))
			{
				text.add(CodeEmitter.putVarStack((Variable)roperand));
			}
			else CodeEmitter.loadConstant((Boolean)loperand.getValue());
			text.add(CodeEmitter.booleanOperation("or"));
			return new TerminalNodeImpl(new CommonToken(SimpLParser.BOOLEAN, "OR"));
		}
		else if(ctx.LT() != null)
		{
			if(loperand.getType().equals("IDENTIFIER"))
			{
				text.add(CodeEmitter.putVarStack((Variable)loperand));
			}
			//else text.add(CodeEmitter.loadConstant((double)loperand.getValue()));
			if(roperand.getType().equals("IDENTIFIER"))
			{
				text.add(CodeEmitter.putVarStack((Variable)roperand));
			}
			//else text.add(CodeEmitter.loadConstant((double) roperand.getValue()));
			text.add(CodeEmitter.booleanOperation("lt"));
			return new TerminalNodeImpl(new CommonToken(SimpLParser.BOOLEAN, "LT"));
		}
		else if(ctx.GT() != null)
		{
			if(loperand.getType().equals("IDENTIFIER"))
			{
				text.add(CodeEmitter.putVarStack((Variable)loperand));
			}
			//else text.add(CodeEmitter.loadConstant((double)loperand.getValue()));
			if(roperand.getType().equals("IDENTIFIER"))
			{
				text.add(CodeEmitter.putVarStack((Variable)roperand));
			}
			//else text.add(CodeEmitter.loadConstant((double) roperand.getValue()));
			text.add(CodeEmitter.booleanOperation("gt"));
			return new TerminalNodeImpl(new CommonToken(SimpLParser.BOOLEAN, "GT"));
		}
		else if(ctx.GTE() != null)
		{
			if(loperand.getType().equals("IDENTIFIER"))
			{
				text.add(CodeEmitter.putVarStack((Variable)loperand));
			}
			//else text.add(CodeEmitter.loadConstant((double)loperand.getValue()));
			if(roperand.getType().equals("IDENTIFIER"))
			{
				text.add(CodeEmitter.putVarStack((Variable)roperand));
			}
			//else text.add(CodeEmitter.loadConstant((double) roperand.getValue()));
			text.add(CodeEmitter.booleanOperation("gte"));
			return new TerminalNodeImpl(new CommonToken(SimpLParser.BOOLEAN, "GTE"));
		}
		else if(ctx.LTE() != null)
		{
			if(loperand.getType().equals("IDENTIFIER"))
			{
				text.add(CodeEmitter.putVarStack((Variable)loperand));
			}
			//else text.add(CodeEmitter.loadConstant((double)loperand.getValue()));
			if(roperand.getType().equals("IDENTIFIER"))
			{
				text.add(CodeEmitter.putVarStack((Variable)roperand));
			}
			//else text.add(CodeEmitter.loadConstant((double) roperand.getValue()));
			text.add(CodeEmitter.booleanOperation("lte"));
			return new TerminalNodeImpl(new CommonToken(SimpLParser.BOOLEAN, "LTE"));
		}
		else if(ctx.EQ() != null)
		{
			if(loperand.getType().equals("IDENTIFIER"))
			{
				text.add(CodeEmitter.putVarStack((Variable)loperand));
			}
			//else text.add(CodeEmitter.loadConstant((double)loperand.getValue()));
			if(roperand.getType().equals("IDENTIFIER"))
			{
				text.add(CodeEmitter.putVarStack((Variable)roperand));
			}
			//else text.add(CodeEmitter.loadConstant((double) roperand.getValue()));
			text.add(CodeEmitter.booleanOperation("eq"));
			return new TerminalNodeImpl(new CommonToken(SimpLParser.BOOLEAN, "EQ"));
		}
		else if(ctx.NEQ() != null)
		{
			if(loperand.getType().equals("IDENTIFIER"))
			{
				text.add(CodeEmitter.putVarStack((Variable)loperand));
			}
			//else text.add(CodeEmitter.loadConstant((double)loperand.getValue()));
			if(roperand.getType().equals("IDENTIFIER"))
			{
				text.add(CodeEmitter.putVarStack((Variable)roperand));
			}
			//else text.add(CodeEmitter.loadConstant((double) roperand.getValue()));
			text.add(CodeEmitter.booleanOperation("neq"));
			return new TerminalNodeImpl(new CommonToken(SimpLParser.BOOLEAN, "NEQ"));
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
	@Override public TerminalNode visitFunc_call(SimpLParser.Func_callContext ctx)
	{
		TerminalNode a = null;
		String name = ctx.NAME().toString();
		Value val = null;
		Variable var = null;
		if(name.equals("println"))
		{
			List<SimpLParser.ExprContext> expressions = ctx.expr();
			for(SimpLParser.ExprContext exp : expressions)
			{
				a = visit(exp);
				val = ValueBuilder.getValue(a.getSymbol(), memory);
				if(a.getSymbol().getType() == SimpLParser.NUMBER)
				{
					text.add(CodeEmitter.println("NUMBER"));
				}
				else if(a.getSymbol().getType() == SimpLParser.NAME)
				{
					var = (Variable) val;
					val = var.getValue();
					text.add(CodeEmitter.putVarStack(var));
					text.add(CodeEmitter.println(val.getType()));
				}
				else if(a.getSymbol().getType() == SimpLParser.BOOLEAN)
				{
					text.add(CodeEmitter.println("BOOLEAN"));
				}
				else
				{
					CodeEmitter.loadConstant(val.getValue().toString());
					text.add(CodeEmitter.println("TEXT"));
				}
			}
		}
		else if(name.equals("print"))
		{
			List<SimpLParser.ExprContext> expressions = ctx.expr();
			for(SimpLParser.ExprContext exp : expressions)
			{
				a = visit(exp);
				val = ValueBuilder.getValue(a.getSymbol(), memory);
				if(a.getSymbol().getType() == SimpLParser.NUMBER)
				{
					text.add(CodeEmitter.print("NUMBER"));
				}
				else if(a.getSymbol().getType() == SimpLParser.NAME)
				{
					var = (Variable) val;
					val = var.getValue();
					text.add(CodeEmitter.putVarStack(var));
					text.add(CodeEmitter.print(val.getType()));
				}
				else if(a.getSymbol().getType() == SimpLParser.BOOLEAN)
				{
					text.add(CodeEmitter.print("BOOLEAN"));
				}
				else
				{
					CodeEmitter.loadConstant(val.getValue().toString());
					text.add(CodeEmitter.print("TEXT"));
				}
			}
		}
		else if(name.equals("read"))
		{

		}
		return a;
	}
	private int countLines()
	{
		int num_lines = 0;
		for(String str : text)
		{
			for(int x = 0; x < str.length(); x++)
			{
				if(str.charAt(x) == '\n' || str.charAt(x) == '\r') num_lines++;
			}
		}
		num_lines += text.size();
		return num_lines;
	}
}
