package exceptions;


/** API level exception thrown when error is intended to be sent to user. */
abstract class SimpLException extends RuntimeException
{
    public SimpLException()
    {
        super();
    }
    public SimpLException(String message)
    {
        super(message);
    }
}
