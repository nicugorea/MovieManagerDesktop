package mmd.common.bases;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import mmd.common.definitions.DMDefinition;
import mmd.common.types.Property;
import mmd.util.errorhandling.ErrorHandlerUtil;

/**
 * 
 * Abstract class that has basic methods and fields of a Data Model
 *
 * @param <T> Type of Data Model
 */
public abstract class DMBase<T> implements DMDefinition<T>
{
	/**
	 * Method to get the name of this Data Model
	 */
	@Override
	public String getName()
	{
		try
		{
			return "";
		}
		catch (Throwable e)
		{
			ErrorHandlerUtil.handleThrowable(e);
		}
		return null;
	}
	
	/**
	 * Implementation of the method to get properties list from a Data Model
	 * @return a {@link List} of {@link Property} 
	 */
	@Override
	public ArrayList<Property> getProperties()
	{
		ArrayList<Property> result = new ArrayList<>();
		try
		{
			for (Field field : this.getClass().getDeclaredFields())
			{
				boolean old = field.isAccessible();
				field.setAccessible(true);
				if (List.class.isInstance((field.get(this))))
				{
					result.add(new Property(field.getName(), field.getName() + "Item",
					        (List<?>) field.get(this), this.getClass()));
				}
				else
				{
					Object value = field.get(this);
					String val = (value != null) ? value.toString() : "";
					result.add(new Property(field.getName(), val, this.getClass()));
				}
				field.setAccessible(old);
			}
		}
		catch (Throwable e)
		{
			ErrorHandlerUtil.handleThrowable(e);
		}
		
		return result;
	}
	
}
