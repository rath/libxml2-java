package rath.libxml.jaxp;

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

import rath.libxml.Document;
import rath.libxml.XPathContext;
import rath.libxml.XPathObject;

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
		rath.libxml.Node node = impl.getRootElement();
		return new ElementImpl(this, node);
	}

	@Override
	public Element createElement(String s) throws DOMException {
		throw new UnsupportedOperationException();
	}

	@Override
	public DocumentFragment createDocumentFragment() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Text createTextNode(String s) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Comment createComment(String s) {
		throw new UnsupportedOperationException();
	}

	@Override
	public CDATASection createCDATASection(String s) throws DOMException {
		throw new UnsupportedOperationException();
	}

	@Override
	public ProcessingInstruction createProcessingInstruction(String s, String s2) throws DOMException {
		throw new UnsupportedOperationException();
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
		NodeList ret = new NodeListImpl(this, result.nodeset.getFirstNode()); // FIXME: will broke when no result returns
//		ctx.dispose(); // TODO: Impl XPathContext.dispose()
		return ret;
	}

	@Override
	public Node importNode(Node node, boolean b) throws DOMException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Element createElementNS(String s, String s2) throws DOMException {
		throw new UnsupportedOperationException();
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
		throw new UnsupportedOperationException();
	}

	@Override
	public void setXmlVersion(String s) throws DOMException {
		throw new UnsupportedOperationException();
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
		throw new UnsupportedOperationException();
	}

	@Override
	public Node getParentNode() {
		throw new UnsupportedOperationException();
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
		throw new UnsupportedOperationException();
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
