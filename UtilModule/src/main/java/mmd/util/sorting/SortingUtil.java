package mmd.util.sorting;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import mmd.util.errorhandling.ErrorHandlerUtil;

/**
 * 
 * Util for sorting List
 *
 */
public class SortingUtil
{
	/**
	 * 
	 * Method to sort a list ascending or descending by a field of the list item
	 * class
	 * 
	 * @param list        The list to be sorted
	 * @param isAscending A boolean parameter telling if the sorting should be true
	 *                    if ascending or false if descending
	 * @param field       Field of the list item class to be sorted by
	 * @return Sorted list
	 */
	public static <T> List<T> sort(List<T> list, boolean isAscending, Field field)
	{
		List<T> result = new LinkedList<T>(list);
		boolean old = field.isAccessible();
		field.setAccessible(true);
		result.sort(new Comparator<T>()
		{
			
			@Override
			public int compare(T o1, T o2)
			{
				int cmp = 0;
				try
				{
					cmp = field.get(o1).toString().compareTo(field.get(o2).toString());
				}
				catch (Exception e)
				{
					ErrorHandlerUtil.handleThrowable(e);
				}
				if (!isAscending) cmp *= -1;
				return cmp;
			}
		});
		field.setAccessible(old);
		return result;
	}
	
}
