package org.xmlsoft.jaxp.util;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * User: rath
 * Date: 10/11/2013
 * Time: 01:21
 */
public class NullEntityResolver implements EntityResolver {
	private static NullEntityResolver INST = new NullEntityResolver();

	public static NullEntityResolver getInstance() {
		return INST;
	}

	private NullEntityResolver() {

	}

	@Override
	public InputSource resolveEntity(String s, String s2) throws SAXException, IOException {
		return null;
	}
}
