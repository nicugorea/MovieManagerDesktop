package mmd.util.filtering;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import org.xml.sax.ErrorHandler;

import mmd.util.errorhandling.ErrorHandlerUtil;

/**
 * 
 * Util for filtering a list 
 */
public class FilteringUtil
{
	/**
	 * Filtering a list in a way that field will have the value of the parameter value
	 * 
	 * @param list List to be filtered
	 * @param field Field that have to be checked for filtering
	 * @param value Value that is required for comparing
	 * @return Filtered List
	 */
	public static <T> List<T> filter(List<T> list, Field field, Object value)
	{
		List<T> result = new LinkedList<T>(list);
		boolean old = field.isAccessible();
		field.setAccessible(true);
		result.removeIf((o) ->
		{
			boolean filter = false;
			try
			{
				if (field.get(o) instanceof List)
				{
					filter = !((List) field.get(o)).contains(value);
				}
				else
				{
					filter = !field.get(o).equals(value);
				}
			}
			catch (IllegalArgumentException | IllegalAccessException e)
			{
				ErrorHandlerUtil.handleThrowable(e);
			}
			return filter;
		});
		field.setAccessible(old);
		return result;
	}
	
}
