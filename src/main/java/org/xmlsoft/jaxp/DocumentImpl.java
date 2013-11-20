package org.xmlsoft.jaxp;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.DocumentType;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Text;
import org.w3c.dom.Comment;
import org.w3c.dom.CDATASection;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Attr;
import org.w3c.dom.EntityReference;
import org.w3c.dom.UserDataHandler;

import org.xmlsoft.Document;
import org.xmlsoft.Namespace;
import org.xmlsoft.XPathContext;
import org.xmlsoft.XPathObject;

/**
 * 
 * User: rath
 * Date: 06/11/2013
 * Time: 05:20
 * 
 */
public class DocumentImpl implements org.w3c.dom.Document {
	private final Document impl;

	DocumentImpl(Document doc) {
		this.impl = doc;
	}

	public Document getImpl() {
		return impl;
	}

	@Override
	public DocumentType getDoctype() {
		throw new UnsupportedOperationException();
	}

	@Override
	public DOMImplementation getImplementation() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Element getDocumentElement() {
		org.xmlsoft.Node node = impl.getRootElement();
//		return NodeImpl.createByType(this, node);
		return new ElementImpl(this, node);
	}

	@Override
	public Element createElement(String name) throws DOMException {
		ElementImpl node = new ElementImpl(this, impl.createElement(name));
		return node;
	}

	@Override
	public DocumentFragment createDocumentFragment() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Text createTextNode(String data) {
		TextImpl node = new TextImpl(this, impl.createText(data));
		return node;
	}

	@Override
	public Comment createComment(String data) {
		CommentImpl node = new CommentImpl(this, impl.createComment(data));
		return node;
	}

	@Override
	public CDATASection createCDATASection(String data) throws DOMException {
		CDATASectionImpl node = new CDATASectionImpl(this, impl.createCDataBlock(data));
		return node;
	}

	@Override
	public ProcessingInstruction createProcessingInstruction(String target, String data) throws DOMException {
		ProcessingInstructionImpl node = new ProcessingInstructionImpl(this, impl.createPI(target, data));
		return node;
	}

	@Override
	public Attr createAttribute(String s) throws DOMException {
		throw new UnsupportedOperationException();
	}

	@Override
	public EntityReference createEntityReference(String s) throws DOMException {
		throw new UnsupportedOperationException();
	}

	@Override
	public NodeList getElementsByTagName(String name) {
		XPathContext ctx = impl.createXPathContext();
		XPathObject result = ctx.evaluate("//" + name);
		NodeList ret;
		try {
			ret = new NodeListImpl(this, result.nodeset);
		} finally {
			result.dispose();
			ctx.dispose();
		}
		return ret;
	}

	@Override
	public Node importNode(Node node, boolean b) throws DOMException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Element createElementNS(String namespaceURI, String qName) throws DOMException {
		if (namespaceURI==null)
			throw new NullPointerException();
		String localName;
		String prefix;

		int i0 = qName.indexOf(':');
		if( i0!=-1 ) {
			prefix = qName.substring(0, i0);
			localName = qName.substring(i0+1);
		} else {
			prefix = null;
			localName = qName;
		}

		Namespace ns = new Namespace();
		ns.setHref(namespaceURI);
		ns.setPrefix(prefix);
		org.xmlsoft.Node created = impl.createElement(ns, qName);

		return new ElementImpl(this, created);
	}

	@Override
	public Attr createAttributeNS(String s, String s2) throws DOMException {
		throw new UnsupportedOperationException();
	}

	@Override
	public NodeList getElementsByTagNameNS(String s, String s2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Element getElementById(String s) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getInputEncoding() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getXmlEncoding() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getXmlStandalone() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setXmlStandalone(boolean b) throws DOMException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getXmlVersion() {
		return impl.getVersion();
	}

	@Override
	public void setXmlVersion(String version) throws DOMException {
		impl.setVersion(version);
	}

	@Override
	public boolean getStrictErrorChecking() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setStrictErrorChecking(boolean b) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getDocumentURI() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setDocumentURI(String s) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Node adoptNode(Node node) throws DOMException {
		throw new UnsupportedOperationException();
	}

	@Override
	public DOMConfiguration getDomConfig() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void normalizeDocument() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Node renameNode(Node node, String s, String s2) throws DOMException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getNodeName() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getNodeValue() throws DOMException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setNodeValue(String s) throws DOMException {
		throw new UnsupportedOperationException();
	}

	@Override
	public short getNodeType() {
		return Node.DOCUMENT_NODE;
	}

	@Override
	public Node getParentNode() {
		return null;
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
	public org.w3c.dom.Document getOwnerDocument() {
		throw new UnsupportedOperationException();
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
		org.xmlsoft.Node created = impl.addChild(((NodeImpl) node).impl);
		return new NodeImpl(this, created);
	}

	@Override
	public boolean hasChildNodes() {
		throw new UnsupportedOperationException();
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
		throw new UnsupportedOperationException();
	}

	@Override
	public String getPrefix() {
		throw new UnsupportedOperationException();
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
		throw new UnsupportedOperationException();
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
		throw new UnsupportedOperationException();
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
}
