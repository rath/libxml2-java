package org.xmlsoft;

import org.xml.sax.*;

/**
 * Receive notification of various events of a document.
 * This is the main interface that most SAX applications implement.
 *
 * @author Jang-Ho Hwang, rath@xrath.com
 */
public interface SAXHandler {
	/**
	 * Receive notification of the beginning of the document.
	 *
	 * By default, do nothing. Application writers may override this method in a subclass
	 * to take specific actions at the beginning of a document (such as allocating the root
	 * node of a tree or creating an output file).
	 */
	public void startDocument();

	/**
	 * Receive notification of the end of the document.
	 *
	 * By default, do nothing. Application writers may override this method in a subclass
	 * to take specific actions at the end of a document (such as finalising a tree or
	 * closing an output file).
	 */
	public void endDocument();

	/**
	 * Receive notification of the start of a Namespace mapping.
	 *
	 * By default, do nothing. Application writers may override this method in a subclass
	 * to take specific actions at the start of each Namespace prefix scope
	 * (such as storing the prefix mapping).
	 * @param prefix The namespace prefix being declared.
	 * @param uri The namespace URI mapped to the prefix.
	 */
	public void startPrefixMapping(String prefix, String uri);

	/**
	 * Receive notification of the start of a Namespace mapping.
	 *
	 * By default, do nothing. Application writers may override this method in a subclass
	 * to take specific actions at the end of each prefix mapping.
	 *
	 * @param prefix The namespace prefix being declared.
	 */
	public void endPrefixMapping(String prefix);

	/**
	 * Receive notification of the start of an element.
	 *
	 * By default, do nothing. Application writers may override this method in a subclass
	 * to take specific actions at the start of each element (such as allocating a new tree node
	 * or writing output to a file).
	 *
	 * @param uri The namespace URI, or the empty string if the element has no namespace URI of if
	 *            namespace processing is not being performed.
	 * @param localName The local name (without prefix), or the empty string if namespace processing
	 *                  is not being performed.
	 * @param qName The qualified name (with prefix), or the empty string if qualified names are not available.
	 * @param atts The attributes attached to the element. If there are no attributes, it should be
	 *             an empty Attributes object.
	 */
	public void startElement(String uri, String localName, String qName, Attributes atts);

	/**
	 * Receive notification of the end of an element.
	 *
	 * By default, do nothing. Application writers may override this method in a subclass
	 * to take specific actions at the end of each element (such as finalising a tree nodes
	 * or writing output to a file).
	 *
	 * @param uri The namespace URI, or the empty string if the element has no namespace URI of if
	 *            namespace processing is not being performed.
	 * @param localName The local name (without prefix), or the empty string if namespace processing
	 *                  is not being performed.
	 * @param qName The qualified name (with prefix), or the empty string if qualified names are not available.
	 */
	public void endElement(String uri, String localName, String qName);

	/**
	 * Receive notification of character data inside an element.
	 *
	 * By default, do nothing. Application writers may override this method to take specific
	 * actions for each chunk of character data (such as adding the data to a node or buffer,
	 * or printing it to a file).
	 *
	 * @param ch The characters.
	 * @param start The start position in the character array.
	 * @param length The number of characters to use from the character array.
	 */
	public void characters(char[] ch, int start, int length);

	/**
	 * Receive notification of ignorable whitespace in element content.
	 *
	 * By default, do nothing. Application writers may override this method to take specific
	 * actions for each chunk of ignorable whitespace (such as adding data to a node or buffer,
	 * or printing it to a file).
	 *
	 * @param ch The whitespace characters.
	 * @param start The start position in the character array.
	 * @param length The number of characters to use from the character array.
	 */
	public void ignorableWhitespace(char[] ch, int start, int length);

	/**
	 * Receive notification of processing instruction.
	 *
	 * By default, do nothing. Application writers may override this method in a subclass
	 * to take specific actions for each processing instruction, such as setting status
	 * variables or invoking other methods.
	 *
	 * @param target The processing instruction target.
	 * @param data The processing instruction data, or null if none is supplied.
	 */
	public void processingInstruction(String target, String data);

	/**
	 * Receive notification of a parser warning.
	 *
	 * The default implementation does nothing. Application writers may override this method in a subclass
	 * to take specific actions for each warning, such as inserting the message in a log file or printing it to the console.
	 *
	 * @param exception The warning information with an exception.
	 */
	public void warning(LibXmlException exception);

	/**
	 * Receive notification of a parser warning.
	 *
	 * The default implementation does nothing. Application writers may override this method in a subclass
	 * to take specific actions for each error, such as inserting the message in a log file or printing it to the console.
	 *
	 * @param exception The error information with an exception.
	 */
	public void error(LibXmlException exception);

	/**
	 * Report a fatal XML parsing error.
	 *
	 * The default implementation does nothing. Application writers may override this method
	 * in a subclass if they need to take specific actions for each fatal error (such as collecting all
	 * of the errors into a single report): in any case, the application must stop all regular processing
	 * when this method is invoked, since the document is no longer reliable, and the parser may no longer
	 * report parsing events.
	 *
	 * @param exception The error information with an exception.
	 */
	public void fatalError(LibXmlException exception);

	public void notationDecl(String name, String publicId, String systemId);
	public void unparsedEntityDecl(String name, String publicId, String systemId, String notationName);

	/**
	 * Receive a Locator object for document events.
	 *
	 * By default, do nothing. Application writers may override this method in a subclass
	 * if they wish to store the locator for use with other document events.
	 *
	 * @param locator A locator for all SAX document events.
	 */
	public void setDocumentLocator(Locator locator);
}

//class x implements DTDHandler {
//}
