package exceptions;


// todo: look into adding format messages that include the input causing the error
// todo: look into providing error skipping methods for compiler
// todo: look into providing error factories to facilitate reuse (to avoid endless constructor overrides)
// todo: look into constructors with specific parameters only, such as: TypeError(type, ...)

/** API level exception thrown when error is intended to be sent to user. */
abstract class SimpLException extends RuntimeException
{
    public SimpLException(String message)
    {
        super(message);
    }
    public SimpLException(String message, Exception cause)
    {
        super(message, cause);
    }
}
