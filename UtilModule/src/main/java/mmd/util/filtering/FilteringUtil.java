package mmd.util.filtering;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import org.xml.sax.ErrorHandler;

import mmd.util.errorhandling.ErrorHandlerUtil;

public class FilteringUtil
{
	public static <T> List<T> filter(List<T> list, Field field,
	        Object value)
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
			catch (IllegalArgumentException
			        | IllegalAccessException e)
			{
				ErrorHandlerUtil.handleThrowable(e);
			}
			return filter;
		});
		field.setAccessible(old);
		return result;
	}
	
}
