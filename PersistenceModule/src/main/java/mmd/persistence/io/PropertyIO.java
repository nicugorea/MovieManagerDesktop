package mmd.persistence.io;

import java.io.File;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import mmd.common.bases.DMBase;
import mmd.common.definitions.DMDefinition;
import mmd.common.models.MovieDM;
import mmd.common.types.Property;
import mmd.util.errorhandling.ErrorHandlerUtil;
import mmd.util.io.IOUtil;

/**
 * Util for input and output of Properties
 */
public class PropertyIO {

	/**
	 * Add an element to a XML File
	 * <p>
	 * If file does not exist, create a new one with specified root
	 * </p>
	 * 
	 * @param dm          Element to add
	 * @param filePath    Path to the file
	 * @param rootElement Root tag
	 */
	public static void addDMDefinitionToFile(final DMDefinition<?> dm, final String filePath,
			final String rootElement) {
		File file = new File(filePath);

		Document document = null;
		Element root = null;
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			document = IOUtil.createEmptyDOMDocument();
			root = document.createElement(rootElement);
			document.appendChild(root);
		} else {
			document = IOUtil.createDOMDocumentFromXMLFile(filePath);
			root = document.getDocumentElement();
		}

		Element element = getElementFromPropertyList(document, dm.getName(), dm.getProperties());

		root.appendChild(element);

		IOUtil.saveDOMDocumentToXMLFile(document, filePath);
	}

	/**
	 * Get a list of specified data model from specified file
	 * 
	 * @param filePath Path to file
	 * @param type     Type of data model
	 * @return List of data model
	 */
	public static <T> List<T> getDMDefinitionsFromFile(final String filePath, final Class<?> type) {
		LinkedList<T> list = new LinkedList<T>();
		try {

			Document document = IOUtil.createDOMDocumentFromXMLFile(filePath);

			Element root = document.getDocumentElement();

			NodeList childNodes = root.getElementsByTagName(((DMDefinition<T>) type.newInstance()).getName());

			for (int i = 0; i < childNodes.getLength(); i++) {
				T object = ((DMDefinition<T>) type.newInstance()).newInstance(
						Property.getObjectFromProperties(getPropertyListFromNode(childNodes.item(i), type)));

				list.add((T) object);
			}

		} catch (Throwable e) {
			ErrorHandlerUtil.handleThrowable(e);
		}
		return list;
	}

	/**
	 * Remove an element from a XML File
	 * 
	 * @param dm
	 * @param dm       Element to remove
	 * @param filePath Path to the file
	 * @throws Exception If file does not exist
	 */
	public static void removeDMDefinitionFromFile(final DMBase dm, Field field, final String filePath)
			throws Exception {
		File file = new File(filePath);

		Document document = null;
		Element root = null;
		if (!file.exists()) {
			throw new Exception("File" + filePath + "does not exist!");
		} else {
			document = IOUtil.createDOMDocumentFromXMLFile(filePath);
			root = document.getDocumentElement();
		}

		Node node = null;
		for (int i = 0; i < root.getChildNodes().getLength(); i++) {
			if (root.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
				boolean old = field.isAccessible();
				field.setAccessible(true);

				Element elemnt = (Element) root.getChildNodes().item(i);
				NodeList nodeList = elemnt.getElementsByTagName(field.getName());
				String value = nodeList.item(0).getTextContent();

				if (value.equals(field.get(dm))) {
					node = root.getChildNodes().item(i);
					break;
				}

				field.setAccessible(old);
			}

		}

		node.getParentNode().removeChild(node);

		// root.removeChild(node);

		IOUtil.saveDOMDocumentToXMLFile(document, filePath);
	}

	/**
	 * Create a DOM Element from a List of properties
	 * 
	 * @param root     DOM Document to get element from
	 * @param name     Tag name of the new element
	 * @param children List of properties to generate child elements of new element
	 * @return
	 */
	private static Element getElementFromPropertyList(final Document root, final String name,
			final List<Property> children) {
		Element result = root.createElement(name);
		try {

			for (Property child : children) {
				Element element = root.createElement(child.getName());
				if (child.getValue() != null) {
					element.setTextContent(child.getValue());
				} else {
					element = getElementFromPropertyList(root, child.getName(), child.getChildren());
				}
				result.appendChild(element);
			}

		} catch (Throwable e) {
			ErrorHandlerUtil.handleThrowable(e);
		}
		return result;
	}

	/**
	 * Get List of properties from DOM Element(Node)
	 * 
	 * @param node Element to be converted
	 * @param type Node class
	 * @return List of properties generated from Node
	 */
	private static List<Property> getPropertyListFromNode(final Node node, final Class<?> type) {

		LinkedList<Property> properties = new LinkedList<Property>();
		try {

			for (Field field : type.getDeclaredFields()) {
				Node fieldNode = ((Element) node).getElementsByTagName(field.getName()).item(0);

				if (fieldNode.getChildNodes().getLength() == 1
						&& fieldNode.getChildNodes().item(0).getNodeType() == Node.TEXT_NODE) {
					properties.add(
							new Property(field.getName(), fieldNode.getChildNodes().item(0).getTextContent(), type));
				} else if (fieldNode.getChildNodes().getLength() >= 1
						&& fieldNode.getChildNodes().item(0).getNodeType() != Node.ELEMENT_NODE) {
					properties.add(new Property(field.getName(), fieldNode.getChildNodes().item(0).getNodeName(),
							getPropertyListFromNodeList(fieldNode.getChildNodes()), type));
				}

			}
		} catch (Throwable e) {
			ErrorHandlerUtil.handleThrowable(e);
		}
		return properties;
	}

	/**
	 * Create a list of property from a list of Node
	 * 
	 * @param list List of nodes to be generated
	 * @return List of properties
	 */
	private static LinkedList<Property> getPropertyListFromNodeList(final NodeList list) {
		LinkedList<Property> result = new LinkedList<Property>();

		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			if (node.getChildNodes().getLength() == 1 && node.getChildNodes().item(0).getNodeType() == Node.TEXT_NODE) {
				result.add(new Property(node.getNodeName(), node.getTextContent(), List.class));
			}

		}

		return result;
	}

}
