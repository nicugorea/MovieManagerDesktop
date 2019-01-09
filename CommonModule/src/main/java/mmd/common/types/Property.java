package mmd.common.types;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.LinkedList;
import java.util.List;

import mmd.util.errorhandling.ErrorHandlerUtil;

public class Property
{
    public Property(final String name, final String value, final Class<?> parentClass)
    {
	this.Name = name;
	this.Value = value;
	this.parentClass = parentClass;
    }

    public Property(final String name, final String childName, final LinkedList<Property> values, final Class<?> parentClass)
    {
	this.Name = name;
	this.Value = null;
	this.propertyClass = List.class;
	this.parentClass=parentClass;
	this.Children=values;
    }

    public Property(final String name, final String childName, final List<?> values,final Class<?> parentClass)
    {
	this.Name = name;
	this.Value = null;
	this.propertyClass = List.class;
	this.parentClass=parentClass;
	this.Children=new LinkedList<Property>();
	for (Object item : values)
	{
	    Property property = new Property(childName, item.toString(), item.getClass());
	    property.setPropertyClass(item.getClass());
	    this.addChild(property);
	}
    }

    @SuppressWarnings("unchecked")
    public static <T> T getObjectFromProperties(final List<Property> properties)
    {

	if(properties.size() == 0)
	{
	    return null;
	}

	T result = null;
	try
	{
	    result = (T) properties.get(0).getParentClass().newInstance();

	    for (Property property : properties)
	    {
		setFieldValue(property, result);
	    }

	}
	catch (Throwable e)
	{
	    ErrorHandlerUtil.handleThrowable(e);
	}
	return result;
    }

    private static void setFieldValue(final Property property, final Object object)
    {
	try
	{
	    Class<?> parentClass = property.getParentClass();

	    Field field = parentClass.getDeclaredField(property.getName());
	    boolean old = field.isAccessible();
	    field.setAccessible(true);
	    field.set(object, property.toObject());
	    field.setAccessible(old);
	}
	catch (Exception e)
	{
	    ErrorHandlerUtil.handleThrowable(e);
	}
    }

    private List<Property> Children = null;

    private String Name;
    private Class<?> parentClass;
    private Class<?> propertyClass;
    private String Value;

    public void addChild(final Property property)
    {
	if(this.Children == null)
	{
	    this.Children = new LinkedList<Property>();
	}
	this.Children.add(property);
    }

    public List<Property> getChildren()
    {
	return this.Children;
    }

    public String getName()
    {
	return this.Name;
    }

    public Class<?> getParentClass()
    {
	return this.parentClass;
    }

    public Class<?> getPropertyClass()
    {
	return this.propertyClass;
    }

    public String getValue()
    {
	return this.Value;
    }

    public boolean haveChildren()
    {
	return this.Children != null;
    }

    public void setName(final String name)
    {
	this.Name = name;
    }

    public void setParentClass(final Class<?> parentClass)
    {
	this.parentClass = parentClass;
    }

    public void setPropertyClass(final Class<?> propertyClass)
    {
	this.propertyClass = propertyClass;
    }

    public void setValue(final String value)
    {
	this.Value = value;
    }

    private Object toObject()
    {
	Object object = null;
	try
	{
	    Field field = this.getParentClass().getDeclaredField(this.getName());
	    Class<?> fieldClass = field.getType();

	    if(fieldClass.equals(String.class))
	    {
		object = this.getValue();
	    }
	    else if(fieldClass.equals(float.class))
	    {
		object = Float.parseFloat(this.getValue());
	    }
	    else if(fieldClass.equals(List.class))
	    {
		List<Object> list = new LinkedList<Object>();
		for (Property property : this.getChildren())
		{
		    ParameterizedType itemType = (ParameterizedType) field.getGenericType();
		    Class<?> itemClass = (Class<?>) itemType.getActualTypeArguments()[0];
		    property.setPropertyClass(itemClass);
		    list.add(property.toObjectFromItem());
		}
		object = list;
	    }
	}
	catch (Throwable e)
	{
	    ErrorHandlerUtil.handleThrowable(e);
	}
	return object;
    }

    private Object toObjectFromItem()
    {
	Object object = null;
	try
	{
	    if(this.propertyClass.equals(String.class))
	    {
		object = this.getValue();
	    }
	    else if(this.propertyClass.equals(float.class))
	    {
		object = Float.parseFloat(this.getValue());
	    }
	    else if(this.propertyClass.equals(List.class))
	    {
		List<Object> list = new LinkedList<Object>();
		for (Property property : this.getChildren())
		{
		    list.add(property.toObject());
		}
		object = list;
	    }
	}
	catch (Throwable e)
	{
	    ErrorHandlerUtil.handleThrowable(e);
	}
	return object;

    }
}
