package rath.libxml.impl;

import rath.libxml.SAXHandler;

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

	public SAXHandlerInternal(SAXHandler handler) {
		this.handler = handler;
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
		int slen = copyBufferImpl();
		handler.characters(characterBuffer, 0, slen);
	}

	public void fireIgnorableWhitespace() {
		int slen = copyBufferImpl();
		handler.ignorableWhitespace(characterBuffer, 0, slen);
	}

	private final int copyBufferImpl() {
		String s = new String(byteBuffer, 0, byteBufferFilled);
		int slen = s.length();
		if( characterBuffer.length < slen ) {
			characterBuffer = new char[slen];
		}
		s.getChars(0, slen, characterBuffer, 0);
		return slen;
	}
}
