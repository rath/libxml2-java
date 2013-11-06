package rath.libxml.jaxp;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import rath.libxml.LibXml;

import javax.xml.parsers.DocumentBuilder;
import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: rath
 * Date: 06/11/2013
 * Time: 04:44
 * To change this template use File | Settings | File Templates.
 */
public class DocumentBuilderImpl extends DocumentBuilder {
	@Override
	public Document parse(File file) throws SAXException, IOException {
		rath.libxml.Document doc = LibXml.parseFile(file);
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
		rath.libxml.Document doc = LibXml.parseString(str);
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
