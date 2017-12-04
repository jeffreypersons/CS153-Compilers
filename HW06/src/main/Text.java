package main;

public class Text implements Value<String>
{
	String value;

	public Text()
	{
		value = "";
	}
	public Text(String in)
	{	
		if(in.length() == 0) in = "";
		else if(in.charAt(0) == '\'') in = in.substring(1, in.length() - 1);// strip first and last one
		value = in;
	}

	public void setValue(String in)
	{
		if(in.length() == 0) in = "";
		else if(in.charAt(0) == '\'') in = in.substring(1, in.length() - 1);
		value = in;
	}
	public String getType()
	{
		return "TEXT";
	}
	public String getValue()
	{
		return value;
	}
}
