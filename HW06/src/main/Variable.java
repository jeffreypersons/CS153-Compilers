/** Used for varaibles. Returns the value stored in the map */
public class Variable implements Value<Value>
{
	String identifier;
	Value a;
	public Variable(String identifier, Value value)
	{
		this.a = value;
		this.identifier = identifier;
	}

	public void setValue(Value value)
	{
		this.a.setValue(value.getValue());
	}

	public Value getValue()
	{
		return a;
	}

	public String getID()
	{
		return this.identifier;
	}

	public String getType()
	{
		return "IDENTIFIER";
	}
}