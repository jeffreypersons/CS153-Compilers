package main;

public class Bool implements Value<Boolean>
{
    private Boolean value;

    public Bool(Boolean value)
    {
        this.value = value;
    }
    public Bool(String value)
    {
        this.value = value.equals("true");
    }
    public String getType()
    {
        return "BOOLEAN";
    }
    public void setValue(Boolean value)
    {
        this.value = value;
    }
    public void setValue(String value)
    {
        this.value = value.equals("true");
    }
    public Boolean getValue()
    {
        return this.value;
    }
    public void toggleValue()
    {
        value = !value;
    }
}
