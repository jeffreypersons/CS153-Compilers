package main;


/** Used for variables. Used to fetch the values stored in the map. */
public class Variable implements Value<Value>
{
    final String cast;
    String identifier;
    Value a;
    int slot_number;

    public Variable(String identifier, Value value, String cast)
    {
        this.cast = cast;
        this.a = value;
        this.identifier = identifier;
        this.slot_number = -1;
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
    public String getCast()
    {
        return this.cast;
    }
    public void setSlot(int slot)
    {
        this.slot_number = slot;
    }
    public int getSlot()
    {
        return this.slot_number;
    }
}