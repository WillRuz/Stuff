import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

/**
 * holds a number of values that make up 
 * @author Emily McCutchan and Will Ruzicka
 * @version 10/27/17
 */
public class PointND implements Iterable<String>
{
    /**holds the set of values in this data point */
    private TreeMap<String, GeneralValue> values;
    
    /**
     * initialize the point
     */
    public PointND()
    {
        values = new TreeMap<String, GeneralValue>(); //initialize the treemap
    }
    
    /**
     * add a subfield to the point
     * @param subFieldName the name of the subfield
     * @param value the value of the subfield at this point
     */
    public void add(String subFieldName, GeneralValue value)
    {
        values.put(subFieldName, value); //use the put method to add the new value
    }
    
    /**
     * retrieve the value of a particular subfield of this point
     * @param subFieldName the subfield whose value is to be retrieved
     * @return the value of that subfield at this point
     */
    public GeneralValue getValue(String subFieldName)
    {
        if (values.containsKey(subFieldName))
        {
            return values.get(subFieldName); //get the subfield out of the hashmap
        }
        else
        {
            return new GeneralValue(Double.NaN); //return an invalid value if the subfield is not found in the map
        }
    }
    /**
     * retrieve the number of dimensions in this point
     * @return the dimensions to this point
     */
    public int size()
    {
        return values.keySet().size(); //get the size of the keys, which should indicate how many dimensions there are
    }
    
    /**
     * get the iterator for all of the keys
     * @return the iterator for the keys
     */
    public Iterator<String> iterator()
    {
        return values.keySet().iterator(); //get the iterator for the keys
    }
    
    /**
     * retrieve the string representation of this point
     * @return the string representation in the form of "KEY = VALUE; "
     */
    @Override
    public String toString()
    {
        String aggregate = ""; //create a holder for the total field
        Set<String> keys = values.keySet(); //grab all of the keys in values
        
        for (String key : keys) //for each key in the table
        {
            aggregate += key + " = " + values.get(key) + "; "; //put in the format "SUBFIELDNAME = VALUE; " 
                                                               //and add to agg
        }
        return aggregate;
    } 
}
