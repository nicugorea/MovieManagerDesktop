package mmd.common.types;

/**
 * 
 * Class that store two objects specifying their types
 *
 * @param <First> First object class
 * @param <Second> Second object class
 */
public class Tuple<First, Second>
{
	/**
	 * Default constructor
	 * 
	 * @param first  First object
	 * @param second Second object
	 */
	public Tuple(final First first, final Second second)
	{
		this.setFirst(first);
		this.setSecond(second);
	}
	
	/**
	 * Get first object
	 * 
	 * @return First Object
	 */
	public First getFirst()
	{
		return first;
	}
	
	/**
	 * Set first object
	 * 
	 * @param first Object to set
	 */
	public void setFirst(First first)
	{
		this.first = first;
	}
	
	/**
	 * Get second object
	 * 
	 * @return Second object
	 */
	public Second getSecond()
	{
		return second;
	}
	
	/**
	 * Set second object
	 * 
	 * @param second Object to set
	 */
	public void setSecond(Second second)
	{
		this.second = second;
	}
	
	private First first = null;
	private Second second = null;
}
