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

    public static Number performOperation(Number a, Number b, String operation)
    {
        Number result = null;
        if(operation.equals("ADD")) result = new Number(a.getValue() + b.getValue()); 
        else if(operation.equals("SUB")) result  = new Number(a.getValue() - b.getValue());
        else if(operation.equals("MUL")) result = new Number(a.getValue() * b.getValue());
        else if(operation.equals("DIV")) result = new Number(a.getValue() / b.getValue());
        else if(operation.equals("POW")) ;
        else return new Number(0);
        return result;
    }
}
