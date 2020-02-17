import java.util.Iterator;
import java.util.TreeMap;

/**
 * Representation of the state of a single time step
 * 
 * @author  Emily McCutchan and Will Ruzicka
 * @version 10/27/17
 *
 */
public class State extends SingleItemAbstract implements Iterable<String>
{
    /**holds the values of the various variables*/
    private TreeMap<String, PointND> variables;
    
    /**contains the container for the other states that this state is a part of*/
    private Trial trial;
    
    /**
     * create a state with no fields
     */
    public State()
    {
        variables = new TreeMap<String, PointND>(); //there are no variables to initialize
        trial = null; //there is no trial to initialize this
    }
    
    /**
     * create a new State variable
     * @param trial what trial this belongs to
     * @param fieldMapper describes how to create fields from the data
     * @param values a csv row containing the data
     */
    public State(Trial trial, FieldMapper fieldMapper, String values)
    {
        this.trial = trial; //set the trial
        String[] stringValues = values.split(",");
        variables = new TreeMap<String, PointND>();
        for (String key : fieldMapper) //for each major key in the fieldMapper
        {
            variables.put(key, fieldMapper.extractPointND(stringValues, key)); //use the extractPointND to put each 
                                                                               //of the values in the right place
        }
    }
     
    /**
     * get the trial of this state
     * @return the trial
     */
    public Trial getTrial()
    {
        return trial; //simple getter
    }
    
    /**
     * get the point for a particular field (e.g. Left wrist, right wrist)
     * @param fieldName the field to be retrieved (e.g. left wrist, right wrist)
     * @return the point containing this field, null if field isn't included in the map
     */
    public PointND getPoint(String fieldName)
    {
        if (variables.containsKey(fieldName))
        {
            return variables.get(fieldName);
    
        }
        else
        {
            return null;
        }
    }
    
    /**
     * gets the value of the subfield of the field
     * @param fieldName the main item contained in this state to access (e.g. left wrist, right wrist)
     * @param subFieldName the subfield (e.g. x, y, z) of the field
     * @return the value of the subfield of the field
     */
    public GeneralValue getValue(String fieldName, String subFieldName)
    { 
        //if neither of the fields are bogus
        if (!variables.containsKey(fieldName) || !variables.get(fieldName).getValue(subFieldName).isValid())
        {
            return new GeneralValue();
        }
        return variables.get(fieldName).getValue(subFieldName); //get the field and then grab the subfield
    }
     
    /**
     * get the maximum state of the subfield
     * @param fieldName the name of the field that the subfield belongs to
     * @param subFieldName the name of the subfield to get the maximum state of
     * @return the state where this subfield is maximized
     */
    public State getMaxState(String fieldName, String subFieldName)
    {
        return this;
    }
    
    /**
     * get the minimum state of the subfield
     * @param fieldName the name fo the field that the subfield belongs to
     * @param subFieldName the name of the subfield to get the minimum state of
     * @return the state where hte subfield is minimized
     */
    public State getMinState(String fieldName, String subFieldName)
    {
        return this; 
    }   
    
    /**
     * get the average value of this field along this subfield
     * @param fieldName the name of the field that subfield belongs to
     * @param subFieldName the name of the subfield to calculate the average of
     * @return the average value of the specified subfield
     */
    public GeneralValue getAverageValue(String fieldName, String subFieldName)
    {
        return variables.get(fieldName).getValue(subFieldName);
    }
    
    /**
     * get the iterator for every variable held in this state
     * @return the iterator
     */
    public Iterator<String> iterator()
    {
        return variables.keySet().iterator(); //get the iterator for every variable within this state
    }
    
    /**
     * return a list of all of the fields in this state with their current state in the form "FIELDNAME(POINTND)\n"
     * @return the string representation
     */
    @Override
    public String toString()
    {
        String result = "";
        for (String majorField : variables.keySet()) //for every major field in this state
        {
            result += majorField + "(" + variables.get(majorField) + ")\n"; //add the string in the proper format
        }
        return result; //return the summation of all the fields
    }
}
