import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A convenience class that allows for easy layout of
 * controls and associated labels within a panel.
 * 
 * @author Nicholas
 * @version 2016-11-09
 */
@SuppressWarnings("serial")
public class ControlPanel extends JPanel
{
    /**
     * The current number of controls that have been added to the panel.
     */
    private int controlCount;
    
    /**
     * The maximum number or rows this panel can have.
     */
    private final int maxRowCount;

    /**
     * Constructs a ControlPanel with the specified height (in rows).
     * 
     * @param maxRowCount The maximum number of rows to fill before
     * adding a new column.
     */
    public ControlPanel(int maxRowCount)
    {
        super(new GridBagLayout());        
        controlCount = 0;
        this.maxRowCount = maxRowCount;
    }
    
    /**
     * Adds the specified control and related information to this
     * control panel for displaying.
     * 
     * @param label The label to display alongside the control.
     * @param control The control to add to the panel.
     * @param toolTipText The text to display when mousing over the control.
     */
    public void addControl(
            String label, JComponent control, String toolTipText)
    {
        /*
         * Computes the column of this panel that
         * the component belongs to.
         */
        int xOffset = controlCount / maxRowCount;
        
        JLabel controlLabel = new JLabel(label);
        
        control.setToolTipText(toolTipText);
        controlLabel.setToolTipText(toolTipText);
        
        /*
         * If the control we are adding is a check box,
         * we want to also be able to click on the control's
         * associated label to interact with it.
         */
        if (control instanceof JCheckBox)
        {
            final JCheckBox checkBox = (JCheckBox)control;

            controlLabel.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent event)
                {
                    checkBox.setSelected(!checkBox.isSelected());
                }
            });
        }

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.2;
        
        /*
         * Each "control" we add is made up of two components:
         * The label, and the component itself.  Thus, each column
         * in our control panel is technically two columns, which
         * is why this needs to be doubled to get the correct
         * column of the grid. 
         */
        constraints.gridx = xOffset * 2;
        
        /*
         * The y-coordinate of the control is based on our MAX_ROW_COUNT
         * variable, if we keep track of the number of controls we have
         * added previously, we can mod that by the max row count to
         * determine which row this control belongs to.
         */
        constraints.gridy = controlCount % maxRowCount;
        
        add(controlLabel, constraints);
        
        /*
         * It is usually a good idea to re-initialize your constraints object
         * so that you don't forget about some constraint set earlier in the
         * code that causes your component to not display as expected.
         */
        constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.8;
        
        /*
         * Same as above, but the component needs to go into the column
         * adjacent to the column occupied by the label, hence the + 1.
         */
        constraints.gridx = xOffset * 2 + 1;
        constraints.gridy = controlCount % maxRowCount;

        add(control, constraints);

        controlCount++;
    }
}
