package rath.libxml.jaxp;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.xml.sax.*;
import rath.libxml.LibXml;
import rath.libxml.LibXmlException;

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
		rath.libxml.Document doc = null;
		try {
			doc = LibXml.parseFile(file);
		} catch( LibXmlException e ) {
			handleParseError(e);
		}
		return new DocumentImpl(doc);
	}

	@Override
	public Document parse(InputSource inputSource) throws SAXException, IOException {
		// TODO: DocumentBuilderImpl.parse. too bad implementation
		InputStream in = inputSource.getByteStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		while(true) {
			int readlen = in.read(buf);
			if( readlen==-1 )
				break;
			baos.write(buf, 0, readlen);
		}
		// TODO: should support stream parsing
		String str = baos.toString(inputSource.getEncoding()==null ? "UTF-8" : inputSource.getEncoding());
		rath.libxml.Document doc = null;
		try {
			doc = LibXml.parseString(str);
		} catch( LibXmlException e ) {
			handleParseError(e);
		}
		return new DocumentImpl(doc);
	}

	@Override
	public Document newDocument() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isNamespaceAware() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isValidating() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setEntityResolver(EntityResolver entityResolver) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setErrorHandler(ErrorHandler errorHandler) {
		throw new UnsupportedOperationException();
	}

	@Override
	public DOMImplementation getDOMImplementation() {
		throw new UnsupportedOperationException();
	}
}
