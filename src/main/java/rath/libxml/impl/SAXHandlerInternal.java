package rath.libxml.impl;

import org.xml.sax.helpers.AttributesImpl;
import rath.libxml.Namespace;
import rath.libxml.SAXHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * NOT thread safe.
 * Most methods are invoked by JNI functions
 *
 * User: rath
 * Date: 08/11/2013
 * Time: 23:33
 */
public class SAXHandlerInternal {
	private final SAXHandler handler;
	private byte[] byteBuffer = new byte[128];
	private int byteBufferFilled = 0;
	private char[] characterBuffer = new char[128];

	private int prefixDetectDistance = 0;
	private Map<Integer, Namespace> prefixNsMap = new HashMap<Integer, Namespace>();
	private boolean awarePrefixMapping = false;

	public SAXHandlerInternal(SAXHandler handler) {
		this.handler = handler;
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

	public byte[] ensureCharacterBufferSize(int size) {
		if(byteBuffer.length < size ) {
			byteBuffer = new byte[size];
		}
		byteBufferFilled = size;
		return byteBuffer;
	}

	public void fireCharacters() {
		int len = copyBufferImpl();
		handler.characters(characterBuffer, 0, len);
	}

	public void fireIgnorableWhitespace() {
		int len = copyBufferImpl();
		handler.ignorableWhitespace(characterBuffer, 0, len);
	}

	private final int copyBufferImpl() {
		String s = new String(byteBuffer, 0, byteBufferFilled);
		int len = s.length();
		if( characterBuffer.length < len ) {
			characterBuffer = new char[len];
		}
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
}
