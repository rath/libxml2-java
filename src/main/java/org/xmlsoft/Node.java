package org.xmlsoft;

import java.util.*;

/**
 * 
 * User: rath
 * Date: 02/11/2013
 * Time: 23:50
 * 
 */
public class Node implements Iterable<Node>, Disposable {
	private boolean disposed = false;
	final long p;
	private Document document;

	private Type type; // set lazy on native
	private String name; // set lazy on native
	private Namespace namespace; // set lazy on native

	Node(long p) {
		this.p = p;
		this.fillRequiredFields();
	}

	/**
	 *
	 * @return its first child
	 */
	public Node getChildren() {
		return children();
	}

	public Node children() {
		return childrenImpl();
	}

	private native Node childrenImpl();

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

	public Type getType() {
		return this.type;
	}

	private void setType(int code) {
		type = Type.asCode(code);
	}

	public String getName() {
		if( type==Type.TEXT )
			return "";

		if( this.name!=null )
			return this.name;
		fillNameImpl();
		return this.name;
	}

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

	private native void fillRequiredFields();

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

	public Document getDocument() {
		return this.document;
	}

	// TODO: xmlFreeNode()

	public Node getParent() {
		Node parent = getParentImpl();
		if( parent.getType()==Type.DOCUMENT ) {
			return document;
		}
		return parent;
	}

	private native Node getParentImpl();

	public Node getLast() {
		Node node = getLastImpl();
		return node;
	}

	private native Node getLastImpl();

	public String getText() {
		return getText(true);
	}

	public String getText(boolean formatted) {
		return getTextImpl(formatted);
	}

	private native String getTextImpl(boolean formatted);

	public String getChildText() {
		return getChildText(true);
	}

	public String getChildText(boolean formatted) {
		Node children = children();
		if (children==null) {
			return null;
		}
		return children.getText(formatted);
	}

	public String getProp(String attributeKey) {
		return getPropImpl(attributeKey);
	}

	private native String getPropImpl(String key);

	public List<Attribute> getAttributeNodes() {
		List<Attribute> buf = new ArrayList<Attribute>();
		fillAttributeNodes(buf);
		return buf;
	}

	private native void fillAttributeNodes(List<Attribute> buf);

	public String getAttribute(String name) {
		return getProp(name);
	}

	public List<String> getAttributeNames() {
		List<String> buf = new ArrayList<String>();
		fillAttributeNames(buf);
		return buf;
	}

	private native void fillAttributeNames(List<String> buffer);

	public Map<String, String> getAttributeMap() {
		Map<String, String> ret = new HashMap<String, String>();
		for(String attributeName : getAttributeNames()) {
			ret.put(attributeName, getAttribute(attributeName));
		}
		return ret;
	}

	public String getAttributeNS(String href, String name) {
		if(href==null)
			throw new NullPointerException("uri cannot be null");
		if(name==null)
			throw new NullPointerException("name cannot be null");
		return getNsPropImpl(name, href);
	}

	private native String getNsPropImpl(String name, String href);

	public void setProp(String name, String value) {
		if(name==null||value==null)
			throw new NullPointerException();
		setPropImpl(name, value);
	}

	private native void setPropImpl(String name, String value);

	public void setAttribute(String name, String value) {
		setProp(name, value);
	}

	public boolean removeProp(String name) {
		if(name==null)
			throw new NullPointerException();
		return removePropImpl(name);
	}

	private native boolean removePropImpl(String name);

	public void removeAttribute(String name) {
		removeProp(name);
	}

	public Node addChild(Node node) {
		if( node==null )
			throw new NullPointerException();
		Node attached = addChildImpl(node);
		return attached;
	}

	private native Node addChildImpl(Node node);

	public void unlink(Node node) {
		if( node==null )
			throw new NullPointerException();
		unlinkImpl(node);
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

	public void setText(String data) {
		if( data==null )
			throw new NullPointerException();
		setTextImpl(data);
	}

	private native void setTextImpl(String data);

	public Node addPrevSibling(Node newNode) {
		return addPrevSiblingImpl(newNode);
	}

	private native Node addPrevSiblingImpl(Node newNode);

	public static enum Type {
		ELEMENT(1), ATTRIBUTE(2), TEXT(3), CDATA(4), ENTITY_REF(5), ENTITY(6), PI(7),
		COMMENT(8), DOCUMENT(9), DOCUMENT_TYPE(10), DOCUMENT_FRAG(11), NOTATION(12), DTD(14);

		private int code;
		Type(int code) {
			this.code = code;
		}
		public int code() {
			return this.code;
		}
		public static Type asCode(int code) {
			for(Type t : Type.values()) {
				if(t.code==code)
					return t;
			}
			throw new IllegalArgumentException("Unsupported node type ("+ + code + ")");
		}
	};

	public String toString() {
		return "Node[" + type + "] " + getName();
	}
}
