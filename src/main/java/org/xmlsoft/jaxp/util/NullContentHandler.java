package org.xmlsoft.jaxp.util;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
 * User: rath
 * Date: 10/11/2013
 * Time: 01:19
 */
public class NullContentHandler implements ContentHandler {
	private static NullContentHandler INST = new NullContentHandler();

	public static NullContentHandler getInstance() {
		return INST;
	}

	private NullContentHandler() {

	}

	@Override
	public void setDocumentLocator(Locator locator) {

	}

	@Override
	public void startDocument() throws SAXException {

	}

	@Override
	public void endDocument() throws SAXException {

	}

	@Override
	public void startPrefixMapping(String s, String s2) throws SAXException {

	}

	@Override
	public void endPrefixMapping(String s) throws SAXException {

	}

	@Override
	public void startElement(String s, String s2, String s3, Attributes attributes) throws SAXException {

	}

	@Override
	public void endElement(String s, String s2, String s3) throws SAXException {

	}

	@Override
	public void characters(char[] chars, int i, int i2) throws SAXException {

	}

	@Override
	public void ignorableWhitespace(char[] chars, int i, int i2) throws SAXException {

	}

	@Override
	public void processingInstruction(String s, String s2) throws SAXException {

	}

	@Override
	public void skippedEntity(String s) throws SAXException {

	}
}
