package rath.libxml;

import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: rath
 * Date: 02/11/2013
 * Time: 23:50
 * To change this template use File | Settings | File Templates.
 */
public class Node implements Iterable<Node> {
	private Document document;
	final long p;
	private Type type;

	private String name; // set lazy on native
	private Namespace namespace; // set lazy on native

	Node(long p) {
		this.p = p;
		this.fillRequiredFields();
	}

	public Node children() {
		return childrenImpl();
	}

	private native Node childrenImpl();

	public Iterator<Node> iterator() {
		final Node childNode = childrenImpl();
		return new Iterator<Node>() {
			private volatile Node current = childNode;
			@Override
			public boolean hasNext() {
				return current.hasNext();
			}
			@Override
			public Node next() {
				Node next = current.getNext();
				current = next;
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

	public String getChildText() {
		return getChildText(true);
	}

	public String getChildText(boolean formatted) {
		return getChildTextImpl(formatted);
	}

	private native String getChildTextImpl(boolean formatted);

	public String getProp(String attributeKey) {
		return getPropImpl(attributeKey);
	}

	private native String getPropImpl(String key);

	public static enum Type {
		ELEMENT(1), ATTRIBUTE(2), TEXT(3), CDATA(4), // ENTITY_REF, ENTITY, PI?
		COMMENT(8), DOCUMENT(9); // DOCUMENT_TYPE, DOCUMENT_FRAG, NOTATION, DTD?

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
