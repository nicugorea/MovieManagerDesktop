package mmd.common.bases;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import mmd.common.definitions.DMDefinition;
import mmd.common.types.Property;
import mmd.util.errorhandling.ErrorHandlerUtil;

public abstract class DMBase<T> implements DMDefinition<T>
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
		    Object value = field.get(this);
		    String val=(value!=null)? value.toString():"";
		    result.add(new Property(field.getName(), val,this.getClass()));
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
