package exceptions;


// todo: look into adding format messages that include the input causing the error

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
