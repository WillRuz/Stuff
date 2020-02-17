import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * holds the information about a particular infant and all of the runs involving
 * that infant
 * 
 * @author Emily McCutchan and Will Ruzicka
 * @version 10/11/17
 */
public class Infant extends MultipleItemAbstract implements Iterable<Trial>
{
    /** holds the information for the different trials using this infant */
    private ArrayList<Trial> trialList;

    /** the ID number of this particular infant */
    private String infantID;

    /** the maximum number of weeks this infant can be involved */
    private static final int MAX_WEEK = 16;

    /**
     * The constructor which takes an infant and its indices
     * 
     * @param infant
     *            The infant
     * @param indices
     *            The indices that indicate the subset of Infant's Trials
     */
    public Infant(Infant infant, int[] indices)
    {
        this.infantID = infant.getInfantID();

        for (int i : indices)
        {
            for (Trial t : infant.trialList)
            {
                if (i == t.getWeek())
                {
                    this.trialList.add(t);
                }
            }
        }
    }

    /**
     * constructor
     * 
     * @param directory
     *            the directory containing the Trial files
     * @param infantID
     *            unique identifier for this infant
     */
    public Infant(String directory, String infantID)
    {
        trialList = new ArrayList<Trial>();
        this.infantID = infantID; // set this infant's ID

        for (int i = 0; i < MAX_WEEK; i++) // for each week that we have data
        {
            try
            {
                trialList.add(new Trial(this, directory, infantID, i)); // add
                                                                        // the
                                                                        // trial
                                                                        // for
                                                                        // this
                                                                        // week
                                                                        // to
                                                                        // the
                                                                        // list

            }
            catch (IOException e) // if we cannot find the file for whatever
                                  // reason
            {
                // this allows the rest of the trials to be loaded
            }
        }
    }

    /**
     * The Iterator which should get an Iterator with Trials in it
     * @return Iterator of all the Trials in a certain infant 
     */
    public Iterator<Trial> iterator()
    {
        return trialList.iterator();
    }

    /**
     * grab an item from the list of trials for this infant
     * 
     * @param index
     *            the index of the trial to be accessed
     * @return the trial for the requested week
     */
    public Trial getItem(int index)
    {
        return trialList.get(index); // grab that element from the list
    }

    /**
     * get the size of the trial list
     * 
     * @return the size of the trial list
     */
    public int getSize()
    {
        return trialList.size(); // get the number of items in the trialList,
                                 // not using MAX_WEEK in case one of the
        // trials can't be loaded
    }

    /**
     * get the unique ID of this infant
     * 
     * @return the ID of the infant
     */
    public String getInfantID()
    {
        return infantID; // simple getter
    }
}