public class Bool implements Value<Boolean>
{
	Boolean value;

	public Bool(Boolean in)
	{
		this.value = value;
	}

	public Bool(String in)
	{
		if(in.equals("true"))
		{
			this.value = true;
		}
		else this.value = false;
	}

	public String getType()
	{
		return "BOOLEAN";
	}

	public void setValue(Boolean in)
	{
		this.value = in;
	}

	public void setValue(String in)
	{
		if(in.equals("true"))
		{
			this.value = true;
		}
		else this.value = false;
	}

	public Boolean getValue()
	{
		return this.value;
	}
}