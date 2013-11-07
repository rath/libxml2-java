package rath.libxml;

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

	@Override
	public Document getDocument() {
		return this;
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
}
