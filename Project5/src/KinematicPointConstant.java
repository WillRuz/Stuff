import java.awt.Color;

/**
 * The Point that stays Constant
 * @author Will/Emily
 * @version 11/30
 */
public class KinematicPointConstant extends KinematicPointAbstract
{
    // new point from PointND class
    private PointND point;

    /**
     * 
     * @param color of the line
     * @param width of the line
     * @param x axis
     * @param y axis
     * @param z axis
     */
    public KinematicPointConstant(Color color, float width, double x, double y, double z)
    {
        super(color, width);
        
        // Create the PointND with the parameters
        
        point = new PointND();
        point.add("x", new GeneralValue(x));
        point.add("y", new GeneralValue(y));
        point.add("z", new GeneralValue(z));
    }

    /**
     * 
     * @param state of time in which this happens
     * @param screenSubfield the subfield for the GeneralValue
     * @return GeneralValue of the place where the point exists
     */
    public GeneralValue getScreenCoordinate(State state, String screenSubfield)
    {
        return point.getValue(screenSubfield);
    }
}
