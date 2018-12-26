package mmd.persistence.io;

import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import mmd.common.models.MovieDM;
import mmd.common.models.Property;
import mmd.persistence.util.IOHelper;

public class MovieIO
{
    private final static String filePath = System.getProperty("user.dir") + "/testing.xml";

    public static void saveMovieToXmlFile()
    {

	MovieDM dm = new MovieDM();

	dm.setTitle("Movie title");
	dm.setDescription("A description right here");
	dm.setIMDbID("adasdasd-a-d12-e1d");
	dm.setImgPath("");
	dm.setScore(9.5f);
	LinkedList<String> list = new LinkedList<String>();
	list.add("SF");
	list.add("Fighting");
	list.add("Anime");
	dm.setCategories(list);

	Document document = IOHelper.createEmptyDOMDocument();

	Element root = document.createElement("Root");

	Element element = getElementFromPropertyList(document, "Movie", dm.getProperties());

	root.appendChild(element);
	document.appendChild(root);

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
		element.appendChild(getElementFromPropertyList(root,child.getName(),child.getChildren()));
	    }
	    result.appendChild(element);
	}

	return result;
    }



}
