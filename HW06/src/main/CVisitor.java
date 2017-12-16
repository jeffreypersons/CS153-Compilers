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
    private int stackSizeLine;
    private int stackSize;
    private int necessaryStackSize;
    private int localCount;
    private int labelCount;
    private int condLabelCount;
    private List<String> text;
    private int functionInsertLine; // line to store declared functions in assembly file

    private int memoryLevel;
    private List<Map<String, Value>> memory;
    private Map<String, Function> functions;
    private Map<String, Integer> ifMemory;
    private Map<String, Supplier<String>> codeEmissionMap;
    private String funcType; // hold function type to validate return statement
    private ArrayList<FuncInfo> funcList;


    public CVisitor()
    {
        super();
        functions = new HashMap<String, Function>();
        memory = new ArrayList<Map<String,Value>>();//new HashMap<>();
        memory.add(new HashMap<String,Value>());
        memoryLevel = 0; // set to global hashmap
        ifMemory = new HashMap<>();

        // create and populate list with typical code emission functions
        codeEmissionMap = new HashMap();
        List<Supplier<String>> codeEmissionFunctions = Arrays.asList(
                CodeEmitter::add, CodeEmitter::sub, CodeEmitter::mul, CodeEmitter::div,
                CodeEmitter::pow
        );
        String [] functionTokens = {"ADD", "SUB", "MUL", "DIV", "POW"};
        for(int x = 0; x < functionTokens.length; x++) codeEmissionMap.put(functionTokens[x], codeEmissionFunctions.get(x));

        stackSize = 0;
        necessaryStackSize = 0;
        localCount = 1;
        labelCount = 0;
        condLabelCount = 0;
        funcType = "";
        funcList = new ArrayList<FuncInfo>();
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
    public List<Map<String, Value>> getMemory()
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
        functionInsertLine = text.size();
        text.add(CodeEmitter.main());

        // todo: rename stackSizeLine - it's not clear what it's doing
        stackSizeLine = 0;
        stackSizeLine = text.size();
        text.add(CodeEmitter.setStack(stackSize) + CodeEmitter.setLocals(localCount));

        TerminalNode node = super.visitChildren(ctx);
        text.set(
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
    @Override public TerminalNode visitStat(SimpLParser.StatContext ctx)
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
        // todo (COMPLETED): add type-checking
        String name = ctx.NAME().toString();
        String varType = ctx.getChild(0).toString().toUpperCase();
        TerminalNode a = null;
        if(ctx.expr() != null)
            a = visit(ctx.expr());
        else
            a = new TerminalNodeImpl(new CommonToken(SimpLParser.LITERAL, "ASSIGN"));
        CommonToken token = null;
        if(a != null) token = new CommonToken(a.getSymbol());
        Value val = null;
        Variable var = null;
        if (ctx.ASSIGN() != null)
        {
            val = getOperandValue(token);
            var = new Variable(name, val, val.getType());
            String valType = var.getCast();
            // Type checking here. if miss match, throw error in else statement
            if (!varType.equals(valType))
                ErrorMessages.throwError(ctx, "type doesn't match. you are trying to assign " + valType + " to " + varType);
            text.add(CodeEmitter.declareVariable(var, localCount));
            memory.get(memoryLevel).put(name, var);
        }
        else
        {
            String type = ctx.TYPE().getSymbol().getText();
            memory.get(memoryLevel).put(name, new Variable(name, ValueBuilder.getValue(type), type, localCount)); // add typing regardless of assignment or not
            localCount++;
        }
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
        String identifier = ctx.NAME().getSymbol().getText();
        TerminalNode a = visit(ctx.expr());
        if(a != null)
        {
            Value val = getOperandValue(a.getSymbol());

            int parserType = getParseType(val);

            incStackSize(2);
            if (memory.get(memoryLevel).get(identifier) == null)
            {
                System.err.println("line " + ctx.getStart().getLine() + ": " + "Undeclared Identifier");
                for (int i = 0; i < ctx.getChildCount(); i++)
                    System.err.print(ctx.getChild(i).getText() + " ");
                System.err.println();
            }
            Variable var = (Variable) memory.get(memoryLevel).get(identifier);
            if (var.getCast().equals(val.getType()))
                var.setValue(val);
            else
            {
                ErrorMessages.throwError(ctx, "Improper cast! you are trying to assign " + val.getType() + " to " + var.getCast());
            }

            memory.get(memoryLevel).put(identifier, var);
            text.add(CodeEmitter.assignVariable(var));
            decStackSize(2);
            return new TerminalNodeImpl(new CommonToken(parserType, getNodeField(val)));
        }
        else return new TerminalNodeImpl(new CommonToken(SimpLParser.LITERAL, "NULL"));
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
    @Override public TerminalNode visitConditional(SimpLParser.ConditionalContext ctx)
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
            if(text.get(text.size() - 1).contains("return"))
            {
                text.remove(text.size() - 1);
                text.remove(text.size() - 1);
            }
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
            if(text.get(text.size() - 1).contains("return"))
            {
                text.remove(text.size() - 1);
                text.remove(text.size() - 1);
            }
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
    @Override public TerminalNode visitFunc_def(SimpLParser.Func_defContext ctx)
    {
        int tmpLocals = localCount;
        localCount = 1;
        funcType = ctx.getChild(0).getText().toUpperCase(); // store function type
        FuncInfo f = new FuncInfo();


        f.setFuncType(funcType);
        memory.add(new HashMap<String,Value>());
        memoryLevel++;
        List<TerminalNode> typeNodes = ctx.TYPE(); // first element is the return type
        List<TerminalNode> nameNodes = ctx.NAME();
        for(int i = 1; i < typeNodes.size(); i++)
            f.addParamType(ctx.TYPE(i).getText());
        f.setNumOfParam(typeNodes.size() - 1);
        String returnType = null;
        String name = ctx.NAME().get(0).toString();
        f.setFuncName(name);
        funcList.add(f);
        List<String> operandTypes = new ArrayList<String>();
        List<String> operandNames = new ArrayList<String>();
        String functionName = null;

        for (int x = 0; x < typeNodes.size(); x++)
        {
            if (x == 0) returnType = typeNodes.get(x).getSymbol().getText().toUpperCase();
            else operandTypes.add(typeNodes.get(x).getSymbol().getText().toUpperCase());
        }

        for (int x = 1; x < nameNodes.size(); x++)
            operandNames.add(nameNodes.get(x).getSymbol().getText());
        for (int x = 0; x < operandNames.size(); x++)
            memory.get(memoryLevel).put(operandNames.get(x),
                new Variable(operandNames.get(x), ValueBuilder.getValue(operandTypes.get(x)), operandTypes.get(x), localCount + x - 1));
        String declaration = CodeEmitter.functionDeclaration(name, operandTypes, returnType);

        functions.put(name, new Function(name, CodeEmitter.functionCall(name, operandTypes, returnType), returnType));
        localCount += operandNames.size();

        int prevDec = text.size();
        text.add(declaration);
        text.add(CodeEmitter.setLocals(15));
        text.add(CodeEmitter.setStack(15));
        visit(ctx.block());
        text.add(CodeEmitter.createReturn(returnType.toUpperCase()));
        text.add(CodeEmitter.endMethod());
        int postDec = text.size();

        StringBuilder functionDeclaration = new StringBuilder();
        for (int x = 0; x < postDec - prevDec; x++) functionDeclaration.append(text.get(prevDec + x)).append("\n");
        for (int x = 0; x < postDec - prevDec; x++) text.remove(prevDec);
        text.add(functionInsertLine, functionDeclaration.toString());
        stackSizeLine++;

        // create function reference in functions table
        functions.put(name, new Function(name, CodeEmitter.functionCall(name, operandTypes, returnType), returnType));
        memory.remove(memoryLevel);
        memoryLevel--;
        localCount = tmpLocals;
        return new TerminalNodeImpl(new CommonToken(SimpLParser.NUMBER, "1"));
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public TerminalNode visitBlock(SimpLParser.BlockContext ctx)
    {
        // don't execute expr yet. this would be the return statement
        List<SimpLParser.StatContext> stmts = ctx.stat();

        for (SimpLParser.StatContext stmt : stmts)
            visit(stmt);
        if(ctx.expr() != null)
        {
            Value val = getOperandValue(visit(ctx.expr()).getSymbol());

            for(int i = 0; i < ctx.getChildCount(); i++)
                if(ctx.getChild(i).getText().equals("return"))
                    if(funcType.equals("VOID"))
                    {
                        ErrorMessages.throwError(ctx, "Should not have return in Void function");
                    }
                    else if(!val.getType().equals(funcType))
                    {
                        ErrorMessages.throwError(ctx, "Returning different type. Expecting " + funcType + " to return " + val.getType());
                    }
        }

        //funcType = ""; // reset function typeS
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
                val = ValueBuilder.getValue(node.getSymbol(), memory, memoryLevel);
                text.add(name.equals("print") ? CodeEmitter.print(val.getType().toUpperCase()) : CodeEmitter.println(val.getType().toUpperCase())); // check if print or println
            }
        }
        else
        {
            ArrayList<String> typesInParam = new ArrayList<String>();
            for (SimpLParser.ExprContext expression : expressions)
            {
                String type = expression.getChild(0).getText();
                if (type.matches("\\d(\\.)?\\d*"))
                    typesInParam.add("NUMBER");
                else if (type.matches("False | True"))
                    typesInParam.add("BOOLEAN");
                else if (type.matches("\'\\w\'"))
                    typesInParam.add("TEXT");
                else if (type.matches("\\w"))
                    typesInParam.add("IDENTIFIER");
            }

            //expressions.get(0).getChild(0).getText();
            boolean nameFound = false;
            boolean paramNumMatch = false;
            boolean paramTypeMatch = true;
            ArrayList<String> info = null;
            for (FuncInfo aFuncList : funcList)
            {
                if (name.equals(aFuncList.getFuncName()))
                {
                    nameFound = true;
                    if (expressions.size() == aFuncList.getNumOfParam())
                    {
                        paramNumMatch = true;
                        int j = 0;
                        while (paramTypeMatch && j < aFuncList.getParamTypeInfo().size())
                        {
                            info = aFuncList.getParamTypeInfo();
                            if (info != null)
                                try
                                {
                                    if (!info.get(j).equals(typesInParam.get(j)) && !typesInParam.get(j).equals("IDENTIFIER"))
                                        paramTypeMatch = false;
                                }
                                catch (Exception e) {}
                            j++;
                        }
                    }
                }
            }

            if(!nameFound)
            {
                ErrorMessages.throwError(ctx, "Function name \'" + name + "\' is not defined.");
            }
            else if(!paramNumMatch)
            {
                ErrorMessages.throwError(ctx, "Number of parameter does not match.");
            }

            for (SimpLParser.ExprContext exp : expressions)
            {
                node = visit(exp);
                val = ValueBuilder.getValue(node.getSymbol(), memory);
            }
            text.add(CodeEmitter.functionCall(name, functions));
        }
        //node = new TerminalNodeImpl(new CommonToken(SimpLParser.LITERAL, "TEST"));
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
        Value value = null;
        if(token == null)
            value = new Number(0);
        else
            value = ValueBuilder.getValue(token, memory.get(memoryLevel));
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
        if (type.equals("NUMBER")) return SimpLParser.NUMBER;
        else if (type.equals("TEXT")) return SimpLParser.TEXT;
        else if (type.equals("BOOLEAN")) return SimpLParser.BOOLEAN;
        // todo: else assume text?
        return SimpLParser.TEXT;
    }

    private int getParseType(Value type)
    {
        return getParseType(getOperandValue(type).getType());
    }

    private String getExprCtxType(SimpLParser.ExprContext ctx)
    {
        if(ctx.func_call() != null)
            return "FUNCTION_CALL";
        else if (ctx.NAME() != null)
            return "IDENTIFIER";
        else if (ctx.LITERAL() != null)
            return "LITERAL";
            //else if () return "BOOL OPERATION";
        else if (ctx.ADD() != null || ctx.SUB() != null ||
                ctx.DIV() != null || ctx.MUL() != null || ctx.POW() != null)
            return "ARITHMETIC";
        else if (ctx.AND() != null || ctx.OR() != null  || ctx.GT() != null || ctx.LT() != null ||
                ctx.GTE() != null || ctx.LTE() != null ||
                ctx.EQ()  != null || ctx.NEQ() != null)
            return "BOOL OPERATION";
        return "TEST";
    }

    private String getCtxOperation(SimpLParser.ExprContext ctx)
    {
        if(ctx == null)
            return "ERROR";
        String [] operations = {"ADD", "SUB", "POW", "MUL", "DIV", "AND", "OR", "GT", "LT", "GTE", "LTE", "EQ", "NEQ"};
        List<Supplier<TerminalNode>> ctxFunctions = Arrays.asList(
                ctx::ADD, ctx::SUB, ctx::POW, ctx::MUL, ctx::DIV,
                ctx::AND, ctx::OR, ctx::GT, ctx::LT, ctx::GTE, ctx::LTE, ctx::EQ, ctx::NEQ
        );
        for (int x = 0; x < operations.length; x++)
            if (ctxFunctions.get(x).get() != null)
                return operations[x];
        return "ERROR";
    }

    private TerminalNode processExprContext(SimpLParser.ExprContext ctx)
    {
        // Terribly written, we should come back and review this. Just trying to get some working code in
        // would be easy to change grammar to encompass symbols by category. e.g. POW, NUL, DIV .. belong to arithmetic_operators
        TerminalNodeImpl node = null;
        if (getExprCtxType(ctx).equals("IDENTIFIER"))
        {
            Value val = memory.get(memoryLevel).get(ctx.NAME().getSymbol().getText()); // if undeclared throw error
            if (val == null)  // todo: throw error, value shouldn't be null
                return new TerminalNodeImpl(new CommonToken(SimpLParser.BOOLEAN, "true"));
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
                if(ctx.func_call() == null) System.out.println(visit(ctx.expr(0)));
                else return visit(ctx.func_call());
                return new TerminalNodeImpl(new CommonToken(SimpLParser.BOOLEAN, "true"));
            }
            String operation = getCtxOperation(ctx);

            if(operation.equals("ERROR"))
            {
                System.out.println("terminal error occured");
            }
            if (getExprCtxType(ctx).equals("ARITHMETIC"))
            {
                if (!(loperand.getType().equals("NUMBER") && roperand.getType().equals("NUMBER")))
                    ErrorMessages.throwError(ctx, "Invalid operand(s)");
                Number result = performOperation((Number) loperand, (Number) roperand, operation);
                Supplier<String> supFunction = codeEmissionMap.get(operation);
                if (supFunction == null)
                    ;  // maybe throw error here in future
                else text.add(supFunction.get());
                node = new TerminalNodeImpl(new CommonToken(getParseType(result), getNodeField(result)));
                decStackSize(1);
            }
            else if (getExprCtxType(ctx).equals("BOOL OPERATION"))
            {
                if ((operation.equals("EQ") || operation.equals("NEQ")) &&
                        (!(loperand.getType().equals(roperand.getType()))) &&
                        (!(loperand.getType().equals("IDENTIFIER")) || (roperand.getType().equals("IDENTIFIER"))))
                    ErrorMessages.throwError(ctx, "Invalid Comparision.");

                if (loperand.getType().equals("IDENTIFIER"))
                    text.add(CodeEmitter.putVarStack((Variable)loperand));
                else if (loperand.getType().equals("TEXT") && !operation.equals("EQ") && !operation.equals("NEQ"))
                    ErrorMessages.throwError(ctx, "There is TEXT on left operand. TEXT is not allowed at BOOLEAN expr.");

                if (roperand.getType().equals("IDENTIFIER"))
                    text.add(CodeEmitter.putVarStack((Variable)roperand));
                else if (loperand.getType().equals("TEXT") && !operation.equals("EQ") && !operation.equals("NEQ"))
                    ErrorMessages.throwError(ctx, "There is TEXT on right operand. TEXT is not allowed at BOOLEAN expr.");
                text.add(CodeEmitter.booleanOperation(operation.toLowerCase()));
                node = new TerminalNodeImpl(new CommonToken(SimpLParser.BOOLEAN, operation));
            }
        }
        return node;
    }
    private String getNodeField(Value nodeVal)
    {
        Value value = getOperandValue(nodeVal);
        switch (getOperandValue(nodeVal).getType())
        {
            case "NUMBER":  return Double.toString((double) value.getValue());
            case "TEXT":    return value.getValue().toString();
            case "BOOLEAN": return String.valueOf(value.getValue());
            default: return null;
        }
    }
    private static Number performOperation(Number a, Number b, String operation)
    {
        switch (operation)
        {
            case "ADD": return new Number(a.getValue() + b.getValue());
            case "SUB": return new Number(a.getValue() - b.getValue());
            case "MUL": return new Number(a.getValue() * b.getValue());
            case "DIV": return new Number(a.getValue() / b.getValue());
            case "POW": return new Number(java.lang.Math.pow(a.getValue(), b.getValue()));
            default: return null;
        }
    }
}
