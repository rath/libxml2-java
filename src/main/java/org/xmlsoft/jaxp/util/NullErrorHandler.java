package org.xmlsoft.jaxp.util;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * User: rath
 * Date: 10/11/2013
 * Time: 01:21
 */
public class NullErrorHandler implements ErrorHandler {
	private static NullErrorHandler INST = new NullErrorHandler();

	public static NullErrorHandler getInstance() {
		return INST;
	}

	private NullErrorHandler() {

	}

	@Override
	public void warning(SAXParseException e) throws SAXException {

	}

	@Override
	public void error(SAXParseException e) throws SAXException {

	}

	@Override
	public void fatalError(SAXParseException e) throws SAXException {

	}
}
