package rath.libxml.jaxp;

import org.xml.sax.*;
import rath.libxml.LibXml;
import rath.libxml.LibXmlException;
import rath.libxml.SAXHandler;
import rath.libxml.impl.SAXHandlerEngine;
import rath.libxml.jaxp.util.NullContentHandler;
import rath.libxml.jaxp.util.NullDTDHandler;
import rath.libxml.jaxp.util.NullEntityResolver;
import rath.libxml.jaxp.util.NullErrorHandler;
import rath.libxml.util.Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * User: rath
 * Date: 10/11/2013
 * Time: 00:51
 */
public class XMLReaderImpl implements XMLReader {
	private EntityResolver entityResolver = NullEntityResolver.getInstance();
	private DTDHandler dtdHandler = NullDTDHandler.getInstance();
	private ContentHandler contentHandler = NullContentHandler.getInstance();
	private ErrorHandler errorHandler = NullErrorHandler.getInstance();

	@Override
	public boolean getFeature(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setFeature(String name, boolean value) throws SAXNotRecognizedException, SAXNotSupportedException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getProperty(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setProperty(String name, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setEntityResolver(EntityResolver resolver) {
		this.entityResolver = resolver;
	}

	@Override
	public EntityResolver getEntityResolver() {
		return this.entityResolver;
	}

	@Override
	public void setDTDHandler(DTDHandler handler) {
		this.dtdHandler = handler;
	}

	@Override
	public DTDHandler getDTDHandler() {
		return this.dtdHandler;
	}

	@Override
	public void setContentHandler(ContentHandler handler) {
		this.contentHandler = handler;
	}

	@Override
	public ContentHandler getContentHandler() {
		return this.contentHandler;
	}

	@Override
	public void setErrorHandler(ErrorHandler handler) {
		this.errorHandler = handler;
	}

	@Override
	public ErrorHandler getErrorHandler() {
		return this.errorHandler;
	}

	@Override
	public void parse(InputSource input) throws IOException, SAXException {
		SAXHandlerEngine engine = new SAXHandlerEngine();
		engine.setAwarePrefixMapping(true);

		String systemId = input.getSystemId();
		if( systemId!=null ) {
			// TODO: InputSource could have byteStream even if systemId is set.
			LibXml.parseSAXSystemId(systemId, new DefaultHandler(), 0, engine);
		} else {
			// TODO: streaming
			String str = Utils.loadInputSource(input);
			LibXml.parseSAX(str, new DefaultHandler(), 0, engine);
		}
	}

	@Override
	public void parse(String systemId) throws IOException, SAXException {
		throw new UnsupportedOperationException();
	}

	class DefaultHandler implements SAXHandler {
		@Override
		public void startDocument() {
			try {
				contentHandler.startDocument();
			} catch (SAXException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void endDocument() {
			try {
				contentHandler.endDocument();
			} catch (SAXException e) {
				e.printStackTrace();
			}

		}

		@Override
		public void startPrefixMapping(String prefix, String uri) {
			try {
				contentHandler.startPrefixMapping(prefix, uri);
			} catch (SAXException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void endPrefixMapping(String prefix) {
			try {
				contentHandler.endPrefixMapping(prefix);
			} catch (SAXException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes atts) {
			try {
				contentHandler.startElement(uri, localName, qName, atts);
			} catch (SAXException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName) {
			try {
				contentHandler.endElement(uri, localName, qName);
			} catch (SAXException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void characters(char[] ch, int start, int length) {
			try {
				contentHandler.characters(ch, start, length);
			} catch (SAXException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void ignorableWhitespace(char[] ch, int start, int length) {
			try {
				contentHandler.ignorableWhitespace(ch, start, length);
			} catch (SAXException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void processingInstruction(String target, String data) {
			try {
				contentHandler.processingInstruction(target, data);
			} catch (SAXException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void warning(LibXmlException exception) {
			try {
				errorHandler.warning(new SAXParseException(exception.getMessage(), null, exception));
			} catch (SAXException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void error(LibXmlException exception) {
			try {
				errorHandler.error(new SAXParseException(exception.getMessage(), null, exception));
			} catch (SAXException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void fatalError(LibXmlException exception) {
			try {
				errorHandler.fatalError(new SAXParseException(exception.getMessage(), null, exception));
			} catch (SAXException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void notationDecl(String name, String publicId, String systemId) {
			try {
				dtdHandler.notationDecl(name, publicId, systemId);
			} catch (SAXException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void unparsedEntityDecl(String name, String publicId, String systemId, String notationName) {
			try {
				dtdHandler.unparsedEntityDecl(name, publicId, systemId, notationName);
			} catch (SAXException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void setDocumentLocator(Locator locator) {
			contentHandler.setDocumentLocator(locator);
		}
	}
}
