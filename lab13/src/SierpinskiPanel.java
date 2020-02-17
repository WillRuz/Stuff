import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;

/**
 * Panel that contains the Sierpinski Spinner.
 * Responsible for animating the spinner, and managing state.
 * 
 * @author Stephen
 * @version 2017-11-15
 */
@SuppressWarnings("serial")
public class SierpinskiPanel extends JPanel
{
    /**
     * The list of triangles currently being tracked and
     * managed by this panel.
     */
    private SierpinskiSpinner spinner;

    /**
     * Constructs a new SierpinskiPanel, initializing the
     * necessary variables.
     */
    public SierpinskiPanel()
    {
        setBackground(Color.CYAN);
        setDoubleBuffered(true);
        spinner = new SierpinskiSpinner(3, new Point(0, 0), 0,
                Color.WHITE, Color.WHITE,
                false, true, false);
    }
    
    /**
     * Changes the triangle displayed. This is used to relocate the triangle after mouse clicks.
     * @param triangle The new triangle that should be displayed.
     */
    public void changeTriangle(SierpinskiSpinner triangle)
    {
        this.spinner = triangle;
    }

    /**
     * Applies the specified radius to the triangle
     * stored in this panel.
     * 
     * @param radius The radius to apply to the triangle.
     */
    public void setRadius(int radius)
    {
        spinner.setRadius(radius);
    }
    
    /**
     * Applies the specified rotational velocity
     * to the triangle stored in this panel.
     * 
     * @param velocity The rotational velocity to apply to the triangle.
     */
    public void setRotationalVelocity(double velocity)
    {
        spinner.setRotationalVelocity(velocity);
    }
    
    /**
     * Applies the specified recursion depth
     * to the triangle stored in this panel.
     * 
     * @param depth The recursion depth to apply to the triangle.
     */
    public void setDepth(int depth)
    {       
        spinner.setDepth(depth);
    }
    
    /**
     * Sets the triangle to either be drawn filled or as wireframes.
     * 
     * @param filled Whether or not the triangle should be drawn filled.
     */
    public void setFilled(boolean filled)
    {
        spinner.setFilled(filled);
    }
    
    /**
     * Sets whether or not inscribed circles should
     * be drawn on the triangles.
     * 
     * @param draw Whether or not inscribed circles should
     * be drawn on the triangles.
     */
    public void setDrawCircles(boolean draw)
    {
        spinner.setToggleCircle(draw);
    }
    
    /**
     * Sets whether or not triangles should
     * be drawn around the circles.
     * 
     * @param draw Whether or not triangles should
     * be drawn around the circles.
     */
    public void setDrawTriangles(boolean draw)
    {
        spinner.setToggleTriangle(draw);
    }
    
    /**
     * Applies the specified primary color
     * to all of the triangles stored in this panel.
     * 
     * @param color The primary color to apply to all triangles.
     */
    public void setPrimaryColor(Color color)
    {
        spinner.setPrimaryColor(color);
    }
    
    /**
     * Applies the specified secondary color
     * to all of the triangles stored in this panel.
     * 
     * @param color The secondary color to apply to all triangles.
     */
    public void setSecondaryColor(Color color)
    {
        spinner.setSecondaryColor(color);
    }
    
    /**
     * Performs the animation step of the program, computing the
     * new location and orientation for all triangles.
     */
    private void updateSpinner()
    {
        spinner.setRotationOffset(
                    spinner.getAngleOffset() +
                    spinner.getRotationalVelocity());
    }
    
    /**
     * Runs the animation step and repaints the triangles.
     */
    public void redraw()
    {
        updateSpinner();
        repaint();
    }
    
    /** (non-Javadoc)
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     * 
     * @param g Graphics object used for drawing.
     */
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        spinner.draw(g);
        
        g.setColor(Color.DARK_GRAY);

        g.drawPolygon(new int[]{0 + 5, getWidth() - 5, getWidth() - 5, 0 + 5},
                new int[]{0 + 5, 0 + 5, getHeight() - 5, getHeight() - 5}, 4);
    }
}
