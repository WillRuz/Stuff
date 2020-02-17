import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Representation of a single trial
 * 
 * @author Emily McCutchan and Will Ruzicka
 * @version 9/20/17
 *
 */
public class Trial extends MultipleItemAbstract
{
    /** Sequence of states.   */
    private ArrayList<State> stateList;
    /**contains information about the infant*/
    private Infant infant;
    /** Week index.  */
    private int week;
    /** File name that was loaded.  */
    private String fileName;

    /** The trialMapper for a trial */
    private FieldMapper trialMapper;

    /**
     * Trial constructor
     * 
     * @param infant The infant to which this Trial belongs
     * @param directory String representing the directory containing the data files
     * @param infantID String representing the infant ID
     * @param week int week for the data file.
     * 
     * @throws IOException If there is an error finding or loading the data file.
     */
    public Trial(Infant infant, String directory, String infantID, int week) throws IOException
    {
        //this.infantID = infantID;
        this.week = week;
        this.fileName = String.format("%s/subject_%s_w%02d.csv", 
                directory, infantID, week);

        this.infant = infant;

        this.stateList = new ArrayList<State>();

        // Open the file
        BufferedReader br = new BufferedReader(new FileReader(this.fileName));
        String strg;

        // Use the first line to construct the FieldMapper
        strg = br.readLine(); 
        FieldMapper trialMapper = new FieldMapper(strg.split(","));

        // Read the first line of data
        strg = br.readLine();

        // Loop as long as there are lines 
        while (strg != null)
        {
            stateList.add(new State(this, trialMapper, strg));
            strg = br.readLine();
        }
        br.close();
    }

    /**
     * The getter for Trial
     * @return FieldMapper for the trial
     */
    public FieldMapper getFieldMapper()
    {
        return this.trialMapper;
    }

    /**
     * get the set of states representing the trial
     * @return the list of states
     * @param index the x,y, or z by # 1 2 or 3
     */
    public State getItem(int index)
    {
        return stateList.get(index); //grab the state of this index
    }

    /**
     * Get the size of the stateList array
     * @return size of list of data
     */

    public int getSize()
    {
        return stateList.size(); //simple getter
    }

    /**
     * retrieve the infant under consideration for this trial
     * @return the infant
     */
    public Infant getInfant()
    {
        return infant; //simple getter
    }

    /**
     * get the week over which the trial was executed
     * @return the week
     */
    public int getWeek()
    {
        return week; //simple getter
    }

    /**
     * the file name in which the trial is being stored
     * @return the file name
     */
    public String getFileName()
    {
        return fileName; //simple getter
    }

    /**
     * Returns a String Representation of the week
     * @return String representation of the week
     */
    public String toString()
    {
        return "Week " + week;
    }


}