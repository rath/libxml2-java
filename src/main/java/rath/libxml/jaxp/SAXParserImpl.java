package rath.libxml.jaxp;

import org.xml.sax.*;

import javax.xml.parsers.SAXParser;

/**
 * User: rath
 * Date: 10/11/2013
 * Time: 00:45
 */
public class SAXParserImpl extends SAXParser {
	private XMLReaderImpl xmlReader;

	SAXParserImpl() {
		xmlReader = new XMLReaderImpl();
	}

	@Override
	public Parser getParser() throws SAXException {
		throw new UnsupportedOperationException();
	}

	@Override
	public XMLReader getXMLReader() throws SAXException {
		return xmlReader;
	}

	@Override
	public boolean isNamespaceAware() {
		return true;
	}

	@Override
	public boolean isValidating() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setProperty(String name, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {
//		throw new UnsupportedOperationException("setProperty: " + name + "=" + value);
	}

	@Override
	public Object getProperty(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
		throw new UnsupportedOperationException("getProperty: " + name);
	}
}
