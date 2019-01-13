package mmd.common.types;

/**
 * A Class used for transferring any kind of data using {@link Tuple} of
 * {@link Object} and {@link Class}
 */
public class GenericData
{
	private Tuple<Object, Class<?>> data = null;
	
	/**
	 * Constructor with default parameters
	 * 
	 * @param obj Object
	 * @param cls Class
	 */
	public GenericData(Object obj, Class<?> cls)
	{
		data = new Tuple<Object, Class<?>>(obj, cls);
	}
	
	/**
	 * Constructor with no arguments
	 */
	public GenericData()
	{
		this(null, null);
	}
	
	/**
	 * Set Object
	 * 
	 * @param value Object to set
	 */
	public void setDataValue(Object value)
	{
		if (data != null) data.setFirst(value);
		else throw new NullPointerException();
	}
	
	/**
	 * Set Class
	 * 
	 * @param cls Class to set
	 */
	public void setDataType(Class<?> cls)
	{
		
		if (data != null) data.setSecond(cls);
		else throw new NullPointerException();
	}
	
	/**
	 * Get Object
	 * 
	 * @return Object part of data
	 */
	public Object getDataValue()
	{
		
		if (data != null) return data.getFirst();
		else throw new NullPointerException();
	}
	
	/**
	 * Get Class
	 * 
	 * @return Class of the data
	 */
	public Class<?> getDataType()
	{
		if (data != null) return data.getSecond();
		else throw new NullPointerException();
	}
}
