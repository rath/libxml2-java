package org.xmlsoft;

import org.xml.sax.helpers.AttributesImpl;
import org.xmlsoft.LibXmlException;
import org.xmlsoft.Namespace;
import org.xmlsoft.SAXHandler;
import org.xmlsoft.impl.LocatorImpl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Bridge class between SAXHandler and native JNI module.</p>
 *
 * <p>This class is <strong>NOT</strong> thread safe.
 * Most methods are invoked by JNI functions.</p>
 *
 * @author Jang-Ho Hwang, rath@xrath.com
 */
public final class SAXHandlerEngine {
	private SAXHandler handler;
	private byte[] byteBuffer = new byte[512];
	private int byteBufferFilled = 0;
	private char[] characterBuffer = new char[512];

	private int prefixDetectDistance = 0;
	private Map<Integer, Namespace> prefixNsMap = new HashMap<Integer, Namespace>();
	private boolean awarePrefixMapping = false;

	public SAXHandlerEngine() {
	}

	public void setHandler(SAXHandler handler) {
		this.handler = handler;
	}

	public SAXHandler getHandler() {
		return handler;
	}

	public void setAwarePrefixMapping(boolean aware) {
		this.awarePrefixMapping = aware;
	}

	public boolean isAwarePrefixMapping() {
		return awarePrefixMapping;
	}

	public void fireStartDocument() {
		handler.startDocument();
	}

	public void fireEndDocument() {
		handler.endDocument();
	}

	byte[] ensureCharacterBufferSize(int size) {
		if(byteBuffer.length < size) {
			byteBuffer = new byte[size];
			characterBuffer = new char[size];
		}
		byteBufferFilled = size;
		return byteBuffer;
	}

	public void fireCharacter(byte data) {
		characterBuffer[0] = (char)data;
		handler.characters(characterBuffer, 0, 1);
	}

	public boolean fireBytes() {
		for(int i=byteBufferFilled-1; i>=0; i--) {
			characterBuffer[i] = (char) byteBuffer[i];
			if(byteBuffer[i]<0) {
				return false;
			}
		}
		handler.characters(characterBuffer, 0, byteBufferFilled);
		return true;
	}

	public void fireCharacters() throws UnsupportedEncodingException {
		int len = copyBufferImpl();
		handler.characters(characterBuffer, 0, len);
	}

	public void fireIgnorableWhitespace() throws UnsupportedEncodingException {
		int len = copyBufferImpl();
		handler.ignorableWhitespace(characterBuffer, 0, len);
	}

	private int copyBufferImpl() throws UnsupportedEncodingException {
		String s = new String(byteBuffer, 0, byteBufferFilled, "UTF-8");
		int len = s.length();
		s.getChars(0, len, characterBuffer, 0);
		return len;
	}

	public void fireStartElement(String uri, String prefix, String localName, String[] namespaces,
	                             String[] attributes, int defaultAttribute) {
		// TODO: Test with startElementNs:defaultAttribute by linking a dtd
		String qName;
		if( prefix==null )
			qName = localName;
		else
			qName = prefix + ":" + localName;
		uri = uri==null ? "" : uri;

		if(awarePrefixMapping)
			trackPrefixMappingStart(namespaces);

		AttributesImpl attrImpl = new AttributesImpl$();
		if( attributes!=null ) {
			for(int i=0; i<attributes.length; i+=4) {
				String aLocal = attributes[i+0];
				String aPrefix = attributes[i+1];
				String aUri = attributes[i+2];
				String aValue = attributes[i+3];

				String aQname;
				if( aPrefix==null )
					aQname = aLocal;
				else
					aQname = aPrefix + ":" + aLocal;
				attrImpl.addAttribute(aUri, aLocal, aQname, aPrefix, aValue);
			}
		}
		handler.startElement(uri, localName, qName, attrImpl);
	}

	private void trackPrefixMappingStart(String[] namespaces) {
		if( namespaces!=null ) {
			for(int i=0; i<namespaces.length; i+=2) {
				String aPrefix = namespaces[i+0];
				String aUri = namespaces[i+1];
				handler.startPrefixMapping(aPrefix, aUri);
				prefixNsMap.put(prefixDetectDistance, new Namespace(aUri, aPrefix));
			}
		}
		prefixDetectDistance++;
	}

	public void fireEndElement(String uri, String prefix, String localName) {
		String qName;
		if (prefix == null)
			qName = localName;
		else
			qName = prefix + ":" + localName;
		uri = uri == null ? "" : uri;
		handler.endElement(uri, localName, qName);

		if(awarePrefixMapping)
			trackPrefixMappingEnd();
	}

	private void trackPrefixMappingEnd() {
		prefixDetectDistance--;
		if( prefixNsMap.containsKey(prefixDetectDistance) ) {
			Namespace ns = prefixNsMap.get(prefixDetectDistance);
			handler.endPrefixMapping(ns.getPrefix());
		}
	}

	static class AttributesImpl$ extends AttributesImpl {
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append('{');
			for(int i=0; i<getLength(); i++) {
				sb.append('[');
				sb.append(getQName(i));
				sb.append('=');
				sb.append(getValue(i));
				sb.append(']');
				if( i+1<getLength() )
					sb.append(", ");
			}
			sb.append('}');
			return sb.toString();
		}
	}

	public void fireProcessingInstruction(String target, String data) {
		handler.processingInstruction(target, data);
	}

	public void fireNotationDecl(String name, String publicId, String systemId) {
		handler.notationDecl(name, publicId, systemId);
	}

	public void fireUnparsedEntityDecl(String name, String publicId, String systemId, String notationName) {
		handler.unparsedEntityDecl(name, publicId, systemId, notationName);
	}

	public void fireWarning(String msg) {
		// TODO: error handler with simple string looks poor
		LibXmlException e = new LibXmlException(msg);
		handler.warning(e);
	}

	public void fireError(String msg) {
		// TODO: error handler with simple string looks poor
		LibXmlException e = new LibXmlException(msg);
		handler.error(e);
	}

	public void fireFatalError(String msg) {
		// TODO: error handler with simple string looks poor
		LibXmlException e = new LibXmlException(msg);
		handler.fatalError(e);
	}

	public void fireSetLocator(LocatorImpl impl) {
		handler.setDocumentLocator(impl);
	}
}
