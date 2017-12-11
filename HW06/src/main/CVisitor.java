package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;

import gen.SimpLBaseVisitor;
import gen.SimpLParser;

public class CVisitor extends SimpLBaseVisitor<TerminalNode>
{
    private int stackSize;
    private int necessaryStackSize;
    private int localCount;
    private int labelCount;
    private int condLabelCount;
    private List<String> text;
    private Map<String, Value> memory;
    private Map<String, Integer> ifMemory;
    private Map<String, Supplier<String>> codeEmissionMap;

    public CVisitor()
    {
        super();
        memory = new HashMap<>();
        ifMemory = new HashMap<>();

        // create and populate list with typical code emission functions
        codeEmissionMap = new HashMap();
        List<Supplier<String>> codeEmissionFunctions = Arrays.asList(CodeEmitter::add, CodeEmitter::sub, CodeEmitter::mul, CodeEmitter::div);
        String [] functionTokens = {"ADD", "SUB", "MUL", "DIV"};
        for(int x = 0; x < functionTokens.length; x++) codeEmissionMap.put(functionTokens[x], codeEmissionFunctions.get(x));

        stackSize = 0;
        necessaryStackSize = 0;
        localCount = 1;
        labelCount = 0;
        condLabelCount = 0;
        CodeEmitter.initialize();
    }
    public List<String> getText()
    {
        return text;
    }
    public int getStackSize()
    {
        return necessaryStackSize;
    }
    public Map<String, Value> getMemory()
    {
        return memory;
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

        // todo: rename stackSizeLine - it's not clear what it's doing
        int stackSizeLine = 0;
        stackSizeLine = text.size();
        text.add(CodeEmitter.setStack(stackSize) + CodeEmitter.setLocals(localCount));

        TerminalNode node = super.visitChildren(ctx);
        text.set(
            // since everything is stored as float, multiply by 2 I think
            stackSizeLine, CodeEmitter.setStack((stackSize + localCount) * 2) + CodeEmitter.setLocals(localCount)
        );
        return node;
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
        // todo: add type-checking
        String name = ctx.NAME().toString();
        CommonToken token = new CommonToken(visit(ctx.expr()).getSymbol());
        Value val = null;
        Variable var = null;
        if (ctx.ASSIGN() != null)
        {
            val = getOperandValue(token);
            var = new Variable(name, val, val.getType());
            text.add(CodeEmitter.declareVariable(var, localCount));
            memory.put(name, var);
        }
        else memory.put(name, null); // add typing regardless of assignment or not
        localCount++;
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

        int parserType = getParseType(val);

        incStackSize(2);
        if (memory.get(identifier) == null)
        {
            //todo: add error for if identifier exists. if not, it must be declared
            // throw error, assignment on undeclared identifier
            //throw new Exception("UNDELCARED IDENTIFIER");
        }
        Variable var = (Variable) memory.get(identifier);
        if (var.getCast().equals(val.getType()))
            var.setValue(val);
        else
            System.out.println("Improper cast!"); // todo: throw error here since different type

        memory.put(identifier, var);
        text.add(CodeEmitter.assignVariable(var));
        decStackSize(2);
        return new TerminalNodeImpl(new CommonToken(parserType, getNodeField(val)));
    }

    @Override public TerminalNode visitWhile_loop(SimpLParser.While_loopContext ctx)
    {
        String label = CodeEmitter.getLabel(labelCount);
        labelCount++;
        String exitLabel = CodeEmitter.getLabel(labelCount);
        labelCount++;

        text.add(label);
        String expr_type = visit(ctx.expr()).getSymbol().getText();
        text.add(CodeEmitter.ifOperation(expr_type, exitLabel));
        visit(ctx.block());
        text.add(CodeEmitter.getGoTo(label));
        text.add(exitLabel);
        return new TerminalNodeImpl(new CommonToken(SimpLParser.BOOLEAN, "WHILE"));
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
        String label = CodeEmitter.getLabel(labelCount);
        text.add(label);
        labelCount++;

        String evaluate_type, cond_label = null;
        List<Integer> lastLabelSkip = new ArrayList<>();
        int block_count = 0;
        for (SimpLParser.ExprContext exp : expressions)
        {
            evaluate_type = visit(exp).getSymbol().getText();
            ifMemory.put(label, countLines());
            cond_label = CodeEmitter.getCondLabel(condLabelCount);
            text.add(CodeEmitter.ifOperation(evaluate_type, cond_label));
            visit(ctx.block(block_count));
            lastLabelSkip.add(text.size());

            text.add(CodeEmitter.getGoTo("temp")); // create a temporary label
            text.add(cond_label);
            condLabelCount++;
            block_count++;
        }

        // else block
        if (block_count < ctx.block().size())
        {
            visit(ctx.block(block_count));
            cond_label = CodeEmitter.getCondLabel(condLabelCount);
            text.add(cond_label);
            condLabelCount++;
        }
        for (int labelNum : lastLabelSkip)
            text.set(labelNum, CodeEmitter.getGoTo(cond_label));

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
        for (SimpLParser.StmtContext stmt : stmts)
            visit(stmt);
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
        return processExprContext(ctx);
    }
    // todo: address the return values of the CodeEmitter function calls
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public TerminalNode visitFunc_call(SimpLParser.Func_callContext ctx)
    {
        TerminalNode node = null;
        String name = ctx.NAME().toString();
        Value val = null;
        Variable var = null;
        List<SimpLParser.ExprContext> expressions = ctx.expr();
        if (name.equals("print") || name.equals("println"))
        {
            for (SimpLParser.ExprContext exp : expressions)
            {
                node = visit(exp);
                val = ValueBuilder.getValue(node.getSymbol(), memory);
                text.add(name.equals("print") ? CodeEmitter.print(val.getType()) : CodeEmitter.println(val.getType())); // check if print or println
            }
        }
        return node;
    }

    // ------ private helpers
    private int countLines()
    {
        int numLines = 0;
        for (String str : text)
            for (int x = 0; x < str.length(); x++)
                if (str.charAt(x) == '\n' || str.charAt(x) == '\r')
                    numLines++;
        return numLines + text.size();
    }

    private Value getOperandValue(Token token)
    {
        Value value = ValueBuilder.getValue(token, memory);
        return value.getType().equalsIgnoreCase("IDENTIFIER")? (Value) value.getValue() : value;
    }

    private Value getOperandValue(Value val)
    {
        return val.getType().equals("IDENTIFIER") ? (Value) val.getValue() : val;
    }

    private void incStackSize(int sizeIncrease)
    {
        stackSize += sizeIncrease;
        if (stackSize > necessaryStackSize)
            necessaryStackSize = stackSize;
    }
    private void decStackSize(int sizeDecrease)
    {
        stackSize = (sizeDecrease >= stackSize)? 0 : stackSize - sizeDecrease;
    }

    private int getParseType(String type)
    {
        if(type.equals("NUMBER")) return SimpLParser.NUMBER;
        else if(type.equals("TEXT")) return SimpLParser.TEXT;
        else if(type.equals("BOOLEAN")) return SimpLParser.BOOLEAN;
        // todo: else assume text?
        return SimpLParser.TEXT;
    }

    private int getParseType(Value type)
    {
        return getParseType(getOperandValue(type).getType());
    }

    private String getExprCtxType(SimpLParser.ExprContext ctx)
    {
        if(ctx.NAME() != null) return "IDENTIFIER";
        else if (ctx.LITERAL() != null) return "LITERAL";
        //else if () return "BOOL OPERATION";// bool
        else if (ctx.POW() != null || ctx.ADD() != null || ctx.SUB() != null || ctx.MUL() != null || ctx.DIV() != null) return "ARITHMETIC";
        else if (ctx.AND() != null || ctx.OR() != null || ctx.GT() != null || ctx.LT() != null || ctx.GTE() != null || ctx.LTE() != null || ctx.EQ() != null || ctx.NEQ() != null) return "BOOL OPERATION";
        return "TEST";
    }

    private Boolean checkParens(SimpLParser.ExprContext ctx)
    {
        return (ctx.LPAREN() != null && ctx.RPAREN() == null) ? false : true;
    }

    private Boolean containsParens(SimpLParser.ExprContext ctx)
    {
        return (ctx.LPAREN() != null || ctx.RPAREN() != null) ? true : false;
    }

    private String getCtxOperation(SimpLParser.ExprContext ctx)
    {
        String [] operations = {"ADD", "SUB", "POW", "MUL", "DIV", "AND", "OR", "GT", "LT", "GTE", "LTE", "EQ", "NEQ"};
        List<Supplier<TerminalNode>> ctxFunctions = java.util.Arrays.asList(ctx::ADD, ctx::SUB, ctx::POW, ctx::MUL, ctx::DIV, ctx::AND, ctx::OR, ctx::GT, ctx::LT, ctx::GTE, ctx::LTE, ctx::EQ, ctx::NEQ);
        for(int x = 0; x < operations.length; x++)
        {
            if(ctxFunctions.get(x).get() != null) return operations[x];
        }
        return "ERROR";
    }

    private TerminalNode processExprContext(SimpLParser.ExprContext ctx)
    {
        // Terribly written, we should come back and review this. Just trying to get some working code in
        // would be easy to change grammar to encompass symbols by category. e.g. POW, NUL, DIV .. belong to arithmetic_operators
        TerminalNodeImpl node = null;
        /*if(!checkParens(ctx))
        {
            System.out.println("unbalanced parens");    // todo: throw error, parens not balanced
        }*/
        if (getExprCtxType(ctx).equals("IDENTIFIER"))
        {
            Value val = memory.get(ctx.NAME().getSymbol().getText().toString()); // if undeclared throw error
            if (val == null) return new TerminalNodeImpl(new CommonToken(SimpLParser.BOOLEAN, "true")); // todo: throw error, value shouldn't be null
            Value operand = (Value) val.getValue();
            text.add(CodeEmitter.putVarStack((Variable) val));
            node = new TerminalNodeImpl(new CommonToken(getParseType(operand), getNodeField(operand)));
        }
        else if (getExprCtxType(ctx).equals("LITERAL"))
        {
            Value operand = getOperandValue(ctx.LITERAL().getSymbol());
            text.add(CodeEmitter.loadConstant(operand));
            node = new TerminalNodeImpl(new CommonToken(getParseType(operand), getNodeField(operand)));
        }
        else if(ctx.LPAREN() != null)
        {
            node = new TerminalNodeImpl(visit(ctx.expr(0)).getSymbol());
        }
        else
        {
            // try for unary operator: `not`
            Value loperand, roperand;
            incStackSize(2);
            if (ctx.NOT() != null)
            {
                loperand = getOperandValue(visit(ctx.expr(0)).getSymbol());
                text.add(CodeEmitter.booleanOperation("NOT"));
                return new TerminalNodeImpl(new CommonToken(SimpLParser.BOOLEAN, "NOT"));
            }
            try
            {
                loperand = getOperandValue(visit(ctx.expr(0)).getSymbol());
                roperand = getOperandValue(visit(ctx.expr(1)).getSymbol());
            }
            catch (Exception e)
            {
                return visit(ctx.func_call());
            }
            String operation = getCtxOperation(ctx);
            if (getExprCtxType(ctx).equals("ARITHMETIC"))
            {
                Number result = performOperation((Number) loperand, (Number) roperand, operation);
                Supplier<String> supFunction = codeEmissionMap.get(operation);
                if (supFunction == null)
                    /* todo: invalid operation throw error */;
                else text.add(supFunction.get());
                node = new TerminalNodeImpl(new CommonToken(getParseType(result), getNodeField(result)));
                decStackSize(1);
            }
            else if (getExprCtxType(ctx).equals("BOOL OPERATION"))
            {
                if (loperand.getType().equals("IDENTIFIER"))
                    text.add(CodeEmitter.putVarStack((Variable)loperand));
                if (roperand.getType().equals("IDENTIFIER"))
                    text.add(CodeEmitter.putVarStack((Variable)roperand));
                text.add(CodeEmitter.booleanOperation(operation.toLowerCase()));
                node = new TerminalNodeImpl(new CommonToken(SimpLParser.BOOLEAN, operation));
            }
        }
        return node;
    }

    private String getNodeField(Value val)
    {
        Value value = getOperandValue(val);
        if (value.getType().equals("NUMBER"))
            return Double.toString((double) value.getValue());
        if (value.getType().equals("TEXT"))
            return value.getValue().toString();
        if (value.getType().equals("BOOLEAN"))
            return String.valueOf(value.getValue());
        return val.getType();
    }
    public static Number performOperation(Number a, Number b, String operation)
    {
        Number result = null;
        if (operation.equals("ADD"))
            return new Number(a.getValue() + b.getValue());
        if (operation.equals("SUB"))
            return new Number(a.getValue() - b.getValue());
        if (operation.equals("MUL"))
            return new Number(a.getValue() * b.getValue());
        if (operation.equals("DIV"))
            return new Number(a.getValue() / b.getValue());
        if (operation.equals("POW"))
            /* Currently unsupported. */;
        else
            /* todo: add error handling here... */;
        return result;
    }
}
