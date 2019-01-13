package mmd.common.types;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.LinkedList;
import java.util.List;

import mmd.util.errorhandling.ErrorHandlerUtil;

/**
 * Class that define a Property 
 */
public class Property
{
	/**
	 * Constructor of a simple property
	 * @param name Property name
	 * @param value Property value
	 * @param parentClass Class of property parent
	 */
	public Property(final String name, final String value, final Class<?> parentClass)
	{
		this.Name = name;
		this.Value = value;
		this.parentClass = parentClass;
	}
	
	/**
	 * Constructor of property for a List of primitive objects
	 * @param name Property name
	 * @param childName List property child name
	 * @param values List of properties
	 * @param parentClass Class of property parent
	 */
	public Property(final String name, final String childName, final LinkedList<Property> values,
	        final Class<?> parentClass)
	{
		this.Name = name;
		this.Value = null;
		this.propertyClass = List.class;
		this.parentClass = parentClass;
		this.Children = values;
	}
	
	/**
	 * Constructor of property for a List of advanced objects
	 * @param name Property name
	 * @param childName List property child name
	 * @param values List of objects
	 * @param parentClass Class of property parent
	 */
	public Property(final String name, final String childName, final List<?> values,
	        final Class<?> parentClass)
	{
		this.Name = name;
		this.Value = null;
		this.propertyClass = List.class;
		this.parentClass = parentClass;
		this.Children = new LinkedList<Property>();
		for (Object item : values)
		{
			Property property = new Property(childName, item.toString(), item.getClass());
			property.setPropertyClass(item.getClass());
			this.addChild(property);
		}
	}
	
	/**
	 * Get Object from List of properties
	 * @param properties List of properties to be converted
	 * @return Converted object
	 */
	public static <T> T getObjectFromProperties(final List<Property> properties)
	{
		
		if (properties.size() == 0) { return null; }
		
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
	
	/**
	 * Set field value of given object using property
	 * @param property Property that hold value and property name
	 * @param object Object to set value
	 */
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
	
	/**
	 * Add a child property
	 * @param property Property to add
	 */
	public void addChild(final Property property)
	{
		if (this.Children == null)
		{
			this.Children = new LinkedList<Property>();
		}
		this.Children.add(property);
	}
	
	/**
	 * Get child properties
	 * @return Children List
	 */
	public List<Property> getChildren()
	{
		return this.Children;
	}
	
	/**
	 * Get Field name
	 * @return Field name
	 */
	public String getName()
	{
		return this.Name;
	}
	
	/**
	 * Get Parent class
	 * @return object class of field
	 */
	public Class<?> getParentClass()
	{
		return this.parentClass;
	}
	
	/**
	 * Get filed value class
	 * @return field value class
	 */
	public Class<?> getPropertyClass()
	{
		return this.propertyClass;
	}
	
	/**
	 * Get Property Value
	 * @return Object
	 */
	public String getValue()
	{
		return this.Value;
	}
	
	/**
	 * Check if current property has Children
	 * @return boolean value if true exist at least one child and false elsewhere
	 */
	public boolean haveChildren()
	{
		return this.Children != null;
	}
	
	/**
	 * Set property field name
	 * @param name Name to set
	 */
	public void setName(final String name)
	{
		this.Name = name;
	}
	
	/**
	 * Set Parent class
	 * @param parentClass Class to set
	 */
	public void setParentClass(final Class<?> parentClass)
	{
		this.parentClass = parentClass;
	}
	
	/**
	 * Set field class
	 * @param propertyClass Class to set
	 */
	public void setPropertyClass(final Class<?> propertyClass)
	{
		this.propertyClass = propertyClass;
	}
	
	/**
	 * Set Field value
	 * @param value Object to set
	 */
	public void setValue(final String value)
	{
		this.Value = value;
	}
	
	/**
	 * Create object from current property
	 * @return Object created from this property
	 */
	private Object toObject()
	{
		Object object = null;
		try
		{
			Field field = this.getParentClass().getDeclaredField(this.getName());
			Class<?> fieldClass = field.getType();
			
			if (fieldClass.equals(String.class))
			{
				object = this.getValue();
			}
			else if (fieldClass.equals(float.class))
			{
				object = Float.parseFloat(this.getValue());
			}
			else if (fieldClass.equals(List.class))
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
	
	/**
	 * Create object from current List Item property
	 * @return Object created from this property
	 */
	private Object toObjectFromItem()
	{
		Object object = null;
		try
		{
			if (this.propertyClass.equals(String.class))
			{
				object = this.getValue();
			}
			else if (this.propertyClass.equals(float.class))
			{
				object = Float.parseFloat(this.getValue());
			}
			else if (this.propertyClass.equals(List.class))
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
