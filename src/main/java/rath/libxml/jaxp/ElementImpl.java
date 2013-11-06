package rath.libxml.jaxp;

import org.w3c.dom.*;
import org.w3c.dom.Document;
import rath.libxml.*;

/**
 * Created with IntelliJ IDEA.
 * User: rath
 * Date: 06/11/2013
 * Time: 06:12
 * To change this template use File | Settings | File Templates.
 */
public class ElementImpl extends NodeImpl implements Element {
	ElementImpl(Document owner, rath.libxml.Node impl) {
		super(owner, impl);
	}

	@Override
	public String getTagName() {
		return impl.getName();
	}

	@Override
	public String getAttribute(String name) {
		return impl.getAttribute(name);
	}

	@Override
	public void setAttribute(String name, String value) throws DOMException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeAttribute(String name) throws DOMException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Attr getAttributeNode(String s) {
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
		throw new UnsupportedOperationException(); // TODO: by using xpath?
	}

	@Override
	public String getAttributeNS(String s, String s2) throws DOMException {
		throw new UnsupportedOperationException();
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
	public NodeList getElementsByTagNameNS(String s, String s2) throws DOMException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasAttribute(String name) {
		return impl.getAttribute(name)!=null;
	}

	@Override
	public boolean hasAttributeNS(String s, String s2) throws DOMException {
		throw new UnsupportedOperationException();
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
}
