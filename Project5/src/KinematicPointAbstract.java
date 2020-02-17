import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.util.ArrayList;



/**
 * Abstract that defines our other Kinematic points
 * @author Will and Emily
 * @version 11/30
 */
public abstract class KinematicPointAbstract 
{
    /**
     *  List of children
     */
    private ArrayList<KinematicPointAbstract> children = new ArrayList<KinematicPointAbstract>();

    /**
     *  Color of the line
     */
    private Color color;

    /**
     * Our stroke object
     */
    private BasicStroke stroke;

    /**
     * Scale for our
     */
    private static double scale;

    /**
     * The Abstract kinematic
     * @param color of the line
     * @param width of the line
     */
    public KinematicPointAbstract(Color color, float width)
    {
        this.color = color;
        this.stroke = new BasicStroke(width);
    }

    
    /**
     * Adds the child to our child List
     * @param child that we're animating
     */
    public void addChild(KinematicPointAbstract child)
    {
        children.add(child);
    }

    /**
     * Draws the point
     * @param g grapics
     * @param state of time
     * @param screenXSubfield point x
     * @param screenYSubfield point y
     */
    public void draw(Graphics2D g, State state, String screenXSubfield, String screenYSubfield)
    {
        // Get the x and y general Values
        GeneralValue x1 = this.getScreenCoordinate(state, screenXSubfield);
        GeneralValue y1 = this.getScreenCoordinate(state, screenYSubfield);
        
        for (KinematicPointAbstract child: children)
        {
            // Same, but for the child
            GeneralValue x2 = child.getScreenCoordinate(state, screenXSubfield);
            GeneralValue y2 = child.getScreenCoordinate(state, screenYSubfield);

            // If the generalValues are valid
            if (x1.isValid() && y1.isValid() && x2.isValid() && y2.isValid())
            {
                // create a 2D line
                Shape shape = new Line2D.Double(x1.getDoubleValue() * scale, y1.getDoubleValue() * scale,
                        x2.getDoubleValue() * scale, y2.getDoubleValue() * scale);

                //Draw the line
                g.setColor(this.color);
                g.setStroke(stroke);
                g.draw(shape);
                
            }

            // draw all the points
            child.draw(g, state, screenXSubfield, screenYSubfield);
        }
    }

    /**
     * Sets the Scale for our actual Point
     * @param scale for our point
     */
    public static void setScale(double scale)
    {
        KinematicPointAbstract.scale = scale;
    }

    /**
     * 
     * @param state time in which this happened
     * @param screenSubfield subfield (x/y/z)
     * @return GeneralValue of our point
     */
    public abstract GeneralValue getScreenCoordinate(State state, String screenSubfield);

}
