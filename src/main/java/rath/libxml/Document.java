package rath.libxml;

/**
 * Created with IntelliJ IDEA.
 * User: rath
 * Date: 02/11/2013
 * Time: 23:50
 * To change this template use File | Settings | File Templates.
 */
public class Document extends Node {
	private boolean hasDisposed = false;

	Document(long p) {
		super(p);
	}

	public Node getRootElement() {
		return getRootElementImpl();
	}

	private native Node getRootElementImpl();

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
	 */
	public void dispose() {
		if(!hasDisposed) {
			hasDisposed = true;
			disposeImpl();
		}
	}

	public native void disposeImpl();

	protected void finalize() throws Throwable {
		dispose();
		super.finalize();
	}
}
