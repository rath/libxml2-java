package org.xmlsoft.jaxp.util;

import org.xml.sax.DTDHandler;
import org.xml.sax.SAXException;

/**
 * User: rath
 * Date: 10/11/2013
 * Time: 01:20
 */
public class NullDTDHandler implements DTDHandler {
	private static NullDTDHandler INST = new NullDTDHandler();

	public static NullDTDHandler getInstance() {
		return INST;
	}

	private NullDTDHandler() {

	}

	@Override
	public void notationDecl(String s, String s2, String s3) throws SAXException {

	}

	@Override
	public void unparsedEntityDecl(String s, String s2, String s3, String s4) throws SAXException {

	}
}
