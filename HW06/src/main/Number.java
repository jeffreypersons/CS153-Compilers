package main;

/**
 * Wrap
 * per used to store numeric values
 */
public class Number implements Value<Double>
{
	double value;
	
	public Number(double in)
	{
		this.value = in;
	}

	public void setValue(Double in)
	{
		this.value = in;
	}

	public Double getValue()
	{
		return this.value;
	}

	public String getType()
	{
		return "NUMBER";
	}
}