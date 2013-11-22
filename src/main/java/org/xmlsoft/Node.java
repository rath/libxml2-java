package org.xmlsoft;

import java.util.*;

/**
 * <p>The Node class is the primary datatype for the entire Document Object Model.
 * It represents a single node in the document tree. While all objects implementing
 * the Node class expose methods for dealing with children, not all objects implementing
 * the Node class may have children.</p>
 *
 * <p></p>
 *
 * @author Jang-Ho Hwang, rath@xrath.com
 */
public class Node implements Iterable<Node>, Disposable {
	public static final short TYPE_ELEMENT = 1;
	public static final short TYPE_ATTRIBUTE = 2;
	public static final short TYPE_TEXT = 3;
	public static final short TYPE_CDATA = 4;
	public static final short TYPE_ENTITY_REF = 5;
	public static final short TYPE_ENTITY = 6;
	public static final short TYPE_PI = 7;
	public static final short TYPE_COMMENT = 8;
	public static final short TYPE_DOCUMENT = 9;
	public static final short TYPE_DOCUMENT_TYPE = 10;
	public static final short TYPE_DOCUMENT_FRAG = 11;
	public static final short TYPE_NOTATION = 12;
	public static final short TYPE_DTD = 14;

	private boolean disposed = false;
	final long p;
	private Document document;

	private short type; // set lazy on native
	private String name; // set lazy on native
	private Namespace namespace; // set lazy on native

	Node(long p, short type, Document owner) {
		this.p = p;
		this.type = type;
		this.document = owner;
	}

	/**
	 * Children of this node, note that node returned is the first child of this node.
	 *
	 * @return first child of this node.
	 */
	public Node getChildren() {
		return children();
	}

	/**
	 * Children of this node, note that node returned is the first child of this node.
	 *
	 * @return first child of this node.
	 */
	public Node children() {
		return childrenImpl();
	}

	private native Node childrenImpl();

	/**
	 * Returns a children iterator of this node.
	 * @return an Iterator.
	 */
	public Iterator<Node> iterator() {
		final Node childNode = childrenImpl();
		return new Iterator<Node>() {
			private volatile Node current = childNode;
			private boolean skip = true;
			@Override
			public boolean hasNext() {
				return current.hasNext();
			}
			@Override
			public Node next() {
				if (skip) {
					skip = false;
					return current;
				}
				current = current.getNext();
				return current;
			}
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	/**
	 * An enum representing the type of the underlying object.
	 * @return type of this node.
	 */
	public short getType() {
		return this.type;
	}

	/**
	 * The name of this node, depending on its type.
	 * @return a name.
	 */
	public String getName() {
		if( type==TYPE_TEXT )
			return "";

		if( this.name!=null )
			return this.name;
		fillNameImpl();
		return this.name;
	}

	/**
	 * The namespace of this node if exists.
	 * @return namespace object.
	 */
	public Namespace getNamespace() {
		if( this.namespace!=null )
			return this.namespace;
		fillNamespaceImpl();
		return this.namespace;
	}

	private native void fillNamespaceImpl();

	/**
	 * Execute as 'this.name = xmlNodePtr->name'
	 */
	private native void fillNameImpl();

	/**
	 * Returns next sibling if possible.
	 * @return next sibling, otherwise it will return null.
	 */
	public Node getNext() {
		Node nextNode = nextImpl();
		return nextNode;
	}

	private native Node nextImpl();

	/**
	 * Tests whether xmlNode->next is null or not.
	 * @return true if there is sibling followed
	 */
	private native boolean hasNext();

	/**
	 * Returns previous sibling if possible.
	 * @return previous sibling, otherwise it will return null.
	 */
	public Node getPrevious() {
		Node prevNode = previousImpl();
		return prevNode;
	}

	private native Node previousImpl();

	void setDocument(Document doc) {
		this.document = doc;
	}

	/**
	 * The Document object associated with this node.
	 * This is also the Document object used to create new nodes.
	 * @return document object associated with this node.
	 */
	public Document getDocument() {
		return this.document;
	}

	/**
	 * The parent of this node. All nodes, except Document, DocumentFragment, Entity and
	 * Notation may have a parent.
	 * @return parent node of this node.
	 */
	public Node getParent() {
		Node parent = getParentImpl();
		if( parent.getType()==TYPE_DOCUMENT ) {
			return document;
		}
		return parent;
	}

	private native Node getParentImpl();

	/**
	 * The last child of this node, If there is no such node, it returns null.
	 * @return the last child of this node.
	 */
	public Node getLast() {
		Node node = getLastImpl();
		return node;
	}

	private native Node getLastImpl();

	/**
	 * This attribute returns the text content of this node.
	 * @return text content of this node.
	 */
	public String getText() {
		return getText(true);
	}

	/**
	 * This attribute returns the text content of this node.
	 * @param formatted inline or not.
	 * @return text content of this node.
	 */
	public String getText(boolean formatted) {
		return getTextImpl(formatted);
	}

	private native String getTextImpl(boolean formatted);

	/**
	 * This attribute returns the text content of its descendants.
	 * @return text content of this node.
	 */
	public String getChildText() {
		return getChildText(true);
	}

	/**
	 * This attribute returns the text content of its descendants.
	 * @param formatted inline or not.
	 * @return text content of this node.
	 */
	public String getChildText(boolean formatted) {
		Node children = children();
		if (children==null) {
			return null;
		}
		return children.getText(formatted);
	}

	/**
	 * Get an attribute by name.
	 * @param attributeKey the name of attribute
	 * @return the value of an attribute with specified name.
	 */
	public String getProp(String attributeKey) {
		return getPropImpl(attributeKey);
	}

	private native String getPropImpl(String key);

	/**
	 * Get all attributes of this node.
	 * @return a list of attributes.
	 */
	public List<Attribute> getAttributeNodes() {
		List<Attribute> buf = new ArrayList<Attribute>();
		fillAttributeNodes(buf);
		return buf;
	}

	private native void fillAttributeNodes(List<Attribute> buf);

	/**
	 * Get an attribute by name.
	 * @param name the name of attribute
	 * @return the value of an attribute with specified name.
	 */
	public String getAttribute(String name) {
		return getProp(name);
	}

	/**
	 * Get a name list of all attributes.
	 * @return list filled with attribute names.
	 */
	public List<String> getAttributeNames() {
		List<String> buf = new ArrayList<String>();
		fillAttributeNames(buf);
		return buf;
	}

	private native void fillAttributeNames(List<String> buffer);

	/**
	 * Get a name/value map of attributes this node has.
	 * @return map filled with names and values.
	 */
	public Map<String, String> getAttributeMap() {
		Map<String, String> ret = new HashMap<String, String>();
		for(String attributeName : getAttributeNames()) {
			ret.put(attributeName, getAttribute(attributeName));
		}
		return ret;
	}

	/**
	 * Get an attribute with the given namespace URI and name.
	 * @param href namespace URI.
	 * @param name name of an attribute.
	 * @return value of attribute found.
	 */
	public String getAttributeNS(String href, String name) {
		if(href==null)
			throw new NullPointerException("uri cannot be null");
		if(name==null)
			throw new NullPointerException("name cannot be null");
		return getNsPropImpl(name, href);
	}

	private native String getNsPropImpl(String name, String href);

	/**
	 * Set attribute with the given name, value pair.
	 * @param name name of newly created attribute.
	 * @param value value of newly created attribute.
	 */
	public void setProp(String name, String value) {
		if(name==null||value==null)
			throw new NullPointerException();
		setPropImpl(name, value);
	}

	private native void setPropImpl(String name, String value);

	/**
	 * Set attribute with the given name, value pair.
	 * @param name name of newly created attribute.
	 * @param value value of newly created attribute.
	 */
	public void setAttribute(String name, String value) {
		setProp(name, value);
	}

	/**
	 * Remove an attribute of this node with the given name.
	 * @param name name of attribute
	 * @return true if the attribute is removed, otherwise false.
	 */
	public boolean removeProp(String name) {
		if(name==null)
			throw new NullPointerException();
		return removePropImpl(name);
	}

	private native boolean removePropImpl(String name);

	/**
	 * Remove an attribute of this node with the given name.
	 * @param name name of attribute
	 */
	public void removeAttribute(String name) {
		removeProp(name);
	}

	/**
	 * Add the given node to the end of the list of children of this node.
	 * @param node
	 * @return
	 */
	public Node addChild(Node node) {
		if( node==null )
			throw new NullPointerException();
		Node attached = addChildImpl(node);
		return attached;
	}

	private native Node addChildImpl(Node node);

	/**
	 * Unlink the given node from children of this node.
	 * @param node node to unlink.
	 */
	public void unlink(Node node) {
		if( node==null )
			throw new NullPointerException();
		unlinkImpl(node);
		LibXml.retainAsConfig(node);
	}

	private native void unlinkImpl(Node node);

	@Override
	public void dispose() {
		if(!disposed) {
			disposed = true;
			disposeImpl();
		}
	}

	private native void disposeImpl();

	/**
	 * Replace the content of a node.
	 * @param data new text content for this node.
	 */
	public void setText(String data) {
		if( data==null )
			throw new NullPointerException();
		setTextImpl(data);
	}

	private native void setTextImpl(String data);

	/**
	 * Add a new node as the previous sibling of this node.
	 * @param newNode node to attach.
	 * @return attached node.
	 */
	public Node addPrevSibling(Node newNode) {
		return addPrevSiblingImpl(newNode);
	}

	private native Node addPrevSiblingImpl(Node newNode);

	@Override
	public String toString() {
		return "Node[" + type + "] " + getName();
	}
}
