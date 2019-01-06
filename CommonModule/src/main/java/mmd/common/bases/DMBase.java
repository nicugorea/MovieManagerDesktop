package mmd.common.bases;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import mmd.common.definitions.DMDefinition;
import mmd.common.models.Property;
import mmd.util.errorhandling.ErrorHandlerUtil;

public class DMBase<T> implements DMDefinition<T>
{

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
		if(List.class.isInstance((field.get(this))))
		{
		    result.add(new Property(field.getName(), field.getName() + "Item", (List<?>)field.get(this),this.getClass()));
		}
		else
		{
		    result.add(new Property(field.getName(), field.get(this).toString(),this.getClass()));
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



    @Override
    public T newInstance(final Object object)
    {
	try
	{
	    return (T) this.getClass().newInstance();
	}
	catch (Throwable e)
	{
	    ErrorHandlerUtil.handleThrowable(e);
	}
	return null;
    }




}
