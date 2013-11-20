package org.xmlsoft.jaxp;

import org.w3c.dom.CharacterData;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.xmlsoft.Node;

/**
 * User: rath
 * Date: 08/11/2013
 * Time: 06:08
 */
public abstract class CharacterDataImpl extends NodeImpl implements CharacterData {
	protected CharacterDataImpl(Document owner, Node impl) {
		super(owner, impl);
	}

	@Override
	public String getData() throws DOMException {
		return getTextContent();
	}

	@Override
	public void setData(String data) throws DOMException {
		setTextContent(data);
	}

	@Override
	public int getLength() {
		return getTextContent().length();
	}

	@Override
	public String substringData(int offset, int count) throws DOMException {
		String str = getTextContent();
		// TODO: range check
		return str.substring(offset, offset+count);
	}

	@Override
	public void appendData(String arg) throws DOMException {
		setTextContent(getTextContent().concat(arg));
	}

	@Override
	public void insertData(int offset, String arg) throws DOMException {
		String s = getTextContent();
		// TODO: range check
		setTextContent(s.substring(0, offset).concat(arg).concat(s.substring(offset + 1)));
	}

	@Override
	public void deleteData(int offset, int count) throws DOMException {
		String s = getTextContent();
		// TODO: range check
		setTextContent(s.substring(0, offset).concat(s.substring(offset + 1 + count)));
	}

	@Override
	public void replaceData(int offset, int count, String arg) throws DOMException {
		String s = getTextContent();
		setTextContent(s.substring(0, offset).concat(arg).concat(s.substring(offset+1+count)));
	}
}
