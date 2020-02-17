import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * represents the set of fields contained within a csv file
 * @author Emily McCutchan and Will Ruzicka
 * @version 10/27/17
 */
public class FieldMapper implements Iterable<String>
{
    /**
     * the map that contains all of the data structure's relational information
     */
    private Map<String, Field> fieldMap;
    
    /**
     * create the data structure surrounding these column names
     * if a column name has a single letter value after an underscore, the name after the underscore is the subfield
     * otherwise, it is just added as it's own major field with a single subfield of ""
     * @param columnNames the names of each column to use to create the data structure off of
     */
    public FieldMapper(String[] columnNames)
    {
        fieldMap = new TreeMap<String, Field>();
        for (int i = 0; i < columnNames.length; i++) //for each of the column names
        {
            String majorField = "";
            String subField = "";
            String columnName = columnNames[i];
            if (columnName.charAt(columnName.length() - 2) == '_') //if there is a singular character after a _ in 
                                                                   //the column name
            {   
                majorField = columnName.substring(0, columnName.length() - 2); //grab the major field from the name
                subField = columnName.substring(columnName.length() - 1); //grab the last char and make it the subfield
                                                                           //name
            }
            else //if the column name does not indicate the presence of a subfield
            {
                majorField = columnName;
            }
            if (!(fieldMap.containsKey(majorField))) //if this is a new key
            {
                fieldMap.put(majorField, new Field()); //set up the new field in the map
            }
            fieldMap.get(majorField).addSubField(subField, i); //even if the subfield is "", there still needs 
                                                               //to be a reference to its location
        
        }
    }
    
    /**
     * turn a row of values into a point
     * @param stringValues the values derived from the csv file
     * @param fieldName the name of the major field which you want the data for
     * @return the point representing the data value(s) of the field at this line
     */
    public PointND extractPointND(String[] stringValues, String fieldName)
    {
        PointND finalPoint = new PointND(); //create the new point to add the fields to
        if (fieldMap.containsKey(fieldName))
        {
            Field field = fieldMap.get(fieldName); //grab the data structure for the specified field
            for (String subfield : field) //for every subfield within the designated field
            {
                //add the variable by adding the name and the value associated with it
                finalPoint.add(subfield, new GeneralValue(stringValues[field.getIndex(subfield)]));
            }
        }
        else
        {
            return null;
        }
        return finalPoint; //return the final result
    }
    
    /**
     * get the object for a specified field
     * @param fieldName the name of the field to retrieve
     * @return the object associated with the fieldName
     */
    public Field getField(String fieldName)
    {
        return fieldMap.get(fieldName); //retrieve the item out of the map
    }
    
    /**
     * get the number of fields contained in the map
     * @return the number of fields
     */
    public int size()
    {
        return fieldMap.size(); //gets the number of keys, ergo the number of fields
    }

    /**
     * grab all of the variables that have been mapped in the constructor's names
     * @return an iterator over all of the keys in the fieldMap
     */
    @Override
    public Iterator<String> iterator()
    {
        return fieldMap.keySet().iterator(); //grab the keys for the map and iterate over them 
    }
}
