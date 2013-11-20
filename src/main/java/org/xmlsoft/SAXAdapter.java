package org.xmlsoft;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;

/**
 * Default base class for SAX2 event handlers.
 *
 * <p>This class is available as a convenience base class for SAX2 applications.</p>
 * <p>Application writers can extend this class when they need to implement only part of an interface;
 * parser writers can instantiate this class to provide default handlers when the application has not
 * supplied its own.</p>
 *
 * @author Jang-Ho Hwang, rath@xrath.com
 */
public class SAXAdapter implements SAXHandler {
	@Override
	public void startDocument() {

	}

	@Override
	public void endDocument() {

	}

	@Override
	public void startPrefixMapping(String prefix, String uri) {

	}

	@Override
	public void endPrefixMapping(String prefix) {

	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) {

	}

	@Override
	public void endElement(String uri, String localName, String qName) {

	}

	@Override
	public void characters(char[] ch, int start, int length) {

	}

	@Override
	public void ignorableWhitespace(char[] ch, int start, int length) {

	}

	@Override
	public void processingInstruction(String target, String data) {

	}

	@Override
	public void warning(LibXmlException exception) {

	}

	@Override
	public void error(LibXmlException exception) {

	}

	@Override
	public void fatalError(LibXmlException exception) {

	}

	@Override
	public void notationDecl(String name, String publicId, String systemId) {

	}

	@Override
	public void unparsedEntityDecl(String name, String publicId, String systemId, String notationName) {

	}

	@Override
	public void setDocumentLocator(Locator locator) {

	}
}
