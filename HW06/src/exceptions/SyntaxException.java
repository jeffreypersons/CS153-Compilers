package exceptions;


/** Thrown for any error that occurs while lexing. */
public class SyntaxException extends SimpLException
{
    public SyntaxException(String message)
    {
        super(message);
    }
    public SyntaxException(String message, Exception cause)
    {
        super(message, cause);
    }
}
