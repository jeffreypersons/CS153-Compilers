package main;

public class Function implements Value<String>
{
	private String byteCode, name, returnType;

	Function(String name, String byteCode, String returnType)
	{
		this.byteCode = byteCode;
		this.returnType = returnType;
		this.name = name;
	}

	public String getValue()
	{
		return this.byteCode;
	}

	public void setValue(String in)
	{
		this.byteCode = in;
	}

	public String getType()
	{
		return "FUNCTION";
	}

	public String getReturnType()
	{
		return returnType;
	}
}