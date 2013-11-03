package rath.libxml;

/**
 * Created with IntelliJ IDEA.
 * User: rath
 * Date: 02/11/2013
 * Time: 23:50
 * To change this template use File | Settings | File Templates.
 */
public class Document extends Node {
	Document(long p) {
		super(p);
	}

	public Node getRootElement() {
		return getRootElementImpl();
	}

	private native Node getRootElementImpl();

	private native void dispose();

	protected void finalize() throws Throwable {
		dispose();
		super.finalize();
	}
}
