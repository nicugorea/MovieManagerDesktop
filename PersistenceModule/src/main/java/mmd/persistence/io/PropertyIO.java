package mmd.persistence.io;

import java.io.File;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import mmd.common.definitions.DMDefinition;
import mmd.common.models.Property;
import mmd.util.errorhandling.ErrorHandlerUtil;
import mmd.util.io.IOUtil;

public class PropertyIO
{

    @SuppressWarnings("unchecked")
    public static <T> List<T> getDMDefinitionFromFile(final String filePath, final Class<?> type)
    {
	LinkedList<T> list = new LinkedList<T>();
	try
	{

	    Document document = IOUtil.createDOMDocumentFromXMLFile(filePath);

	    Element root = document.getDocumentElement();

	    NodeList moviesNodes = root.getElementsByTagName(((DMDefinition<T>) type.newInstance()).getName());

	    for (int i = 0; i < moviesNodes.getLength(); i++)
	    {
		DMDefinition<T> object = (DMDefinition<T>) ((DMDefinition<T>) type.newInstance()).newInstance(
			Property.getObjectFromProperties(getPropertyListFromNode(moviesNodes.item(i), type)));

		list.add((T) object);
	    }

	}
	catch (Throwable e)
	{
	    ErrorHandlerUtil.handleThrowable(e);
	}
	return list;
    }

    public static void saveDMDefinitionToFile(final DMDefinition<?> dm, final String filePath, final String rootElement)
    {
	File file = new File(filePath);

	Document document = null;
	Element root = null;
	if(!file.exists())
	{
	    file.getParentFile().mkdirs();
	    document = IOUtil.createEmptyDOMDocument();
	    root = document.createElement(rootElement);
	    document.appendChild(root);
	}
	else
	{
	    document = IOUtil.createDOMDocumentFromXMLFile(filePath);
	    root = document.getDocumentElement();
	}

	Element element = getElementFromPropertyList(document, dm.getName(), dm.getProperties());

	root.appendChild(element);

	IOUtil.saveDOMDocumentToXMLFile(document, filePath);
    }

    private static Element getElementFromPropertyList(final Document root, final String name,
	    final List<Property> children)
    {
	Element result = root.createElement(name);
	try
	{

	    for (Property child : children)
	    {
		Element element = root.createElement(child.getName());
		if(child.getValue() != null)
		{
		    element.setTextContent(child.getValue());
		}
		else
		{
		    element = getElementFromPropertyList(root, child.getName(), child.getChildren());
		}
		result.appendChild(element);
	    }

	}
	catch (Throwable e)
	{
	    ErrorHandlerUtil.handleThrowable(e);
	}
	return result;
    }

    private static List<Property> getPropertyListFromNode(final Node node, final Class<?> type)
    {

	LinkedList<Property> properties = new LinkedList<Property>();
	try
	{

	    for (Field field : type.getDeclaredFields())
	    {
		Node fieldNode = ((Element) node).getElementsByTagName(field.getName()).item(0);

		if(fieldNode.getChildNodes().getLength() == 1
			&& fieldNode.getChildNodes().item(0).getNodeType() == Node.TEXT_NODE)
		{
		    properties.add(
			    new Property(field.getName(), fieldNode.getChildNodes().item(0).getTextContent(), type));
		}
		else if(fieldNode.getChildNodes().getLength() >= 1
			&& fieldNode.getChildNodes().item(0).getNodeType() != Node.ELEMENT_NODE)
		{
		    properties.add(new Property(field.getName(), fieldNode.getChildNodes().item(0).getNodeName(),
			    getPropertyListFromNodeList(fieldNode.getChildNodes()), type));
		}

	    }
	}
	catch (Throwable e)
	{
	    ErrorHandlerUtil.handleThrowable(e);
	}
	return properties;
    }

    private static LinkedList<Property> getPropertyListFromNodeList(final NodeList list)
    {
	LinkedList<Property> result = new LinkedList<Property>();

	for (int i = 0; i < list.getLength(); i++)
	{
	    Node node = list.item(i);
	    if(node.getChildNodes().getLength() == 1
		    && node.getChildNodes().item(0).getNodeType() == Node.TEXT_NODE)
	    {
		result.add(new Property(node.getNodeName(), node.getTextContent(), List.class));
	    }

	}

	return result;
    }

}
