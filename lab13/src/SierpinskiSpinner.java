import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * Represents a Sierpinski triangle fidget spinner,
 * able to draw itself using a provided Graphics object.
 * 
 * A Sierpinski spinner is defined by the geometry of a Sierpinski triangle.
 * Two main additions/changes exist however:
 *  -Circles are inscribed in the triangles to give the appearance of a fidget spinner.
 *   Rectangles (the "arms" of the spinner) are drawn between the circles as well to fill out the appearance.
 *  -Each level of triangles spins around the previous level of triangles. This
 *   gives the effect of having multiple spinners of decreasing size.
 * 
 * @author Stephen, modified by ????
 * @version 2017-11-15
 */
public class SierpinskiSpinner
{
    /**
     * The recursion depth at which triangles and circles are drawn.
     */
    private int drawingDepth;
    
    /**
     * The radius of this spinner.
     */
    private int baseRadius;
    
    /**
     * The initial, absolute offset of the angle this spinner is drawn at.
     */
    private double angleOffset;
    
    /**
     * The rate at which this spinner rotates.
     */
    private double rotationalVelocity;
    
    /**
     * Whether or not to draw as a fidget spinner. That is, whether or not to draw circles
     * inscribed in the triangles, and wheter or not to draw lines between the circles
     * (the "arms" of a fidget spinner)
     */
    private boolean toggleCircle;
    
    /**
     * Whether or not to draw triangles. These are drawn around where the fidget spinner
     * circles would appear. At angleOffset = 0, this creates a Sierpinski Triangle.
     */
    private boolean toggleTriangle;
    
    /**
     * Whether or not this spinner should be drawn filled in.
     */
    private boolean filled;

    /**
     * The center point of the spinner.
     */
    private Point center;

    /**
     * The change in the primary color required at each level
     * of recursion to generate a gradient-like effect in the image.
     */
    private Color primaryColorStep;
    
    /**
     * The change in the secondary color required at each level
     * of recursion to generate a gradient-like effect in the image.
     */
    private Color secondaryColorStep;

    /**
     * The background color this spinner is drawn atop of.
     */
    private static final Color BACKGROUND_COLOR = Color.BLACK;
    
    /**
     * The primary color of the spinner.
     * This color is used to draw the triangles.
     */
    private Color primaryColor;
    
    /**
     * The secondary color of the spinner.
     * This color is used to draw the circles.
     */
    private Color secondaryColor;
    
    /**
     * Constructs a SierpinskiTriangle with the
     * specified properties.
     * 
     * @param depth {@link #drawingDepth}
     * @param centerPoint {@link #center}
     * @param radius {@link #baseRadius}
     * @param primaryColor {@link #primaryColor}
     * @param secondaryColor {@link #secondaryColor}
     * @param filled {@link #filled}
     * @param toggleCircle {@link #toggleCircle}
     * @param toggleTriangle {@link #toggleTriangle}
     */
    public SierpinskiSpinner(int depth, Point centerPoint, int radius,
        Color primaryColor, Color secondaryColor,
        boolean filled, boolean toggleCircle, boolean toggleTriangle)
    {
        this.center = centerPoint;
        this.drawingDepth = depth;
        this.filled = filled;
        this.baseRadius = radius;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.toggleCircle = toggleCircle;
        this.toggleTriangle = toggleTriangle;
        setDepth(depth);
    }

    
    /**
     * Constructs a SierpinskiTriangle with the
     * specified properties.
     * 
     * @param depth {@link #drawingDepth}
     * @param centerPoint {@link #center}
     * @param radius {@link #baseRadius}
     * @param rotationalVelocity {@link #rotationalVelocity}
     * @param primaryColor {@link #primaryColor}
     * @param secondaryColor {@link #secondaryColor}
     * @param filled {@link #filled}
     * @param toggleCircle {@link #toggleCircle}
     * @param toggleTriangle {@link #toggleTriangle}
     */
    public SierpinskiSpinner(int depth, Point centerPoint, int radius,
            double rotationalVelocity, Color primaryColor, Color secondaryColor,
            boolean filled, boolean toggleCircle, boolean toggleTriangle)
    {
        this(depth, centerPoint, radius, primaryColor,
                secondaryColor, filled, toggleCircle, toggleTriangle);
        
        this.rotationalVelocity = rotationalVelocity;
    }

    /**
     * Helper method for the recursive drawing function, produces
     * a color based on the input parameters.
     * 
     * @param isPrimary If true, generates the appropriate
     * shade of the primary color, otherwise, this generates the
     * appropriate shade of the secondary color.
     * @param depth The current recursion depth, used to
     * produce the appropriate color.
     * @return The generated color.
     */
    public Color generateColor(boolean isPrimary, int depth)
    {
        if (isPrimary)
        {
            return new Color(primaryColorStep.getRed() * depth,
                    primaryColorStep.getGreen() * depth,
                    primaryColorStep.getBlue() * depth);
        }

        return new Color(
                secondaryColorStep.getRed() * (this.drawingDepth + 1 - depth),
                secondaryColorStep.getGreen() * (this.drawingDepth + 1 - depth),
                secondaryColorStep.getBlue() * (this.drawingDepth + 1 - depth));
    }
    
    /**
     * Computes the change in color required to move evenly
     * from black (0, 0, 0) to the given colors in {@link #drawingDepth} steps.
     */
    public void setColorSteps()
    {
        try
        {
            primaryColorStep = new Color(
                    primaryColor.getRed() / drawingDepth,
                    primaryColor.getGreen() / drawingDepth,
                    primaryColor.getBlue() / drawingDepth);
            
            secondaryColorStep = new Color(
                    secondaryColor.getRed() / drawingDepth,
                    secondaryColor.getGreen() / drawingDepth,
                    secondaryColor.getBlue() / drawingDepth);
        }
        catch (ArithmeticException e)
        {
            primaryColorStep = primaryColor;
            secondaryColorStep = secondaryColor;
        }
    }
    
    /**
     * @return the centerPoint
     */
    public Point getCenterPoint()
    {
        return center;
    }
    
    /**
     * @return the depth
     */
    public int getDepth()
    {
        return drawingDepth;
    }

    /**
     * @return the filled
     */
    public boolean isFilled()
    {
        return filled;
    }

    /**
     * @return the radius
     */
    public int getRadius()
    {
        return baseRadius;
    }
    
    /**
     * @return the rotationalVelocity
     */
    public double getRotationalVelocity()
    {
        return rotationalVelocity;
    }
    
    /**
     * @return the angleOffset
     */
    public double getAngleOffset()
    {
        return angleOffset;
    }

    /**
     * @param point the point to set the center to
     */
    public void setCenterPoint(Point point)
    {
        center = point;
    }

    /**
     * Sets the recursion depth and calls {@link #setColorSteps()}.
     * 
     * @param depth The recursion depth to draw triangles to.
     */
    public void setDepth(int depth)
    {
        this.drawingDepth = depth;
        
        setColorSteps();
    }
    
    /**
     * @param toggleCircle Whether or not inscribed circles should be drawn.
     */
    public void setToggleCircle(boolean toggleCircle)
    {
        this.toggleCircle = toggleCircle;
    }
    
    /**
     * @param toggleTriangle Whether or not inscribed circles should be drawn.
     */
    public void setToggleTriangle(boolean toggleTriangle)
    {
        this.toggleTriangle = toggleTriangle;
    }

    /**
     * @param filled Whether or not the triangles should be drawn as filled.
     */
    public void setFilled(boolean filled)
    {
        this.filled = filled;
    }
    
    /**
     * @param color the color to set, recalculate the color steps
     */
    public void setPrimaryColor(Color color)
    {
        primaryColor = color;
        setColorSteps();
    }

    /**
     * @param radius the radius to set
     */
    public void setRadius(int radius)
    {
        this.baseRadius = radius;
    }
    
    /**
     * @param offset the offset to set, modulo 2pi
     */
    public void setRotationOffset(double offset)
    {
        angleOffset = offset % (2 * Math.PI);
    }
    
    /**
     * @param velocity the velocity to set
     */
    public void setRotationalVelocity(double velocity)
    {
        rotationalVelocity = velocity;
    }

    /**
     * @param color the color to set, recalculate the color steps
     */
    public void setSecondaryColor(Color color)
    {
        secondaryColor = color;
        setColorSteps();
    }

    /**
     * Draws a triangle, given the requisite information.
     * 
     * @param g the Graphics object to use for drawing.
     * @param centerPoint {@link #center}
     * @param radius {@link #baseRadius}
     * @param rotationOffset The angle offset at which to draw this triangle.
     * @param color The color with which to draw this triangle.
     * @param drawFilled {@link #filled}
     */
    private void drawTriangle(Graphics g, Point centerPoint, int radius,
        double rotationOffset, Color color, boolean drawFilled)
    {
        int[] x = new int[3];
        int[] y = new int[3];
        Point[] points = new Point[3];
        
        /* Point at the top of the triangle */
        points[0] = computeVertex(centerPoint, radius, rotationOffset);
        
        /* Right base-point. */
        points[1] = computeVertex(
            centerPoint, radius, rotationOffset + Math.PI * (2.0 / 3));
        
        /* Left base-point */
        points[2] = computeVertex(
            centerPoint, radius, rotationOffset - Math.PI * (2.0 / 3));

        for (int i = 0; i < points.length; i++)
        {
            x[i] = points[i].x;
            y[i] = points[i].y;            
        }
        
        g.setColor(color);
        
        if (drawFilled)
        {
            g.fillPolygon(x, y, 3);
        }
        else
        {
            g.drawPolygon(x, y, 3);
        }        
    }
    
    /**
     * Helper function for recursive drawing.  This method does the work
     * of recursively computing spinners and drawing them.
     * 
     * @param g The Graphics object to use for drawing.
     * @param depth {@link #drawingDepth}
     * @param centerPoint {@link #center}
     * @param radius {@link #baseRadius}
     * @param initialRotationOffset The base angle offset at which to draw this spinners. That is, the 
     * angle orientation of the base level. This determines the direction the triangles are pointed.
     * @param rotationOffset The addition angle offset used to compute centerpoints. This increases as the
     * depth decreases to create the effect of the high-depth spinners spinning on top.
     */
    private void drawHelper(Graphics g, int depth, Point centerPoint,
        int radius, double initialRotationOffset, double rotationOffset)
    {
        /* TODO
         * 
         * Implement this method per the details in the specification document.
         * This method is responsible for drawing the current triangle and then
         * computing the positions of the three smaller surrounding triangles and
         * calling itself to draw those.
         * 
         * You should draw triangles and circles conditionally (see variables above).
         * 
         * Use generateColor to determine the color of the triangles and circles.
         * Remember that triangles use the primary color and circles use the secondary.
         */
        
        /*
         * TODO: Set an ending condition => recursion should stop when depth == 0.
         */

        /* 
         * TODO: Conditionally draw the triangle for this level of depth.
         * intialRotationOffset determines the direction the triangle is pointed.
         */
        
        /*
         * TODO: Conditionally draw an inscribed circle on this triangle.
         */
        
        /* 
         * TODO: Compute the center points of the three surrounding smaller spinners.
         * 
         * Use the rotation offset for computing the new centerpoints. This is also affected
         * by the initial rotation. Review the geometry of a sierpinski triangle to determine
         * the angles to choose.
         * 
         * Once the centerpoints are computed, if the circles are being drawn, the "arm" of the
         * fidget spinner should be drawn. You should use the drawThickLine to draw a line from
         * the current center to the new centers. Use radius/4 for the thickness. Only draw
         * when the depth is > 1.
         */
        
        /*
         *  TODO: Recurse and draw the next spinner at each new point.
         *  The new radius is equal to the apothem of the triangle defining this spinner.
         *  The rotationOffset x 4 is also passed to the next level.
         *  This is done so that each level spins at a faster rate than the previous level, giving
         *  an effect of rotation.
         */
    }
    
    /**
     * Generates a vertex the given distance from the center point,
     * and with the given angle offset.
     * 
     * @param centerPoint {@link #center}
     * @param radius {@link #baseRadius}
     * @param angle The angle this vertex is offset from the vertex
     * at the top of the triangle, given that the triangle is resting
     * evenly on one side.
     * @return The generated vertex.
     */
    public Point computeVertex(Point centerPoint, double radius, double angle)
    {
        double x = centerPoint.x;
        double y = centerPoint.y - radius;
        
        AffineTransform transform = new AffineTransform();

        Point2D result = new Point2D.Double();

        transform.rotate(angle, centerPoint.x, centerPoint.y);
        transform.transform(new Point((int)x, (int)y), result);

        return new Point((int)result.getX(), (int)result.getY());
    }
    
    /**
     * Draw a circle given the center point rather than the top-left most point.
     * @param g The Graphics object to use for drawing.
     * @param centerX The x position of the center of the circle to draw.
     * @param centerY The y position of the center of the circle to draw.
     * @param radius The radius of the circle.
     * @param filled Whether to draw filled or not.
     */
    private void ovalFromCenter(Graphics g, double centerX, double centerY, double radius)
    {
        if (filled)
        {
            g.fillOval((int)(centerX - radius),
                    (int)(centerY - radius),
                    (int)(radius * 2), (int)(radius * 2));
        }
        else
        {
            g.drawOval((int)(centerX - radius),
                    (int)(centerY - radius),
                    (int)(radius * 2), (int)(radius * 2));
        }
    }
    
    /**
     * Draws a line with a thickness vs. the standard single pixel line. Essentially, just draws
     * a rectangle that is rotated, defined by a thin line through its center - we convert
     * a thin line to a thick line by expanding it on either side to match the thickness.
     * @param g The Graphics object to use for drawing.
     * @param x1 The x position of the starting point of the thin line.
     * @param y1 The y position of the starting point of the thin line.
     * @param x2 The x position of the ending point of the thin line.
     * @param y2 The y position of the ending point of the thin line.
     * @param thickness How many pixels thick the line should be.
     */
    private void drawThickLine(Graphics g, int x1, int y1, int x2, int y2, int thickness)
    {
        int[] xPoints = new int[4];
        int[] yPoints = new int[4];
        /*
         *  Get angle perpendicular to the line:
         */
        double angle = Math.atan(((double)y2 - y1) / ((double)x2 - x1)) + Math.PI / 2;
        double xOffset = Math.cos(angle) * thickness / 2.0;
        double yOffset = Math.sin(angle) * thickness / 2.0;
        
        xPoints[0] = x1 + (int)xOffset;
        yPoints[0] = y1 + (int)yOffset;        
        xPoints[3] = x2 + (int)xOffset;
        yPoints[3] = y2 + (int)yOffset;        
        xPoints[2] = x2 - (int)xOffset;
        yPoints[2] = y2 - (int)yOffset;        
        xPoints[1] = x1 - (int)xOffset;
        yPoints[1] = y1 - (int)yOffset;
        
        if (filled)
        {
            g.fillPolygon(xPoints, yPoints, 4); 
        }
        else
        {
            g.drawPolygon(xPoints, yPoints, 4);
        }
    }
    
    /**
     * Draws this Sierpinski triangle.
     * A triangle with color {@link #BACKGROUND_COLOR} should first be drawn
     * before recursively drawing triangles on top of it.  Be mindful of both
     * the orientation [see angleOffset] of this initial triangle, and the orientation of
     * the triangles drawn on top of it [should be oriented 180 degrees from initial triangle].
     * 
     * This should only be done if the spinner should display triangles (toggleTriangle = true).
     * 
     * @param g The Graphics object to use for drawing.
     */
    public void draw(Graphics g)
    {
        /*
         * TODO: Implement this method.  First, by drawing the base triangle (if you should
         * draw triangles) using the provided drawTriangle() method, and then by calling the
         * recursive drawHelper() method to draw the rest of the fidget spinner.
         * 
         * Take note of which values you pass into the methods, especially the angle.
         */
    }
}
