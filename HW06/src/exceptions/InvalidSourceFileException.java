package exceptions;


/** Thrown when source file is invalid (eg doesn't exist, bad extension, etc). */
public class InvalidSourceFileException extends SimpLException
{
    public InvalidSourceFileException(String message)
    {
        super(message);
    }
    public InvalidSourceFileException(String message, Exception cause)
    {
        super(message, cause);
    }
}
