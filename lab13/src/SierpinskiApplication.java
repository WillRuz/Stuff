import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * The application frame.  This class contains
 * the main method, creating an instance of itself
 * and running the application.
 * 
 * @author Stephen
 * @version 2017-11-15
 */
public class SierpinskiApplication
{
    /**
     * The frame that holds the application.
     */
    JFrame applicationFrame;

    /**
     * Time in milliseconds to wait between animation frames.
     */
    private static final int ANIMATION_TICK = 40;
    
    /**
     * The SierpinskiPanel instance responsible for maintaining
     * and computing the state of the spinner as well as drawing it.
     */
    private SierpinskiPanel sPanel;
    
    /**
     * Slider that specifies how many levels of recursion to draw spinners at.
     */
    private JSlider recursionDepthSlider;
    
    /**
     * Slider that specifies the rotational velocity of the spinners.
     */
    private JSlider rotationSlider;
    
    /**
     * Slider that specifies the radius of the spinners.
     */
    private JSlider sizeSlider;

    /**
     * Check box specifying whether or not the spinners should
     * be drawn filled in.
     */
    private JCheckBox wireframeCheckBox;
    
    /**
     * Check box specifying whether or not to draw inscribed circles
     * on the triangles.
     */
    private JCheckBox circleCheckBox;
    
    /**
     * Check box specifying whether or not to draw triangles inscribing
     * the circles.
     */
    private JCheckBox triangleCheckBox;
    
    /**
     * Timer that fires at regular intervals to animate the spinners.
     */
    Timer timer;

    /**
     * The minimum width of the application.
     */
    private int width;
    
    /**
     * The minimum height of the application.
     */
    private int height;
    
    /**
     * Constructor, creates a new instance of the application and runs it.
     * 
     * @param title The application frame title.
     * @param width The minimum width of the application.
     * @param height The minimum height of the application.
     */
    public SierpinskiApplication(String title, int width, int height)
    {
        /**
         * The panel that holds the application UI.
         */
        JPanel contentPanel;
        
        /**
         * A convenience container to hold labeled UI components.
         */
        ControlPanel controlPanel;

        applicationFrame = new JFrame(title);
        this.width = width;
        this.height = height;

        /* Setup sliders */
        recursionDepthSlider = new JSlider(JSlider.HORIZONTAL, 1, 7, 3);
        rotationSlider = new JSlider(JSlider.HORIZONTAL, 0, 15, 0);
        sizeSlider = new JSlider(
                JSlider.HORIZONTAL, height / 4, height, height / 4);

        /* Setup check boxes */
        wireframeCheckBox = new JCheckBox();
        circleCheckBox = new JCheckBox();
        circleCheckBox.setSelected(true);
        triangleCheckBox = new JCheckBox();
        
        controlPanel = new ControlPanel(3);
        controlPanel.setBorder(
                BorderFactory.createTitledBorder("Display Parameters"));

        sPanel = new SierpinskiPanel();

        controlPanel.addControl("Recursion depth", recursionDepthSlider,
                "Changes the depth at which to recursively draw spinners.");
        
        controlPanel.addControl("Rotational velocity", rotationSlider,
                "Changes the rate at which the spinners rotate.");
        
        controlPanel.addControl("Radius", sizeSlider,
                "Changes the radius of the spinners.");
        
        controlPanel.addControl("Wireframe", wireframeCheckBox,
                "Specifies whether or not to draw only the outlines "
                + "of the spinners.");
        
        controlPanel.addControl("Draw circles", circleCheckBox,
                "Specifies whether or not to draw inscribed circles on"
                + "the triangles.");
        
        controlPanel.addControl("Draw triangles", triangleCheckBox,
                "Specifies whether or not to draw triangles around"
                + "the circles.");

        contentPanel = new JPanel(new GridBagLayout());

        JPanel chosenColorsPanel = new JPanel(new GridBagLayout());
        chosenColorsPanel.setBorder(
                BorderFactory.createTitledBorder("Current colors"));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 5);
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.ipadx = 50;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 5;
        constraints.gridheight = 2;
        chosenColorsPanel.add(new JLabel("Primary"), constraints);

        constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 5, 10, 10);
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.ipadx = 50;
        constraints.gridx = 5;
        constraints.gridy = 0;
        constraints.gridwidth = 5;
        constraints.gridheight = 2;
        chosenColorsPanel.add(new JLabel("Secondary"), constraints);

        constraints = new GridBagConstraints();        
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.6;
        constraints.weighty = 0.10;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        contentPanel.add(controlPanel, constraints);      

        applicationFrame.setLayout(new BorderLayout());
        applicationFrame.add(sPanel, BorderLayout.CENTER);
        applicationFrame.add(contentPanel, BorderLayout.SOUTH);

        /* Setup listeners for the UI. */
        sPanel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent event)
            {
                /*
                 * We change the spinner displayed in the SierpinskiPanel,
                 * grabbing the current values of our property
                 * controls to pass into the SierpinskSpinner constructor.
                 */
                sPanel.changeTriangle(
                        new SierpinskiSpinner(
                                recursionDepthSlider.getValue(),
                                event.getPoint(),
                                sizeSlider.getValue(),
                                rotationSlider.getValue() * (Math.PI / 180),
                                Color.WHITE,
                                Color.WHITE,
                                !wireframeCheckBox.isSelected(),
                                circleCheckBox.isSelected(),
                                triangleCheckBox.isSelected()));
            }            
        });

        recursionDepthSlider.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent event)
            {
                sPanel.setDepth(recursionDepthSlider.getValue());
            }
        });

        rotationSlider.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent event)
            {
                sPanel.setRotationalVelocity(
                            rotationSlider.getValue() * (Math.PI / 180));
            }
        });        

        sizeSlider.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent event)
            {
                sPanel.setRadius(sizeSlider.getValue());
            }
        });

        wireframeCheckBox.addChangeListener(new ChangeListener()
        {    
            @Override
            public void stateChanged(ChangeEvent e)
            {
                sPanel.setFilled(!wireframeCheckBox.isSelected());       
            }
        });

        circleCheckBox.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                sPanel.setDrawCircles(circleCheckBox.isSelected());
            }        
        });
        
        triangleCheckBox.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                sPanel.setDrawTriangles(triangleCheckBox.isSelected());
            }        
        });

        /* 
         * 
         * Initialize the timer variable to a newly created timer
         * that fires at a regular interval, calling a method in
         * SierpinskiPanel causing the next step of animation to
         * be computed, and the screen to be re-drawn.
         */ 
        
        timer = new Timer(ANIMATION_TICK, new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                sPanel.redraw();
            }
        });
 
    }

    /**
     * Starts the application.
     */
    public void start()
    {
        timer.start();
        // Start the timer you created in the constructor.

        applicationFrame.setMinimumSize(new Dimension(width, height));
        applicationFrame.pack();
        applicationFrame.setLocationRelativeTo(null); // Center of the screen
        applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        applicationFrame.setVisible(true);
    }

    /**
     * Entry-point for program execution.
     * 
     * @param args Command-line objects.
     */
    public static void main(String[] args)
    {
        new SierpinskiApplication(
                "Sierpinski Spinners", 1280, 768).start();
    }
}
