package rath.libxml;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;

/**
 * User: rath
 * Date: 08/11/2013
 * Time: 21:40
 */
public interface SAXHandler {
	public void startDocument();
	public void endDocument();
	public void startPrefixMapping(String prefix, String uri);
	public void endPrefixMapping(String prefix);
	public void startElement(String uri, String localName, String qName, Attributes atts);
	public void endElement(String uri, String localName, String qName);
	public void characters(char[] ch, int start, int length);
	public void ignorableWhitespace(char[] ch, int start, int length);
	public void processingInstruction(String target, String data);
	public void skippedEntity(String name);
	public void warning(SAXParseException exception);
	public void error(SAXParseException exception);
	public void fatalError(SAXParseException exception);
}