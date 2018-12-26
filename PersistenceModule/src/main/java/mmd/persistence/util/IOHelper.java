package mmd.persistence.util;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;


public final class IOHelper
{

    public static Document createDOMDocumentFromXMLFile(final String path)
    {

	File xmlFile = new File(path);
	DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder documentBuilder = null;

	try
	{
	    documentBuilder = documentBuilderFactory.newDocumentBuilder();
	}
	catch (ParserConfigurationException e)
	{
	}

	Document document = documentBuilder.newDocument();

	try
	{
	    document = documentBuilder.parse(xmlFile);
	}
	catch (SAXException e)
	{
	}
	catch (IOException e)
	{
	}

	document.getDocumentElement().normalize();

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
	catch (ParserConfigurationException e)
	{
	}

	Document document = documentBuilder.newDocument();

	return document;

    }

    public static void saveDOMDocumentToXMLFile(final Document document, final String path)
    {

	Transformer transformer = null;
	try
	{
	    transformer = TransformerFactory.newInstance().newTransformer();
	}
	catch (TransformerConfigurationException e)
	{
	}
	catch (TransformerFactoryConfigurationError e)
	{
	}
	transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	DOMSource domSource = new DOMSource(document);

	File file = new File(path);
	if(!file.exists()) {
	    file.getParentFile().mkdirs();
	}
	StreamResult streamResult = new StreamResult(file);


	try
	{
	    transformer.transform(domSource, streamResult);
	}
	catch (TransformerException e)
	{
	}

    }

}
