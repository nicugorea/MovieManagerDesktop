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

/**
 * 
 * Util for input and output operations
 */
public class IOUtil
{
	/**
	 * Method to copy a file from source path to target path replacing the existent file if there is one
	 * 
	 * @param source Source path
	 * @param target Target path
	 */
	public static void copyFile(final String source, final String target)
	{
		
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
	
	/**
	 * Method to create a DOM document from a XML File
	 * 
	 * @param path Path of the XML File
	 * @return DOM Document
	 */
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
	
	/**
	 *  Method to create a empty DOM document
	 * 
	 * @return DOM Document
	 */
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

	/**
	 *  Method to create a empty DOM document with a root tag
	 * 
	 * @param name Root Tag name
	 * @return DOM Document
	 */
	public static Document createEmptyDOMDocumetWithParentTag(final String name)
	{
		
		Document document = createEmptyDOMDocument();
		document.appendChild(document.createElement(name));
		return document;
	}
	
	/**
	 * Method to check if a file exists at given path
	 * 
	 * @param path File path
	 * @return boolean value telling if the File exist or not
	 */
	public static boolean existFile(final String path)
	{
		return new File(path).exists();
	}
	
	/**
	 * Method to compute a image name with default movie image path
	 * 
	 * @param name Image name with extension
	 * @return Computed path to the image
	 */
	public static String getImagePath(final String name)
	{
		return MagicValues.MovieThumbnailPath + name;
	}
	
	/**
	 * Method to get a resource URL or null if it does not exist
	 * 
	 * @param name Resource name including packages name
	 * @return Resource URL
	 */
	public static URL getResourcePath(final String name)
	{
		return UtilModule.class.getClassLoader().getResource(name);
	}
	
	/**
	 * Method to convert a string path to a URL path in string form
	 * 
	 * @param path Path to be parsing
	 * @return URL created from path using a file
	 * @throws MalformedURLException
	 */
	public static String getStringURLOfPath(final String path) throws MalformedURLException
	{
		return new File(path).toURI().toURL().toString();
	}
	
	/**
	 * Method to save a DOM Document to XML File at given path
	 * 
	 * @param document Document to save
	 * @param path Path where to save the XML File
	 */
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
	
	/**
	 * Method to get a file extension
	 * 
	 * @param file File to get extension from
	 * @return File extension
	 */
	public static String getFileExtension(File file)
	{
		String fileName = file.getName();
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
		    return fileName.substring(fileName.lastIndexOf("."));
		else return "";
	}
	
}
