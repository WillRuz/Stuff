import java.util.Iterator;
import java.util.TreeMap;

/**
 *  captures a set of subfields within one field
 * @author Emily McCutchan and Will Ruzicka
 * @version 10/27/17
 */
public class Field implements Iterable<String>
{
    /**
     * contains all of the subfields of this field (e.g. x, y, z)
     */
    private TreeMap<String, Integer> subFields;
    
    /**
     * create a new field
     */
    public Field()
    {
        subFields = new TreeMap<String, Integer>(); //initialize the hashmap
    } 
    
    /**
     * add a field to this item
     * @param subFieldName the name of the subfield 
     * @param columnIndex the index for the column of this subfield within the field
     */
    public void addSubField(String subFieldName, int columnIndex)
    {
        subFields.put(subFieldName, columnIndex); //simply use the put function 
    }
    
    /**
     * retrieve a specific subfield's value
     * @param subFieldName the name of the subfield to get information for
     * @return the information linked to that field
     */
    public Integer getIndex(String subFieldName)
    {
        return subFields.get(subFieldName); //retrieve the mapped information for this specific subfield
    }
    
    /**
     * retrieve how many subfields there are in this field
     * @return the number of subfields
     */
    public int size()
    {
        return subFields.keySet().size(); //grab the number of subfields
    }
    
    /**
     * create an iterator over each of the subfields in this field
     * @return the iterator containing the keys
     */
    public Iterator<String> iterator()
    {
        return subFields.keySet().iterator(); //grab the iterator for the keys
    }
    
    /**
     * get the string representation of this field as a list of subfields in the form "SUBFIELD(INDEX); "
     * @return the 
     */
    @Override
    public String toString()
    {
        String result = ""; //container for the summation
        for (String subfield : subFields.keySet()) //
        {
            result += subfield + "(" + subFields.get(subfield) + "); ";
        }
        return result;
    }
}