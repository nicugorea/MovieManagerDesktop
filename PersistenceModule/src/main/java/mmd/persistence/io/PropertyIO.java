package mmd.persistence.io;

import java.io.File;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import mmd.common.definitions.DMDefinition;
import mmd.common.models.Property;
import mmd.persistence.util.IOHelper;

public class PropertyIO
{

    public static void saveDMDefinition(final DMDefinition<?> dm, final String filePath, final String rootElement)
    {
	File file = new File(filePath);

	Document document = null;
	Element root = null;
	if(!file.exists()) {
	    file.getParentFile().mkdirs();
	    document = IOHelper.createEmptyDOMDocument();
	    root = document.createElement(rootElement);
	    document.appendChild(root);
	}
	else {
	    document = IOHelper.createDOMDocumentFromXMLFile(filePath);
	    root = document.getDocumentElement();
	}

	Element element = getElementFromPropertyList(document, dm.getName(), dm.getProperties());

	root.appendChild(element);

	IOHelper.saveDOMDocumentToXMLFile(document, filePath);
    }


    private static Element getElementFromPropertyList(final Document root, final String name, final List<Property> children)
    {
	Element result = root.createElement(name);

	for(Property child : children) {
	    Element element = root.createElement(child.getName());
	    if(child.getValue()!=null)
	    {
		element.setTextContent(child.getValue());
	    }
	    else {
		element = getElementFromPropertyList(root,child.getName(),child.getChildren());
	    }
	    result.appendChild(element);
	}

	return result;
    }
}
