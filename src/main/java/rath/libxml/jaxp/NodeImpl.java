package rath.libxml.jaxp;

import org.w3c.dom.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import rath.libxml.*;
import static rath.libxml.Node.Type.*;

/**
 * Created with IntelliJ IDEA.
 * User: rath
 * Date: 06/11/2013
 * Time: 05:37
 * To change this template use File | Settings | File Templates.
 */
public class NodeImpl implements Node {
	protected final rath.libxml.Node impl;
	private short nodeType;
	private Document owner;

	NodeImpl(Document owner, rath.libxml.Node impl) {
		this.owner = owner;
		this.impl = impl;
	}

	@Override
	public String getNodeName() {
		return impl.getName();
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
			case DOCUMENT:
				return Node.DOCUMENT_TYPE_NODE;
			case ELEMENT:
				return Node.ELEMENT_NODE;
			case ATTRIBUTE:
				return Node.ATTRIBUTE_NODE;
			case TEXT:
				return Node.TEXT_NODE;
			case CDATA:
				return Node.CDATA_SECTION_NODE;
			case COMMENT:
				return Node.COMMENT_NODE;
		}
		throw new UnsupportedOperationException();
	}

	@Override
	public Node getParentNode() {
		return new NodeImpl(owner, impl.getParent());
	}

	@Override
	public NodeList getChildNodes() {
		return new NodeListImpl(owner, impl.children());
	}

	@Override
	public Node getFirstChild() {
		return new NodeImpl(owner, impl.children());
	}

	@Override
	public Node getLastChild() {
		return new NodeImpl(owner, impl.getLast());
	}

	@Override
	public Node getPreviousSibling() {
		return new NodeImpl(owner, impl.getPrevious());
	}

	@Override
	public Node getNextSibling() {
		return new NodeImpl(owner, impl.getNext());
	}

	@Override
	public NamedNodeMap getAttributes() {
		throw new UnsupportedOperationException(); // TODO: NodeImpl.getAttributes()
	}

	@Override
	public Document getOwnerDocument() {
		return this.owner;
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
		throw new RuntimeException("test with libxml2 native, then impl!");
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
		throw new UnsupportedOperationException();
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
		return impl.getText();
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
}
