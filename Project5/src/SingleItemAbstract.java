
/**
 * 
 * @author Emily McCutchan and Will Ruzicka
 * @version 10/11/17
 * Creates a class about which a number of statistics about an object can be computed
 */
public abstract class SingleItemAbstract
{
    /**
     * get the maximum state of a subfield of a major field
     * @param fieldName the name of the major field the subfield belongs to
     * @param subFieldName the axis along which to get the statistic
     * @return the maximum state
     */
    public abstract State getMaxState(String fieldName, String subFieldName);
    
    /**
     * get the minimum state of the subfield
     * @param fieldName the name of the major field the subfield belongs to
     * @param subFieldName the name of the subfield to be evaluated
     * @return the minimum state
     */
    public abstract State getMinState(String fieldName, String subFieldName);
    
    /**
     * get the average value of a subfield
     * @param fieldName the name of the major field the subfield belongs to
     * @param subFieldName the name of the subfield to be evaluated
     * @return the average value of the subfield
     */
    public abstract GeneralValue getAverageValue(String fieldName, String subFieldName);
}