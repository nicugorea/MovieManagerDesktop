package mmd.common.models;

import java.util.LinkedList;
import java.util.List;

public class Property
{
    public Property(final String name, final String value)
    {
	this.Name = name;
	this.Value = value;
    }

    public <T> Property(final String name, final String childName, final List<T> values)
    {
	this.Name = name;
	this.Value = null;
	for(Object item : values) {
	    this.addChild(new Property(childName, item.toString()));
	}
    }

    private LinkedList<Property> Children = null;
    private String Name;
    private String Value;

    public void addChild(final Property prop)
    {
	if(this.Children == null)
	{
	    this.Children = new LinkedList<Property>();
	}
	this.Children.add(prop);
    }

    public LinkedList<Property> getChildren()
    {
	return this.Children;
    }

    public String getName()
    {
	return this.Name;
    }

    public String getValue()
    {
	return this.Value;
    }

    public void setName(final String name)
    {
	this.Name = name;
    }

    public void setValue(final String value)
    {
	this.Value = value;
    }
}
