package main;

import org.antlr.v4.runtime.Token;
import gen.*;
/**
 * Used to construct wrapped objects. Works properly for interpreter may not be needed for compilation
 */
public class ValueBuilder
{
	/**
	 * Takes in a token and a map and constructs the appropriate wrapper for it
	 * @param  token Tokent o be converted into value
	 * @param  map   map used to store identifiers
	 * @return       a Value created from the token and map
	 */
	public static Value getValue(Token token, java.util.Map<String, Value> map)
	{
		if(token.getType() == simpLParser.NAME)
		{
			// add check for cast to determine
			return map.get(token.getText());
		}
		if(token.getType() == simpLParser.NUMBER)
		{
			return new Number(Double.parseDouble(token.getText()));
		}
		else if(token.getType() == simpLParser.LITERAL)
		{
			// add checks for all possible literals
			Value val;
			try
			{
				val = new Number(Double.parseDouble(token.getText()));
			}catch (Exception e)
			{
				String text = token.getText().toString();
				if(text.equals("true") || text.equals("false"))
				{
					val = new Bool(text);
				}
				else val = new Text(text);
			}
			return val;
		}
		else if(token.getType() == simpLParser.BOOLEAN)
		{
			return new Bool(token.getText());
		}
		else if(token.getType() == simpLParser.TEXT)
		{
			return new Text(token.getText());
		}
		else
		{
			System.out.println("NO VALID CAST " + token.getType());
			return new Number(0);
		}
	}

	// add values for primities
	public static Value getValue(double in)
	{
		return new Number(in);
	}
}