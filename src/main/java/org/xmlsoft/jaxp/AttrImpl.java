package org.xmlsoft.jaxp;

import org.w3c.dom.*;
import org.xmlsoft.Attribute;
import org.xmlsoft.Namespace;

/**
 * User: rath
 * Date: 08/11/2013
 * Time: 02:31
 */
public class AttrImpl implements Attr {
	private final Attribute impl;
	private Element ownerElement;

	public AttrImpl(Attribute impl) {
		this.impl = impl;
	}

	@Override
	public String getName() {
		return impl.getName();
	}

	@Override
	public boolean getSpecified() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getValue() {
		return impl.getValue();
	}

	@Override
	public void setValue(String s) throws DOMException {
		throw new UnsupportedOperationException();
	}

	public void setOwnerElement(Element element) {
		this.ownerElement = element;
	}

	@Override
	public Element getOwnerElement() {
		return ownerElement;
	}

	@Override
	public TypeInfo getSchemaTypeInfo() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isId() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getNodeName() {
		return getName();
	}

	@Override
	public String getNodeValue() throws DOMException {
		return getValue();
	}

	@Override
	public void setNodeValue(String s) throws DOMException {
		throw new UnsupportedOperationException();
	}

	@Override
	public short getNodeType() {
		return Node.ATTRIBUTE_NODE;
	}

	@Override
	public Node getParentNode() {
		return ownerElement;
	}

	@Override
	public NodeList getChildNodes() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Node getFirstChild() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Node getLastChild() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Node getPreviousSibling() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Node getNextSibling() {
		throw new UnsupportedOperationException();
	}

	@Override
	public NamedNodeMap getAttributes() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Document getOwnerDocument() {
		return ownerElement.getOwnerDocument();
	}

	@Override
	public Node insertBefore(Node node, Node node2) throws DOMException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Node replaceChild(Node node, Node node2) throws DOMException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Node removeChild(Node node) throws DOMException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Node appendChild(Node node) throws DOMException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasChildNodes() {
		return false;
	}

	@Override
	public Node cloneNode(boolean b) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void normalize() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isSupported(String s, String s2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getNamespaceURI() {
		Namespace ns = impl.getNs();
		if(ns==null)
			return null;
		return ns.getHref();
	}

	@Override
	public String getPrefix() {
		Namespace ns = impl.getNs();
		if(ns==null)
			return null;
		return ns.getPrefix();
	}

	@Override
	public void setPrefix(String s) throws DOMException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getLocalName() {
		return impl.getName();
	}

	@Override
	public boolean hasAttributes() {
		return false;
	}

	@Override
	public String getBaseURI() {
		throw new UnsupportedOperationException();
	}

	@Override
	public short compareDocumentPosition(Node node) throws DOMException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getTextContent() throws DOMException {
		return getValue();
	}

	@Override
	public void setTextContent(String s) throws DOMException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isSameNode(Node node) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String lookupPrefix(String s) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isDefaultNamespace(String s) {
		return !impl.hasNs() || impl.getNs().getPrefix()==null;
	}

	@Override
	public String lookupNamespaceURI(String s) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isEqualNode(Node node) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getFeature(String s, String s2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object setUserData(String s, Object o, UserDataHandler userDataHandler) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getUserData(String s) {
		throw new UnsupportedOperationException();
	}

	public String toString() {
		return "AttrImpl{" + impl.getName() + "=" + impl.getValue() + ", ns=" + impl.getNs() + "}";
	}

}
