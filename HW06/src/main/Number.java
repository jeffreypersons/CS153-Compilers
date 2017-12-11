package main;

/**
 * Wrapper used to store numeric values
 */
public class Number implements Value<Double>
{
    private double value;

    public Number(double value)
    {
        this.value = value;
    }
    public void setValue(Double value)
    {
        this.value = value;
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
