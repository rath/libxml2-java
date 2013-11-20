package org.xmlsoft.jaxp;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Text;
import org.xmlsoft.Node;

/**
 * User: rath
 * Date: 08/11/2013
 * Time: 06:01
 */
public class TextImpl extends CharacterDataImpl implements Text {

	protected TextImpl(Document owner, Node impl) {
		super(owner, impl);
	}

	@Override
	public String getNodeValue() throws DOMException {
		return impl.getText();
	}

	@Override
	public Text splitText(int offset) throws DOMException {
		String str = getTextContent();
		String curText = str.substring(0, offset);
		String newText = str.substring(offset+1);
		setTextContent(curText);
		TextImpl newNode = (TextImpl) owner.createTextNode(newText);
		org.w3c.dom.Node parentNode = getParentNode();
		if( parentNode!=null ) {
			// TODO: Should test (Text.splitText)
			parentNode.insertBefore(newNode, getNextSibling());
		}
		return newNode;
	}

	@Override
	public boolean isElementContentWhitespace() {
		String str = getTextContent();
		for(int i=str.length()-1; i>=0; i--) {
			if( str.charAt(i)!=' ' )
				return false;
		}
		return true;
	}

	@Override
	public String getWholeText() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Text replaceWholeText(String s) throws DOMException {
		throw new UnsupportedOperationException();
	}
}
