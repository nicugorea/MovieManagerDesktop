package mmd.persistence.util;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import mmd.util.errorhandling.ErrorHandlerUtil;

public final class IOHelper
{

    public static Document createDOMDocumentFromXMLFile(final String path)
    {

	Document document = null;
	try
	{
	    File xmlFile = new File(path);
	    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder documentBuilder = null;

	    documentBuilder = documentBuilderFactory.newDocumentBuilder();

	    document = documentBuilder.newDocument();

	    document = documentBuilder.parse(xmlFile);

	    document.getDocumentElement().normalize();

	}
	catch (Throwable e)
	{
	    ErrorHandlerUtil.handleThrowable(e);
	}

	return document;

    }

    public static Document createEmptyDOMDocument()
    {
	DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder documentBuilder = null;

	try
	{
	    documentBuilder = documentBuilderFactory.newDocumentBuilder();
	}
	catch (Throwable e)
	{
	    ErrorHandlerUtil.handleThrowable(e);
	}

	Document document = documentBuilder.newDocument();

	return document;

    }

    public static void saveDOMDocumentToXMLFile(final Document document, final String path)
    {

	try
	{
	    Transformer transformer = null;
	    transformer = TransformerFactory.newInstance().newTransformer();
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	    DOMSource domSource = new DOMSource(document);

	    File file = new File(path);

	    StreamResult streamResult = new StreamResult(file);

	    transformer.transform(domSource, streamResult);

	}
	catch (Throwable e)
	{
	    ErrorHandlerUtil.handleThrowable(e);
	}
    }

}
