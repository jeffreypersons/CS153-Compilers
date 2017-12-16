package main;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class CodeEmitter
{
    private static final String SET_NAME = ".class public ";
    private static final String INIT = ".super java/lang/Object\n";
    private static final String MAIN = "\n.method public static main([Ljava/lang/String;)V\n";
    private static final String START_FUNCTION = "\n.method public static ";
    private static final String END_FUNCTION = ".end method\n";
    private static final String STACK_SIZE = ".limit stack ";
    private static String programName = "a";
    private static Map<String, String> booleanOperations, ifOperations;
    private static Map<String, Map<String,String>> typeOps;
    private static Map<String, String> valueTypeToBC; // for converting value into bytecode equivalent. e.g. Number to F
    private static Boolean isInitialized = false;

    public static void initialize()
    {
        // construct hashmaps for all boolean and if operations
        if (isInitialized)
            return;

        ifOperations = new HashMap<String, String>();
        booleanOperations = new HashMap<String, String>();
        typeOps = new HashMap<String, Map<String,String>>();
        valueTypeToBC = new HashMap<String, String>();
        String[] types          = {"NUMBER", "TEXT", "BOOLEAN"};
        String[] load_commands  = {"fload ", "aload ", "iload "};
        String[] store_commands = {"fstore ", "astore ", "istore "};
        String[] command_types  = {"store", "load"};
        String[] ops            = {"GT", "LT", "GTE", "LTE", "EQ", "NEQ", "NOT", "OR", "AND"};
        String[] if_ops         = {"ifgt", "iflt", "ifgt", "iflt", "ifne", "ifeq", "iconst_1\nisub\niflt"};
        String[] bool_ops       = {"swap\nfcmpg\niconst_1\niadd", "swap\nfcmpg\niconst_1\nisub", "fcmpg",
                                   "swap\nfcmpg", "fcmpg", "fcmpg", "ineg", "ior", "iand"};
        String[] byteCodeTypes = {"F", "Ljava/lang/String;", "I"}; // I for bool?

        Map<String, String> loads;
        for (int x = 0; x < if_ops.length; x++)
            ifOperations.put(ops[x], if_ops[x]);
        for (int x = 0; x < bool_ops.length; x++)
            booleanOperations.put(ops[x], bool_ops[x]);
        for (int x = 0; x < types.length; x++)
        {
            loads = new HashMap<String, String>();
            loads.put(command_types[0], store_commands[x]);
            loads.put(command_types[1], load_commands[x]);

            typeOps.put(types[x], loads);

            valueTypeToBC.put(types[x], byteCodeTypes[x]);
        }
        isInitialized = true;
    }

    public static String program(String name)
    {
        CodeEmitter.programName = name;
        return SET_NAME + name + "\n" + INIT;
    }
    public static String main()
    {
        return MAIN;
    }

    public static String setStack(int limit)
    {
        return STACK_SIZE + limit + "\n";
    }
    public static String setLocals(int locals)
    {
        return ".limit locals " + locals + "\n";
    }

    public static String add()
    {
        return "invokestatic " + CodeEmitter.programName + "/add(FF)F\n";
    }
    public static String sub()
    {
        return "invokestatic " + CodeEmitter.programName + "/sub(FF)F\n";
    }
    public static String mul()
    {
        return "invokestatic " + CodeEmitter.programName + "/mul(FF)F\n";
    }
    public static String div()
    {
        return "invokestatic " + CodeEmitter.programName + "/div(FF)F\n";
    }

    public static String pow()
    {
        return "invokestatic " + CodeEmitter.programName + "/pow(FF)F\n";
    }

    // todo: change to input()?, and add string parameter as the prompt message like python...?!
    public static String read(String type)
    {
        return "getstatic java/lang/System/in Ljava/io/InputStream;\n" +
               "invokevirtual java/io/InputStream/read()" + type + "\n";
    }
    public static String endMethod()
    {
        return END_FUNCTION;
    }
    private static void shortCutInstruction(String instruction)
    {
    }
    /**
     * Declares a variable using a slot number
     * @param  var [variable that will be assigned]
     * @param  num [slot number]
     * @return     [assembly code for that slot]
     */
    public static String declareVariable(Variable var, int num)
    {
        //StringBuilder construct = new StringBuilder("ldc " + value.getValue().getValue() + "\n");
        StringBuilder construct = new StringBuilder("");
        var.setSlot(num);
        construct.append(assignVariable(var)).append("\n");
        return construct.toString();
    }
    /**
     * Creates assembly code for assigning either a string or a number to a variable
     * @param  var [variable that will be assigned new value]
     * @return     [assembly that will cause new value put into that slot or variable]
     */
    public static String assignVariable(Variable var)
    {
        if (var.getSlot() < 0)
            ;  // todo: throw error here. Undeclared variable
        Value val = var.getValue();
        String storeType = typeOps.get(val.getType()).get("store");
        return storeType + var.getSlot();
    }
    /**
     * Put a constant string onto the stack
     * @param constant [string to put onto stack]
     * @return         [assembly code to put string on stack]
     */
    public static String loadConstant(String constant)
    {
        return "ldc " + "\"" + constant + "\"";
    }
    public static String loadConstant(Boolean constant)
    {
        return constant? "ldc 1" : "ldc -1";
    }
    /**
     * Load constant onto stack
     * @param  constant number to put onto stack
     * @return          assembly code to push requested number onto stack
     */
    public static String loadConstant(double constant)
    {
        return "ldc " + constant;
    }

    /**
     * If number used fstore else use astore assuming address to string.
     * Can change type to array in the future
     */
    public static String putVarStack(Variable a)
    {
        // no checking
        Value val = a.getValue();
        String load_type = "";
        load_type = typeOps.get(val.getType()).get("load");
        return load_type + a.getSlot();
    }
    public static String booleanOperation(String type)
    {
        String result = booleanOperations.get(type.toUpperCase());
        if (result == null)
            result = "";
        return result;
    }
    public static String ifOperation(String type, String label)
    {
        String compareType = ifOperations.get(type.toUpperCase());
        return compareType == null? "iflt" : compareType + " " + label.replaceAll(":", "");
    }

    public static String getLabel(int num)
    {
        return "LABEL_" + num + ":";
    }
    public static String getCondLabel(int num)
    {
        return "COND_" + num + ":";
    }
    public static String getGoTo(String label)
    {
        return "goto " + label.replaceAll(":", "");
    }

    public static String print(String type)
    {
        StringBuilder construct = new StringBuilder("");
        if (type.equals("TEXT"))
        {
            construct.append("getstatic java/lang/System/out Ljava/io/PrintStream;\n");
            construct.append("swap\n");
            construct.append("invokevirtual java/io/PrintStream/print(Ljava/lang/String;)V\n");
        }
        else if (type.equals("NUMBER"))
        {
            construct.append("getstatic java/lang/System/out Ljava/io/PrintStream;\n");
            construct.append("swap\n");
            construct.append("invokevirtual java/io/PrintStream/print(F)V\n");
        }
        else if (type.equals("BOOLEAN"))
        {
            construct.append("getstatic java/lang/System/out Ljava/io/PrintStream;\n");
            construct.append("swap\n");
            construct.append("invokevirtual java/io/PrintStream/print(I)V\n");
        }
        return construct.toString();
    }
    public static String printi(String in)
    {
        return "getstatic java/lang/System/out Ljava/io/PrintStream;\n" +
                "ldc \"" + in + "\"\n" +
                "invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V\n" +
                "return\n";
    }
    /**
     * Create print command based on the type that is being passed into the function
     * @param  type String description that matches possible types. e.g. NUMBER or TEXT
     * @return      Return the appropriate println command in jasmin assembly code
     */
    public static String println(String type)
    {
        StringBuilder construct = new StringBuilder("getstatic java/lang/System/out Ljava/io/PrintStream;\ndup\n");
        construct.append("ldc \" \"\ninvokevirtual java/io/PrintStream/println(Ljava/lang/String;)V\n");
        if (type.equals("TEXT"))
        {
            //construct.append();
            construct.append("swap\n");
            construct.append("invokevirtual java/io/PrintStream/print(Ljava/lang/String;)V\n");
        }
        else if (type.equals("NUMBER"))
        {
            //construct.append("getstatic java/lang/System/out Ljava/io/PrintStream;\n");
            construct.append("swap\n");
            construct.append("invokevirtual java/io/PrintStream/print(F)V\n");
        }
        else if (type.equals("BOOLEAN"))
        {
            //construct.append("getstatic java/lang/System/out Ljava/io/PrintStream;\n");
            construct.append("swap\n");
            construct.append("invokevirtual java/io/PrintStream/print(I)V\n");
        }
        return construct.toString();
    }

    /**
     * Prints basic operations in assembly code. In the future create class and invoke virtual from inside assembly.
     * Only temporary
     * @param  lib_name [which library to print the assembly code]
     * @return          [the library as text in assembly]
     */
    public static String getLibraryCode(String lib_name)
    {
        StringBuilder construct = new StringBuilder("");
        if (lib_name.equals("math"))
        {
            // create multiply code
            construct.append(START_FUNCTION + "mul(FF)F\n");
            construct.append(setStack(2));
            construct.append(setLocals(2));
            construct.append("fload_0\n");
            construct.append("fload_1\n");
            construct.append("fmul\n");
            construct.append("freturn\n");
            construct.append(END_FUNCTION);

            // create add code
            construct.append(START_FUNCTION + "add(FF)F\n");
            construct.append(setStack(2));
            construct.append(setLocals(2));
            construct.append("fload_0\n");
            construct.append("fload_1\n");
            construct.append("fadd\n");
            construct.append("freturn\n");
            construct.append(END_FUNCTION);

            // create div code
            construct.append(START_FUNCTION + "div(FF)F\n");
            construct.append(setStack(2));
            construct.append(setLocals(2));
            construct.append("fload_0\n");
            construct.append("fload_1\n");
            construct.append("fdiv\n");
            construct.append("freturn\n");
            construct.append(END_FUNCTION);

            // create sub code
            construct.append(START_FUNCTION + "sub(FF)F\n");
            construct.append(setStack(2));
            construct.append(setLocals(2));
            construct.append("fload_0\n");
            construct.append("fload_1\n");
            construct.append("fsub\n");
            construct.append("freturn\n");
            construct.append(END_FUNCTION);

            // create pow code
            construct.append(START_FUNCTION + "pow(FF)F\n");
            construct.append(setStack(4));
            construct.append(setLocals(3));
            
            construct.append("ldc 1.0\nfstore_2\n");
            construct.append("ldc 1.0\n");

            construct.append("LOOP:\n");
            construct.append("ldc 0.0\nfload_1\n");
            construct.append("fcmpg\n");
            construct.append("ifeq END_LOOP\n");

            construct.append("fload_2\nfload_0\n" + CodeEmitter.mul());
            construct.append("fstore_2\n");

            construct.append("fload_1\nldc 1.0\n");
            construct.append(CodeEmitter.sub() + "\nfstore_1\n");
            construct.append("goto LOOP\n");
            construct.append("END_LOOP:\n");
            construct.append("pop\nfload_2\n");
            construct.append("freturn\n");
            construct.append(END_FUNCTION);
        }
        return construct.toString();
    }

    public static String loadConstant(Value val)
    {
        String output = null;
        Object value = val.getValue();
        if(val.getType().equals("NUMBER")) output = loadConstant((double) value);
        else if(val.getType().equals("BOOLEAN")) output = loadConstant((Boolean) value);
        else if(val.getType().equals("IDENTIFIER"))
        {
            output = putVarStack((Variable)val);
        }
        else output = loadConstant((String) value);
        return output;
    }

    public static String functionDeclaration(String name, List<String> operandTypes, String returnType)
    {
        return START_FUNCTION + functionCall(name, operandTypes, returnType);
    }

    public static String functionCall(String name, List<String> operandTypes, String returnType)
    {
        StringBuilder call = new StringBuilder(name + "(");
        String type = null;
        if(returnType.equals("")) returnType = "V";
        //if(operandTypes.size() == 0) call.append("V");
        for(String operandType : operandTypes)
        {
            type = valueTypeToBC.get(operandType.toUpperCase());
            call.append(type);
        }
        call.append(")" + valueTypeToBC.get(returnType.toUpperCase()));
        return call.toString();
    }

    public static String functionCall(String functionName, Map<String, Function> functionMap)
    {
        StringBuilder functionCall = new StringBuilder("invokestatic ");
        functionCall.append(programName + "/").append(functionMap.get(functionName).getValue());
        return functionCall.toString();
    }

    public static String createReturn(String type)
    {
        if(type.equals("TEXT")) return "areturn";
        else if(type.equals("NUMBER")) return "freturn";
        else if(type.equals("BOOLEAN")) return "ireturn";
        else return "return";
    }
}
