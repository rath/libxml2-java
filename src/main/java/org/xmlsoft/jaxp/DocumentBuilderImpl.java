package org.xmlsoft.jaxp;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.xml.sax.*;
import org.xmlsoft.LibXml;
import org.xmlsoft.LibXmlException;
import org.xmlsoft.util.Utils;

import javax.xml.parsers.DocumentBuilder;
import java.io.*;

/**
 * 
 * User: rath
 * Date: 06/11/2013
 * Time: 04:44
 * 
 */
public class DocumentBuilderImpl extends DocumentBuilder {
	private void handleParseError(LibXmlException e) throws SAXParseException {
		if( e.getCode()==76 ) {
			throw new SAXParseException(e.getMessage(), null, null, e.getLineNumber(), e.getColumnNumber());
		}
		throw e;
	}

	@Override
	public Document parse(File file) throws SAXException, IOException {
		org.xmlsoft.Document doc = null;
		try {
			doc = LibXml.parseFile(file);
		} catch( LibXmlException e ) {
			handleParseError(e);
		}
		return new DocumentImpl(doc);
	}

	@Override
	public Document parse(InputSource inputSource) throws SAXException, IOException {
		org.xmlsoft.Document doc = null;
		try {
			if (inputSource.getByteStream() != null) {
				doc = LibXml.parseInputStream(inputSource.getByteStream());
			} else if (inputSource.getCharacterStream() != null) {
				String s = Utils.loadReader(inputSource.getCharacterStream());
				doc = LibXml.parseString(s);
			} else if (inputSource.getSystemId() != null) {
				doc = LibXml.parseSystemId(inputSource.getSystemId());
			}
		} catch (LibXmlException e) {
			handleParseError(e);
		}
		return new DocumentImpl(doc);
	}

	@Override
	public Document newDocument() {
		return new DocumentImpl(org.xmlsoft.Document.create("1.0"));
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
	public void setEntityResolver(EntityResolver entityResolver) {
//		throw new UnsupportedOperationException();
	}

	@Override
	public void setErrorHandler(ErrorHandler errorHandler) {
//		throw new UnsupportedOperationException();
	}

	@Override
	public DOMImplementation getDOMImplementation() {
		throw new UnsupportedOperationException();
	}
}
