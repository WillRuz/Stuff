
/**
 * Value class
 * 
 * Captures both a double value and whether or not it is valid
 * 
 * @author Will Ruzicka and Emily McCutchan
 * @version 10/11/17
 *
 */
public class GeneralValue
{
    /** Is the value valid? */
    private boolean valid;
    /** Double value stored.  */
    private double doubleValue;

    /**
     * General constructor, means theres no value in doubleValue
     */
    public GeneralValue()
    {
        //initializes a null doubleValue but a false valid boolean
        this.valid = false;
    }
    
    /**
     * The usual constructor with a value in it
     * @param strg the double value or "NaN" for no data
     */
    public GeneralValue(String strg)
    {
        // if the strg given is "NaN" it isnt valid
        if (strg.equalsIgnoreCase("NaN"))
        {
            this.valid = false; //this is invalid if the value is "NaN"
        }
        //since no other data besides doubles and "NaN"'s are given, any other data is valid
        else 
        {
            this.valid = true; //this must be valid because it is not NaN
            this.doubleValue = Double.parseDouble(strg); //parse the double value
        }
    }
    
    /**
     * create a new GeneralValue based off of a double
     * @param doubleValue the double to create the value off of
     */
    public GeneralValue(double doubleValue)
    {
        if (Double.isNaN(doubleValue))  //if the double is valid
        {
            this.valid = false; //then this value is not valid 
        }
        else //when the double is not valid
        {
            this.valid = true; //set the value as valid
            this.doubleValue = doubleValue; //set the double value
        }
    }
    
    /**
     * Getter
     * @return true or false based upon the value of valid
     */
    public boolean isValid()
    {
        return this.valid; //basic getter, just grab the value
    }
    
    /**
     * Getter
     * @return the value of the actual point of data
     */
    public double getDoubleValue() throws InvalidValueException
    {
        if (this.isValid()) //if this value is valid
        {
            return this.doubleValue; //basic getter, just grab the value
        }
        else
        {
            throw new InvalidValueException("tried to access an invalid value"); //throw an exception because the user
                                                                                 //is not allowed to grab an invalid
        }
    }
    /**
     * String rep of the general double
     * @return a String with the double value or "Invalid"
     */
    public String toString()
    {
        if (!this.valid) //if this is not valid
        {
            return "invalid"; //print out "invalid"
        }
        else //this must be valid
        {
            return String.format("%.3f", doubleValue); //use the formatter to get the correct number of decimal places
        }
        
    }
    
    /**
     * determines if the value is less than this value
     * @param value the value for comparison
     * @return if this is less than the other value
     *          automatically false if this value is not valid
     *          automatically true if the other value is not valid
     */
    public boolean isLessThan(GeneralValue value)
    {
        if (!this.isValid()) //if this is not valid
        {
            return false; //then this is automatically greater than value
        }
        else if (!value.isValid()) //if value is invalid
        {
            return true; //then this is automatically less than value
        }
        else
        {
            return this.getDoubleValue() < value.getDoubleValue(); //use the regular comparison for all other cases
        }
    }

    /**
     * determines if the value is greater than this value
     * @param value the value for comparison
     * @return if this is greater than the other value
     *          automatically false if this value is not valid
     *          automatically true if the other value is not valid
     */
    
    public boolean isGreaterThan(GeneralValue value)
    {
        if (!this.isValid()) //if this is not valid
        {
            return false; //then this is automatically less than value
        }
        else if (!value.isValid()) //if value is invalid
        {
            return true; //then this is automatically greater than value
        }
        else
        {
            return this.getDoubleValue() > value.getDoubleValue(); //use the regular comparison for all other cases
        }
    }
}
