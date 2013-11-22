package org.xmlsoft.jaxp;

import org.w3c.dom.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xmlsoft.*;
import static org.xmlsoft.Node.*;

/**
 * 
 * User: rath
 * Date: 06/11/2013
 * Time: 05:37
 * 
 */
public class NodeImpl implements Node {
	protected final org.xmlsoft.Node impl;
	protected Document owner;

	protected NodeImpl(Document owner, org.xmlsoft.Node impl) {
		this.owner = owner;
		this.impl = impl;
	}

	public static NodeImpl createByType(Document owner, org.xmlsoft.Node impl) {
		if( impl==null )
			return null;
		short type = impl.getType();
		if( type== TYPE_ELEMENT ) {
			return new ElementImpl(owner, impl);
		} else
		if( type== TYPE_TEXT ) {
			return new TextImpl(owner, impl);
		}
		return new NodeImpl(owner, impl);
	}

	@Override
	public String getNodeName() {
		Namespace ns = impl.getNamespace();
		if(ns==null || ns.getPrefix()==null)
			return impl.getName();
		return ns.getPrefix().concat(":").concat(impl.getName());
	}

	@Override
	public String getNodeValue() throws DOMException {
		return null;
	}

	@Override
	public void setNodeValue(String value) throws DOMException {

	}

	@Override
	public short getNodeType() {
		switch (impl.getType()) {
			case TYPE_DOCUMENT:
				return Node.DOCUMENT_TYPE_NODE;
			case TYPE_ELEMENT:
				return Node.ELEMENT_NODE;
			case TYPE_ATTRIBUTE:
				return Node.ATTRIBUTE_NODE;
			case TYPE_TEXT:
				return Node.TEXT_NODE;
			case TYPE_CDATA:
				return Node.CDATA_SECTION_NODE;
			case TYPE_COMMENT:
				return Node.COMMENT_NODE;
		}
		throw new UnsupportedOperationException();
	}

	@Override
	public Node getParentNode() {
		org.xmlsoft.Node parent = impl.getParent();
		if( parent==null )
			return null;
		return createByType(owner, parent);
	}

	@Override
	public NodeList getChildNodes() {
		return new NodeListImpl(owner, impl.children());
	}

	@Override
	public Node getFirstChild() {
		return createByType(owner, impl.children());
	}

	@Override
	public Node getLastChild() {
		return createByType(owner, impl.getLast());
	}

	@Override
	public Node getPreviousSibling() {
		return createByType(owner, impl.getPrevious());
	}

	@Override
	public Node getNextSibling() {
		return createByType(owner, impl.getNext());
	}

	@Override
	public NamedNodeMap getAttributes() {
		if(impl.getType()!= TYPE_ELEMENT) {
			throw new UnsupportedOperationException("Node.getAttributes has been called for type " + impl.getType());
		}

		AttributeNamedNodeMapImpl namedMap = new AttributeNamedNodeMapImpl((ElementImpl)this);
		return namedMap;
	}

	@Override
	public Document getOwnerDocument() {
		return this.owner;
	}

	@Override
	public Node insertBefore(Node newChild, Node refChild) throws DOMException {
		NodeImpl refNode = (NodeImpl) refChild;
		NodeImpl newNode = (NodeImpl) newChild;
		org.xmlsoft.Node addedNode = refNode.impl.addPrevSibling(newNode.impl);
		return NodeImpl.createByType(owner, addedNode);
	}

	@Override
	public Node replaceChild(Node node, Node node2) throws DOMException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Node removeChild(Node node) throws DOMException {
		NodeImpl implToRemove = (NodeImpl) node;
		impl.unlink(implToRemove.impl);
		implToRemove.impl.dispose();
		return node;
	}

	@Override
	public Node appendChild(Node node) throws DOMException {
		return NodeImpl.createByType(owner, impl.addChild(((NodeImpl) node).impl));
	}

	@Override
	public boolean hasChildNodes() {
		return impl.children()!=null;
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
	public boolean isSupported(String feature, String version) {
		throw new UnsupportedOperationException("feature="+feature+", version="+version);
	}

	@Override
	public String getNamespaceURI() {
		Namespace ns = impl.getNamespace();
		if( ns==null )
			return null;
		return ns.getHref();
	}

	@Override
	public String getPrefix() {
		Namespace ns = impl.getNamespace();
		if( ns==null )
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
		return !impl.getAttributeNames().isEmpty(); // performance?
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
		String value = null;
		switch (impl.getType()) {
			case TYPE_TEXT:
			case TYPE_CDATA:
			case TYPE_COMMENT:
			case TYPE_PI:
				value = impl.getText();
				break;
			case TYPE_ELEMENT:
			case TYPE_ATTRIBUTE:
			case TYPE_ENTITY:
			case TYPE_DOCUMENT_FRAG:
				value = impl.getChildText();
				break;
			case TYPE_DOCUMENT:
			case TYPE_DOCUMENT_TYPE:
			case TYPE_NOTATION:
				value = null;
				break;
		}
		return value;
	}

	@Override
	public void setTextContent(String data) throws DOMException {
		impl.setText(data);
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
		throw new UnsupportedOperationException();
	}

	@Override
	public String lookupNamespaceURI(String s) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isEqualNode(Node node) {
		return ((NodeImpl)node).impl.equals(this.impl);
	}

	@Override
	public Object getFeature(String feature, String version) {
		throw new UnsupportedOperationException("feature="+feature+", version="+version);
	}

	@Override
	public Object setUserData(String s, Object o, UserDataHandler userDataHandler) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getUserData(String s) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return impl.toString();
	}
}
