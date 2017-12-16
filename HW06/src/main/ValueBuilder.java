package main;

import java.util.Map;
import java.util.List;

import org.antlr.v4.runtime.Token;

import gen.SimpLParser;

/**
 * Used to construct wrapped objects. Works properly for interpreter may not be needed for compilation
 */
public class ValueBuilder
{
    /**
     * Takes in a token and a map and constructs the appropriate wrapper for it
     * @param  token Token to be converted into value
     * @param  map   map used to store identifiers
     * @return       a Value created from the token and map
     */
    public static Value getValue(Token token, Map<String, Value> map)
    {
        if (token.getType() == SimpLParser.NAME)
        {
            // todo: add check for cast to determine
            return map.get(token.getText());
        }
        if (token.getType() == SimpLParser.NUMBER)
        {
            return new Number(Double.parseDouble(token.getText()));
        }
        else if (token.getType() == SimpLParser.LITERAL)
        {
            // todo: add checks for all possible literals
            Value val;
            try
            {
                val = new Number(Double.parseDouble(token.getText()));
            }
            catch (Exception e)
            {
                String text = token.getText();
                if (text.equals("true") || text.equals("false"))
                    val = new Bool(text);
                else
                    val = new Text(text);
            }
            return val;
        }
        else if (token.getType() == SimpLParser.BOOLEAN)
        {
            return new Bool(token.getText());
        }
        else if (token.getType() == SimpLParser.TEXT)
        {
            return new Text(token.getText());
        }
        else
        {
            System.out.println("NO VALID CAST " + token.getType());
            return new Number(0);  // todo - do we really want this on error?...
        }
    }

    public static Value getValue(Token token, List<Map<String,Value>> mapList)
    {
        return getValue(token, mapList, 0); // pull from global list
    }

    public static Value getValue(Token token, List<Map<String,Value>> mapList, int level)
    {
        return getValue(token, mapList.get(level));
    }

    // todo: add values for primitives
    public static Value getValue(double value)
    {
        return new Number(value);
    }

    public static Value getValue(String type)
    {
        if(type.equals("NUMBER")) return new Number(0);
        else if(type.equals("TEXT")) return new Text("");
        else if(type.equals("BOOLEAN")) return new Bool(false);
        else return new Number(0); // should really throw error here
    }
}
