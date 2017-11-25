public class Text implements Value<String>
{
	String value;
	public Text(String in)
	{
		value = in;
	}

	public Text()
	{
		value = "";
	}

	public void setValue(String in)
	{
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