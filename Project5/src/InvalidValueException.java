/**
 * 
 * @author Emily McCutchan and Will Ruzicka
 * @version 10/11/2017
 * an exception that is thrown if one of the values given to 
 */

public class InvalidValueException extends RuntimeException
{
    /**
     * creates a new InvalidValueException
     * @param message the message to be displayed in the error report
     */
    public InvalidValueException(String message)
    {
        super(message); //this is only a more specific case of RuntimeException
    }
}
