import java.awt.Color;

/**
 * Represents a point whose location is defined by a specific field of a state
 * @author Will Ruzicka and Emily McCutchan
 * @version 11/30
 */
public class KinematicPointState extends KinematicPointAbstract
{
    /**
     *  fieldName to be used 
     */
    private String fieldName;

    /**
     * The constructor for the Point State
     * @param color of the Line
     * @param width of the Line
     * @param fieldName of where the point is (shoulder, etc)
     */
    public KinematicPointState(Color color, float width, String fieldName)
    {
        super(color, width);
        this.fieldName = fieldName;
    }

    /**
     * This returns a general value to be used
     * @param state of time in which this takes place
     * @param screenSubfield of Where the exact point is
     * @return General Value of which the coordinate exists
     */
    public GeneralValue getScreenCoordinate(State state, String screenSubfield)
    {
        return state.getValue(fieldName, screenSubfield);
    }
}
