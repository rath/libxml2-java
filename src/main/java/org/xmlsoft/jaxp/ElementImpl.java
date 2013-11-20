package org.xmlsoft.jaxp;

import org.w3c.dom.*;
import org.w3c.dom.Document;
import org.xmlsoft.*;

/**
 * 
 * User: rath
 * Date: 06/11/2013
 * Time: 06:12
 * 
 */
public class ElementImpl extends NodeImpl implements Element {
	ElementImpl(Document owner, org.xmlsoft.Node impl) {
		super(owner, impl);
	}

	@Override
	public String getTagName() {
		return getNodeName();
	}

	@Override
	public String getAttribute(String name) {
		return impl.getAttribute(name);
	}

	@Override
	public void setAttribute(String name, String value) throws DOMException {
		impl.setProp(name, value);
	}

	@Override
	public void removeAttribute(String name) throws DOMException {
		impl.removeProp(name);
	}

	@Override
	public Attr getAttributeNode(String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Attr setAttributeNode(Attr attr) throws DOMException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Attr removeAttributeNode(Attr attr) throws DOMException {
		throw new UnsupportedOperationException();
	}

	@Override
	public NodeList getElementsByTagName(String name) {
		XPathContext ctx = ((DocumentImpl)owner).getImpl().createXPathContext();
		XPathObject result = ctx.evaluate("//" + name);
		NodeList ret;
		try {
			ret = new NodeListImpl(owner, result.nodeset);
		} finally {
			result.dispose();
			ctx.dispose();
		}
		return ret;
	}

	@Override
	public String getAttributeNS(String namespaceURI, String localName) throws DOMException {
		return impl.getAttributeNS(namespaceURI, localName);
	}

	@Override
	public void setAttributeNS(String s, String s2, String s3) throws DOMException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeAttributeNS(String s, String s2) throws DOMException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Attr getAttributeNodeNS(String s, String s2) throws DOMException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Attr setAttributeNodeNS(Attr attr) throws DOMException {
		throw new UnsupportedOperationException();
	}

	@Override
	public NodeList getElementsByTagNameNS(String namespaceURI, String localName) throws DOMException {
		// TODO: When using xpath, we should know the prefix over the href. how?

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasAttribute(String name) {
		return impl.getAttribute(name)!=null;
	}

	@Override
	public boolean hasAttributeNS(String namespaceURI, String localName) throws DOMException {
		return impl.getAttributeNS(namespaceURI, localName)!=null;
	}

	@Override
	public TypeInfo getSchemaTypeInfo() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setIdAttribute(String s, boolean b) throws DOMException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setIdAttributeNS(String s, String s2, boolean b) throws DOMException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setIdAttributeNode(Attr attr, boolean b) throws DOMException {
		throw new UnsupportedOperationException();
	}

	public String toString() {
		return impl.toString();
	}
}
