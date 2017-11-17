import org.antlr.v4.runtime.Token;

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
			return new Variable(token.getText(), map.get(token.getText()));
		}
		if(token.getType() == simpLParser.NUMBER)
		{
			return new Number(Double.parseDouble(token.getText()));
		}
		else if(token.getType() == simpLParser.LITERAL)
		{
			// add checks for all possible literals
			return new Number(Double.parseDouble(token.getText()));
		}
		else return new Number(0);
	}

	// add values for primities
	public static Value getValue(double in)
	{
		return new Number(in);
	}
}