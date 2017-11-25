public class CodeEmitter
{
	private static final String SET_NAME = ".class public ";
	private static final String INIT = ".super java/lang/Object\n";
	private static final String MAIN = "\n.method public static main([Ljava/lang/String;)V\n";
	private static final String START_FUNCTION = "\n.method public static ";
	private static final String END_FUNCTION = ".end method\n";
	private static final String STACK_SIZE = ".limit stack ";
	public static String program_name = "a";
	public static String Program(String name)
	{
		CodeEmitter.program_name = name;
		StringBuilder construct = new StringBuilder(SET_NAME);
		return construct.append(name).append("\n").append(INIT).toString();
	}

	public static String Main()
	{
		return MAIN;
	}

	public static String SetStack(int limit)
	{
		StringBuilder construct = new StringBuilder(STACK_SIZE);
		return construct.append(limit + "\n").toString();
	}

	public static String SetLocals(int locals)
	{
		StringBuilder construct = new StringBuilder(".limit locals " + locals + "\n");
		return construct.toString();
	}

	public static String Add()
	{
		//StringBuilder construct = new StringBuilder("ldc " + a).append("\nldc " + b);
		StringBuilder construct = new StringBuilder("");
		construct.append("invokestatic " + CodeEmitter.program_name + "/add(FF)F\n");
		return construct.toString();
	}

	public static String Sub()
	{
		StringBuilder construct = new StringBuilder("");
		construct.append("invokestatic " + CodeEmitter.program_name + "/sub(FF)F\n");
		return construct.toString();	
	}


	public static String Mul()
	{
		StringBuilder construct = new StringBuilder("");
		construct.append("invokestatic " + CodeEmitter.program_name + "/mul(FF)F\n");
		return construct.toString();	
	}

	public static String Div()
	{
		StringBuilder construct = new StringBuilder("");
		construct.append("invokestatic " + CodeEmitter.program_name + "/div(FF)F\n");
		return construct.toString();	
	}

	public static String Printi(String in)
	{
		StringBuilder construct = new StringBuilder("getstatic java/lang/System/out Ljava/io/PrintStream;\n");
		return construct.append("ldc " + "\"" + in + "\"\n").append("invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V\nreturn\n").toString();
	}

	public static String Read(String type)
	{
    	StringBuilder construct = new StringBuilder("getstatic java/lang/System/in Ljava/io/InputStream;\n");
    	return construct.append("invokevirtual java/io/InputStream/read()").append(type).append("\n").toString();
	}

	public static String EndMethod()
	{
		return END_FUNCTION;
	}

	private static void shortCutInstruction(String instruction)
	{
	}

	public static String GetLibraryCode(String lib_name)
	{
		StringBuilder construct = new StringBuilder("");
		if(lib_name.equals("math"))
		{
			// create multiply code
			construct.append(START_FUNCTION + "mul(FF)F\n");
    		construct.append(SetStack(2));
    		construct.append(SetLocals(2));
    		construct.append("fload_0\n");
    		construct.append("fload_1\n");
    		construct.append("fmul\n");
    		construct.append("freturn\n");
			construct.append(END_FUNCTION);

			// create add code
			construct.append(START_FUNCTION + "add(FF)F\n");
    		construct.append(SetStack(2));
    		construct.append(SetLocals(2));
    		construct.append("fload_0\n");
    		construct.append("fload_1\n");
    		construct.append("fadd\n");
    		construct.append("freturn\n");
			construct.append(END_FUNCTION);

			// create div code
			construct.append(START_FUNCTION + "div(FF)F\n");
    		construct.append(SetStack(2));
    		construct.append(SetLocals(2));
    		construct.append("fload_0\n");
    		construct.append("fload_1\n");
    		construct.append("fdiv\n");
    		construct.append("freturn\n");
			construct.append(END_FUNCTION);

			// create sub code
			construct.append(START_FUNCTION + "sub(FF)F\n");
    		construct.append(SetStack(2));
    		construct.append(SetLocals(2));
    		construct.append("fload_0\n");
    		construct.append("fload_1\n");
    		construct.append("fsub\n");
    		construct.append("freturn\n");
			construct.append(END_FUNCTION);
		}
		return construct.toString();
	}

	public static String DeclareVariable(Variable value, int num)
	{
		//StringBuilder construct = new StringBuilder("ldc " + value.getValue().getValue() + "\n");
		StringBuilder construct = new StringBuilder("");
		String store_type = "";
		if(value.getValue().getType().equals("NUMBER")) store_type = "fstore ";
		else store_type = "astore ";
		construct.append(store_type + num + "\n");
		value.setSlot(num);
		return construct.toString();
	}

	public static String AssignVariable(Variable value)
	{
		if(value.getSlot() < 0) ;//throw error here. Undeclared variable
		Value val = value.getValue();
		String store_type = "";
		if(val.getType().equals("NUMBER")) store_type = "fstore ";
		else store_type = "astore ";
		return store_type + value.getSlot();
	}	

	public static String LoadConstant(String in)
	{
		return "ldc " + "\"" + in + "\"";
	}

	public static String LoadConstant(double in)
	{
		return "ldc " + in;
	}

	public static String Print(String type)
	{
		StringBuilder construct = new StringBuilder("");
		if(type.equals("TEXT"))
		{
			construct.append("getstatic java/lang/System/out Ljava/io/PrintStream;\n");
			construct.append("swap\n");
			construct.append("invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V\n");
		}
		else if(type.equals("NUMBER"))
		{
			construct.append("getstatic java/lang/System/out Ljava/io/PrintStream;\n");
			construct.append("swap\n");
			construct.append("invokevirtual java/io/PrintStream/println(F)V\n"); // only prints number right now get type from evaluation node
		}
		return construct.toString();
	}

	public static String PutVarStack(Variable a)
	{
		// just for numbers no checking
		Value val = a.getValue();
		String load_type = "";
		if(val.getType().equals("NUMBER")) load_type = "fload ";
		else load_type = "aload ";
		return load_type + a.getSlot();
	}
}