/**
 * 
 * @author Emily McCutchan and Will Ruzicka
 * @version 10/11/17
 * gets the average statistics across a number of single items
 */
public abstract class MultipleItemAbstract extends SingleItemAbstract
{
    /**
     * get the number of cases being considered within this class
     * @return the number of single items in the dataset
     */
    public abstract int getSize();
    
    /**
     * get a specific item within the dataset 
     * @param index the index of the object to retrieve
     * @return the single item at this index
     */
    public abstract SingleItemAbstract getItem(int index);
    
    /**
     * get the maximum state of this item
     * @param fieldName the name of the major field for the subfield
     * @param subFieldName the subfield about which to calculate the statistics
     * @return the state that contains the maximum state of the subfield
     */
    public State getMaxState(String fieldName, String subFieldName)
    {
        // Best value found so far
        GeneralValue maxValue = new GeneralValue();
        
        State maxState = new State();

        // Loop over all States
        for (int i = 0; i < this.getSize(); ++i) 
        {
            // Extract the value of the specific dimension and time
            State s = this.getItem(i).getMaxState(fieldName, subFieldName);
            GeneralValue v = s.getValue(fieldName, subFieldName);

            // Is this one larger?
            if (v.isGreaterThan(maxValue))            
            {
                // Yes - replace it
                maxValue = v;
                maxState = s;
            }
        }

        // 
        return maxState;
    }
    
    /**
     * get the minimum state of this item
     * @param fieldName the name of the major field for the subfield
     * @param subFieldName the subfield about which to calculate the statistics
     * @return the state that contains the minimum state of the subfield
     */
    public State getMinState(String fieldName, String subFieldName)
    {
        // Best value found so far
        GeneralValue minValue = new GeneralValue();
        
        State minState = new State();

        // Loop over all States
        for (int i = 0; i < this.getSize(); ++i) 
        {
            // Extract the value of the specific dimension and time
            State s = this.getItem(i).getMinState(fieldName, subFieldName);
            GeneralValue v = s.getValue(fieldName, subFieldName);

            // Is this one larger?
            if (v.isLessThan(minValue))            
            {
                // Yes - replace it
                minValue = v;
                minState = s;
            }
        }

        // 
        return minState;
    }
    
    /**
     * calculate the average value of this subfield
     * @param fieldName the name of the major field for the subfield
     * @param subFieldName the subfield about which to calculate the statistics
     * @return the average value of the subfield
     */
    public GeneralValue getAverageValue(String fieldName, String subFieldName)
    {
        //containers to be used in final calculation
        double sum = 0;
        int valuesIncluded = 0;
        for (int i = 0; i < getSize(); i++) //for each state in the trial
        {
            SingleItemAbstract currItem = getItem(i);
            GeneralValue currItemAvg = currItem.getAverageValue(fieldName, subFieldName);
            if (currItemAvg.isValid()) //if this value is valid
            {
                //add it to the average calculation
                sum += currItemAvg.getDoubleValue(); //add the value to the sum
                valuesIncluded++; //add to the total number of values included
            }
            //otherwise, do nothing
        }
        return new GeneralValue(sum / (double) valuesIncluded); //return the overall average

    }
}
