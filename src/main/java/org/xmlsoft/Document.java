package org.xmlsoft;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.Charset;

/**
 * <p>The Document class represents the entire XML document.
 * Conceptually, it is the root of the document tree, and provides the primary access
 * to the document's data.</p></p>
 *
 * <p>Since elements, text nodes, comments, processing instructions, etc. cannot exist
 * outside the context of a Document, the Document class also contains the factory methods
 * needed to create these objects. The Node objects created have a owner document attribute
 * by getDocument() which associates them with the Document within whose context they were created.</p>
 *
 * @author Jang-Ho Hwang, rath@xrath.com
 */
public class Document extends Node implements Disposable {
	private boolean disposed = false;

	Document(long p) {
		super(p, TYPE_DOCUMENT, null);
		LibXml.retainAsConfig(this);
	}

	/**
	 * An enum representing the type of the underlying object.
	 * @return Node.Type.DOCUMENT always.
	 */
	public short getType() {
		return TYPE_DOCUMENT;
	}

	/**
	 * Get the root element of the document.
	 * @return the root node or null.
	 */
	public Node getRootElement() {
		return getRootElementImpl();
	}

	private native Node getRootElementImpl();

	/**
	 * An attribute specifying, as part of the XML declaration, the version number of this document.
	 * @param version version of the XML document, usually '1.0'.
	 */
	public void setVersion(String version) {
		if(version==null)
			throw new NullPointerException();
		setVersionImpl(version);
	}

	private native void setVersionImpl(String version);

	/**
	 * Return an attribute specifying, as part of the XML declaration, the version number of this document.
	 * @return version of the XML document, usually '1.0'.
	 */
	public String getVersion() {
		String version = getVersionImpl();
		return version;
	}

	private native String getVersionImpl();

	/**
	 * Return document of this document, meaning <strong>this</strong>.
	 * @return this document.
	 */
	@Override
	public Document getDocument() {
		return this;
	}

	/**
	 * Return namespace of this document, it always null.
	 * @return namespace of this document, null.
	 */
	@Override
	public Namespace getNamespace() {
		return null;
	}

	/**
	 * Returns parent node of this document, a.k.a. null.
	 * @return always null.
	 */
	@Override
	public Node getParent() {
		return null;
	}

	/**
	 * Creates a new XPathContext object.
	 * @return xpath context just allocated.
	 */
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
	@Override
	public void dispose() {
		if(!disposed) {
			disposed = true;
			disposeImpl();
		}
	}

	/**
	 * Register this document object to the auto dispose manager.
	 * <p>You should call LibXml.disposeAutoRetainedItems() on the same thread after your logic has done.</p>
	 * @return this instance, for method chaining.
	 */
	public Document autoDispose() {
		LibXml.retain(this);
		return this;
	}

	public native void disposeImpl();

	/**
	 * Called by the garbage collector, it tries to dispose all system resources
	 * if it didn't dispose up until that moment.
	 * @throws Throwable any errors if occur.
	 */
	@Override
	protected void finalize() throws Throwable {
		dispose();
		super.finalize();
	}

	/**
	 * Creates an element of the type specified.
	 * @param name The name of the element type to instantiate.
	 * @return A new element object with the name.
	 */
	public Node createElement(String name) {
		Node created = createElementImpl(null, name);
		return created;
	}

	/**
	 * Creates an element of the given qualified name and namespace URI.
	 * @param ns namespace to create, null is allowed.
	 * @param name The name of the element type to instantiate.
	 * @return A new element object with the name and namespace.
	 */
	public Node createElement(Namespace ns, String name) {
		Node created = createElementImpl(ns, name);
		return created;
	}

	private native Node createElementImpl(Namespace ns, String name);

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

	/**
	 * Dump this XML document to the given OutputStream, converting it to the given encoding.
	 *
	 * @param out outputStream to save this document.
	 * @param encoding charset on dumping document.
	 * @throws IOException If any IO error occur.
	 */
	public void save(OutputStream out, String encoding) throws IOException {
		saveStreamImpl(out, encoding);
	}

	private native void saveStreamImpl(OutputStream out, String encoding) throws IOException;

	/**
	 * Dump this XML document to the given Writer, converting it to the given encoding.
	 *
	 * @param out Writer interface to save this document.
	 * @param encoding charset on dumping document.
	 * @throws IOException If any IO error occur.
	 */
	public void save(Writer out, String encoding) throws IOException {
		Charset charset = Charset.forName("UTF-8");
		saveWriterImpl(out, encoding, charset);
	}

	private native void saveWriterImpl(Writer out, String encoding, Charset charset) throws IOException;
}
