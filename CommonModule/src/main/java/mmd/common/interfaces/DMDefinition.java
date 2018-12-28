package mmd.common.interfaces;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import mmd.common.models.Property;

public interface DMDefinition<T>
{

    @SuppressWarnings("unchecked")
    default T getFromProperties(final List<Property> properties)
    {
	for (Property property : properties)
	{
	    Field field = null;
	    try
	    {
		field = this.getClass().getDeclaredField(property.getName());
	    }
	    catch (NoSuchFieldException e)
	    {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    catch (SecurityException e)
	    {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    if(field != null)
	    {
		if(property.getValue() != null)
		{
		    boolean old = field.isAccessible();
		    field.setAccessible(true);
		    Object value =null;
		    String typeName = field.getType().getName();
		    if(typeName.equals(String.class.getTypeName()))
		    {
			value=property.getValue();
		    }
		    else if(typeName.equals(float.class.getTypeName()))
		    {
			value=Float.parseFloat(property.getValue());
		    }
		    try
		    {
			field.set(this,value);
		    }
		    catch (IllegalArgumentException | IllegalAccessException e)
		    {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
		    field.setAccessible(old);
		}
	    }
	}

	return (T) this;
    }

    String getName();

    default ArrayList<Property> getProperties(){
	ArrayList<Property> result = new ArrayList<>();


	for(Field field : this.getClass().getDeclaredFields())
	{
	    boolean old = field.isAccessible();
	    field.setAccessible(true);
	    try
	    {
		if(!field.get(this).getClass().isArray()) {

		    result.add(new Property(field.getName(), field.get(this).toString()));
		}else {
		    result.add(new Property(field.getName(), field.getName()+"Item",(List<T>) field.get(this)));
		}
	    }
	    catch (IllegalArgumentException e)
	    {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    catch (IllegalAccessException e)
	    {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    field.setAccessible(old);
	}

	return result;
    }
}
