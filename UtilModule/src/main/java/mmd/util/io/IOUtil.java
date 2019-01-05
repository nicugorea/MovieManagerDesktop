package mmd.util.io;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import mmd.util.MagicValues;
import mmd.util.UtilModule;
import mmd.util.errorhandling.ErrorHandlerUtil;


public class IOUtil
{
    public static void copyFile(final String source, final String target) {

	Path src = Paths.get(source);
	Path trg = Paths.get(target);
	try
	{
	    Files.copy(src, trg, StandardCopyOption.REPLACE_EXISTING);
	}
	catch (Throwable e)
	{
	    ErrorHandlerUtil.handleThrowable(e);
	}
    }

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

    public static Document createEmptyDOMDocumetWithParentTag(final String name) {

	Document document = createEmptyDOMDocument();
	document.appendChild(document.createElement(MagicValues.MoviesTagName));
	return document;
    }

    public static boolean existFile(final String path) {
	return new File(path).exists();
    }

    public static String getImagePath(final String name) {
	return MagicValues.MovieThumbnailPath+name;

    }

    public static URL getResourcePath(final String name) {
	return UtilModule.class.getClassLoader()
		.getResource(name);
    }

    public static String getStringURLOfPath(final String imagePath) throws MalformedURLException
    {
	return new File(imagePath).toURI().toURL().toString();
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
