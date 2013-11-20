package org.xmlsoft.jaxp;

import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * User: rath
 * Date: 10/11/2013
 * Time: 00:42
 */
public class SAXParserFactoryImpl extends SAXParserFactory {
	@Override
	public SAXParser newSAXParser() throws ParserConfigurationException, SAXException {
		SAXParserImpl impl = new SAXParserImpl();
		return impl;
	}

	@Override
	public void setFeature(String name, boolean value) throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException {
		System.out.println("setFeature: " + name + ", " + value);
		throw new UnsupportedOperationException(name + ": " + value);
	}

	@Override
	public boolean getFeature(String name) throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException {
		throw new UnsupportedOperationException(name);
	}
}
