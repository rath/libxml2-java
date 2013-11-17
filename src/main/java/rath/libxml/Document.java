package rath.libxml;

import java.io.File;

/**
 * 
 * User: rath
 * Date: 02/11/2013
 * Time: 23:50
 * 
 */
public class Document extends Node {
	private boolean disposed = false;

	Document(long p) {
		super(p);
	}

	public Node.Type getType() {
		return Node.Type.DOCUMENT;
	}

	public Node getRootElement() {
		return getRootElementImpl();
	}

	private native Node getRootElementImpl();

	public void setVersion(String version) {
		if(version==null)
			throw new NullPointerException();
		setVersionImpl(version);
	}

	private native void setVersionImpl(String version);

	public String getVersion() {
		String version = getVersionImpl();
		return version;
	}

	private native String getVersionImpl();

	@Override
	public Document getDocument() {
		return this;
	}

	@Override
	public Namespace getNamespace() {
		return null;
	}

	@Override
	public Node getParent() {
		return null;
	}

	public XPathContext createXPathContext() {
		XPathContext ctx = createXPathContextImpl();
		ctx.setDocument(this);
		return ctx;
	}

	private native XPathContext createXPathContextImpl();

	@Override
	public boolean equals(Object o) {
		if( o!=null && o instanceof Document )
			if( ((Document)o).p==p )
				return true;
		return false;
	}

	@Override
	public int hashCode() {
		return (int)this.p;
	}

	/**
	 * Cleanup all resources related to this document.
	 * Be careful when you call this since this will free all children resources as well.
	 */
	public void dispose() {
		if(!disposed) {
			disposed = true;
			disposeImpl();
		}
	}

	public native void disposeImpl();

	protected void finalize() throws Throwable {
		dispose();
		super.finalize();
	}

	public Node createElement(String name) {
		Node created = createElementImpl(name);
		return created;
	}

	private native Node createElementImpl(String name);

	/**
	 * Creates a Text node given the specified string.
	 * @param data The data for the node.
	 * @return new Text node.
	 */
	public Node createText(String data) {
		Node created = createTextImpl(data);
		return created;
	}

	private native Node createTextImpl(String data);

	/**
	 * Creates a Comment node given the specified string.
	 * @param data The data for the node.
	 * @return new Comment node.
	 */
	public Node createComment(String data) {
		Node created = createCommentImpl(data);
		return created;
	}

	private native Node createCommentImpl(String data);

	/**
	 * Creates a CDataSection node whose value is the specified string.
	 * @param data The data for the CDATASection contents
	 * @return new CDATA node.
	 */
	public Node createCDataBlock(String data) {
		Node created = createCDataImpl(data);
		return created;
	}

	private native Node createCDataImpl(String data);

	/**
	 * Creates a ProcessingInstruction node given the specified name and data.
	 * @param name The target part of the processing instruction.
	 * @param content The data for the node
	 * @return
	 */
	public Node createPI(String name, String content) {
		Node created = createPIImpl(name, content);
		return created;
	}

	private native Node createPIImpl(String name, String content);

	/**
	 * Create a new document instance with specified version.
	 *
	 * @param version version of this document. it's almost 1.0.
	 * @return created document instance
	 */
	public static Document create(String version) {
		long documentP = createDocumentImpl(version);
		return new Document(documentP);
	}

	private static native long createDocumentImpl(String version);

	/**
	 * Dump this XML document, converting it to the given encoding.
	 * @param file local file to save this document
	 * @param encoding charset on dumping document
	 */
	public void save(File file, String encoding) {
		saveImpl(file.getAbsolutePath(), encoding);
	}

	private native void saveImpl(String absolutePath, String encoding);
}
