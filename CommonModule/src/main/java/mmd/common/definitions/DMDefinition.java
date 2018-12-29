package mmd.common.definitions;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import mmd.common.models.Property;
import mmd.util.errorhandling.ErrorHandlerUtil;

public abstract class DMDefinition<T>
{



    public T getFromProperties(final List<Property> properties)
    {
	try
	{
	    for (Property property : properties)
	    {
		Field field = null;

		field = this.getClass().getDeclaredField(property.getName());
		if(field != null)
		{
		    if(property.getValue() != null)
		    {
			boolean old = field.isAccessible();
			field.setAccessible(true);
			Object value = null;
			String typeName = field.getType().getName();
			if(typeName.equals(String.class.getTypeName()))
			{
			    value = property.getValue();
			}
			else if(typeName.equals(float.class.getTypeName()))
			{
			    value = Float.parseFloat(property.getValue());
			}
			field.set(this, value);
			field.setAccessible(old);
		    }
		    else if(property.getChildren()!=null){
		    }
		}
	    }
	}
	catch (Throwable e)
	{
	    ErrorHandlerUtil.handleThrowable(e);
	}

	return (T) this;
    }

    public abstract String getName();

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

}

