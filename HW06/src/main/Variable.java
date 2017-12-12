package main;

/** Used for variables. Used to fetch the values stored in the map. */
public class Variable implements Value<Value>
{
    private final String cast;
    private String identifier;
    private Value value;
    private int slotNumber;

    public Variable(String identifier, Value value, String cast)
    {
        this.cast = cast;
        this.value = value;
        this.identifier = identifier;
        this.slotNumber = -1;
    }

    public Variable(String identifier, Value value, String cast, int slotNumber)
    {
        this.cast = cast;
        this.value = value;
        this.identifier = identifier;
        this.slotNumber = slotNumber;
    }

    public void setValue(Value value)
    {
        this.value.setValue(value.getValue());
    }
    public void setSlot(int slotNumber)
    {
        this.slotNumber = slotNumber;
    }
    
    public Value getValue()
    {
        return value;
    }
    public String getID()
    {
        return identifier;
    }
    public String getType()
    {
        return "IDENTIFIER";
    }
    public String getCast()
    {
        return cast;
    }
    public int getSlot()
    {
        return slotNumber;
    }
}
