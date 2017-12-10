package exceptions;


/** Thrown for any error that occurs while Parsing. */
public class ParserException extends SimpLException
{
    public ParserException(String message)
    {
        super(message);
    }
    public ParserException(String message, Exception cause)
    {
        super(message, cause);
    }
}
