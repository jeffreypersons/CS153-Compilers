package exceptions;


/** Thrown when source file is invalid (eg doesn't exist, bad extension, etc). */
public class InvalidSourceFileException extends SimpLException
{
    public InvalidSourceFileException()
    {
        super();
    }
    public InvalidSourceFileException(String message)
    {
        super(message);
    }
}
