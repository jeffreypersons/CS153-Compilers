package main;

public class Text implements Value<String>
{
    private String value;

    public Text()
    {
        value = "";
    }
    public Text(String value)
    {
        setValue(value);
    }

    public void setValue(String value)
    {
        if (value.length() == 0)
            this.value = "";
        else if (value.charAt(0) == '\'')
            this.value = value.substring(1, value.length() - 1);  // strip first and last one
        else
            // todo: add error handling?!
            this.value = value;
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
