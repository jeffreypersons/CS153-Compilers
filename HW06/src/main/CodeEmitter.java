package main;

import java.util.Map;
import java.util.HashMap;


public class CodeEmitter
{
	private static final String SET_NAME = ".class public ";
	private static final String INIT = ".super java/lang/Object\n";
	private static final String MAIN = "\n.method public static main([Ljava/lang/String;)V\n";
	private static final String START_FUNCTION = "\n.method public static ";
	private static final String END_FUNCTION = ".end method\n";
	private static final String STACK_SIZE = ".limit stack ";
	public  static String program_name = "a";
	private static Map<String, String> boolean_operations, if_operations;
	private static Map<String, Map<String,String>> type_ops;
	private static Boolean isInitialized = false;

	public static String program(String name)
	{
		CodeEmitter.program_name = name;
		StringBuilder construct = new StringBuilder(SET_NAME);
		return construct.append(name).append("\n").append(INIT).toString();
	}
	public static String main()
	{
		return MAIN;
	}

	public static String setStack(int limit)
	{
		StringBuilder construct = new StringBuilder(STACK_SIZE);
		return construct.append(limit + "\n").toString();
	}
	public static String setlocals(int locals)
	{
		StringBuilder construct = new StringBuilder(".limit locals " + locals + "\n");
		return construct.toString();
	}

	public static String add()
	{
		//StringBuilder construct = new StringBuilder("ldc " + a).append("\nldc " + b);
		StringBuilder construct = new StringBuilder("");
		construct.append("invokestatic " + CodeEmitter.program_name + "/add(FF)F\n");
		return construct.toString();
	}
	public static String sub()
	{
		StringBuilder construct = new StringBuilder("");
		construct.append("invokestatic " + CodeEmitter.program_name + "/sub(FF)F\n");
		return construct.toString();
	}
	public static String mul()
	{
		StringBuilder construct = new StringBuilder("");
		construct.append("invokestatic " + CodeEmitter.program_name + "/mul(FF)F\n");
		return construct.toString();
	}
	public static String div()
	{
		StringBuilder construct = new StringBuilder("");
		construct.append("invokestatic " + CodeEmitter.program_name + "/div(FF)F\n");
		return construct.toString();
	}

	public static String printi(String in)
	{
		StringBuilder construct = new StringBuilder("getstatic java/lang/System/out Ljava/io/PrintStream;\n");
		return construct.append("ldc " + "\"" + in + "\"\n").append("invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V\nreturn\n").toString();
	}
	public static String read(String type)
	{
		StringBuilder construct = new StringBuilder("getstatic java/lang/System/in Ljava/io/InputStream;\n");
		return construct.append("invokevirtual java/io/InputStream/read()").append(type).append("\n").toString();
	}
	public static String endMethod()
	{
		return END_FUNCTION;
	}
	private static void shortCutInstruction(String instruction)
	{
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
			construct.append(setlocals(2));
			construct.append("fload_0\n");
			construct.append("fload_1\n");
			construct.append("fmul\n");
			construct.append("freturn\n");
			construct.append(END_FUNCTION);

			// create add code
			construct.append(START_FUNCTION + "add(FF)F\n");
			construct.append(setStack(2));
			construct.append(setlocals(2));
			construct.append("fload_0\n");
			construct.append("fload_1\n");
			construct.append("fadd\n");
			construct.append("freturn\n");
			construct.append(END_FUNCTION);

			// create div code
			construct.append(START_FUNCTION + "div(FF)F\n");
			construct.append(setStack(2));
			construct.append(setlocals(2));
			construct.append("fload_0\n");
			construct.append("fload_1\n");
			construct.append("fdiv\n");
			construct.append("freturn\n");
			construct.append(END_FUNCTION);

			// create sub code
			construct.append(START_FUNCTION + "sub(FF)F\n");
			construct.append(setStack(2));
			construct.append(setlocals(2));
			construct.append("fload_0\n");
			construct.append("fload_1\n");
			construct.append("fsub\n");
			construct.append("freturn\n");
			construct.append(END_FUNCTION);
		}
		return construct.toString();
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
		construct.append(assignVariable(var) + "\n");
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
		    ;  //throw error here. Undeclared variable
		Value val = var.getValue();
		String store_type = "";
		store_type = type_ops.get(val.getType()).get("store");
		return store_type + var.getSlot();
	}
	/**
	 * Put a constant string onto the stack
	 * @param  in [string to put onto stack]
	 * @return    [assembly code to put string on stack]
	 */
	public static String loadConstant(String in)
	{
		return "ldc " + "\"" + in + "\"";
	}
	public static String loadConstant(Boolean a)
	{
		if (a)
		{
			return "ldc " + 1;
		}
		else
		    return "ldc " + -1;
	}
	/**
	 * Load constant onto stack
	 * @param  in number to put onto stack
	 * @return    assembly code to push requested number onto stack
	 */
	public static String loadConstant(double in)
	{
		return "ldc " + in;
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
        load_type = type_ops.get(val.getType()).get("load");
        return load_type + a.getSlot();
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
	public static String booleanOperation(String type)
	{
		String result = boolean_operations.get(type.toUpperCase());
		if (result == null)
		    result = "";
		return result;
	}
	public static String ifOperation(String type, String label)
	{
		String compare_type = "";
		type = type.toUpperCase();
		compare_type = if_operations.get(type);
		if (compare_type == null)
		    compare_type = "iflt";
		return compare_type + " " + label.replaceAll(":", "");
	}

	public static void initialize()
	{
		// construct hashmaps for all boolean and if operations
		if (isInitialized)
		    return;

		if_operations = new HashMap<String, String>();
		boolean_operations = new HashMap<String, String>();
		type_ops = new HashMap<String, Map<String,String>>();

        String[] types          = {"NUMBER", "TEXT", "BOOLEAN"};
        String[] load_commands  = {"fload ", "aload ", "iload "};
        String[] store_commands = {"fstore ", "astore ", "istore "};
        String[] command_types  = {"store", "load"};
		String[] ops            = {"GT", "LT", "GTE", "LTE", "EQ", "NEQ", "NOT", "OR", "AND"};
		String[] if_ops         = {"ifgt", "iflt", "ifgt", "iflt", "ifne", "ifeq", "iconst_1\nisub\niflt"};
		String[] bool_ops       = {"swap\nfcmpg\niconst_1\niadd", "swap\nfcmpg\niconst_1\nisub", "fcmpg",
                                   "swap\nfcmpg", "fcmpg", "fcmpg", "ineg", "ior", "iand"};

		Map<String, String> loads;
		for (int x = 0; x < if_ops.length; x++)
			if_operations.put(ops[x], if_ops[x]);
		for (int x = 0; x < bool_ops.length; x++)
			boolean_operations.put(ops[x], bool_ops[x]);
		for (int x = 0; x < types.length; x++)
		{
			loads = new HashMap<String, String>();
			loads.put(command_types[0], store_commands[x]);
			loads.put(command_types[1], load_commands[x]);

			type_ops.put(types[x], loads);
		}
		isInitialized = true;
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
}
