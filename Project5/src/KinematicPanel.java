import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * Panel for drawing a single view of an infant
 * 
 * @author CS2334, modified by Will Ruzicka and Emily McCutchan
 * @version 2017-11-30
 *
 */
public class KinematicPanel extends JPanel
{
    /**
     * serial Crap
     */
    private static final long serialVersionUID = 2L;
    /**
     * flip for X
     */
    private double flipX;
    /**
     * flip for Y
     * 
     */
    
    private double flipY;

    /**
     * Subfield X.
     */
    
    private String screenXSubfield;
    /**
     * Subfield Y 
     */
    
    private String screenYSubfield;
    /**
     * Root of the kinematic tree.
     */
    
    private KinematicPointAbstract rootPoint;
    
    /** 
     * State to paint.
     */
    private State state;
    
    /** 
     * Panel title 
     */
    private String title;
    /**
     *  Font used for panel title. 
     */
    private static final Font FONT = new Font(Font.SERIF, Font.ITALIC, 20);

    /**
     * Constructor
     * 
     * @param rootPoint of the kinematic tree for this panel
     * @param flipX direction for X dimension
     * @param flipY direction for Y dimension
     * @param screenXSubfield for the X dimension
     * @param screenYSubfield for the Y dimension
     * @param title of the panel Panel title
     */
    public KinematicPanel(KinematicPointAbstract rootPoint, double flipX, double flipY, String screenXSubfield,
            String screenYSubfield, String title)
    {
        super();
        this.flipX = flipX;
        this.flipY = flipY;
        this.rootPoint = rootPoint;
        this.screenXSubfield = screenXSubfield;
        this.screenYSubfield = screenYSubfield;
        this.setPreferredSize(new Dimension(400, 200));
        this.title = title;

        // Set up the border for the panel
        Border border = BorderFactory.createLineBorder(Color.black);
        this.setBorder(border);
    }

    /**
     * Set the state to render
     * 
     * @param state to be painted
     */
    public void setState(State state)
    {
        this.state = state;
        this.repaint();
    }

    /**
     * Paints the panel
     * 
     * @param g  is the Graphics
     */
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        // Draw the title
        g.setFont(FONT);
        FontMetrics fm = g.getFontMetrics();
        g.drawString(this.title, 30, (int) fm.getHeight() + 10);

        // Render as long as state is defined
        if (this.state != null)
        {
            Graphics2D g2 = (Graphics2D) g;

            // Translate the graphics context origin to the center of the panel
            g2.translate(this.getWidth() / 2, this.getHeight() / 2);
            // Flip the drawing directions
            g2.scale(flipX, flipY);

            // Draw the kinematic tree
            rootPoint.draw(g2, state, screenXSubfield, screenYSubfield);

            // These next two lines make the border drawing work properly
            // Flip back
            g2.scale(flipX, flipY);
            // Translate the origin back
            g2.translate(-this.getWidth() / 2, -this.getHeight() / 2);
        }
    }
}
