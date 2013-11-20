package org.xmlsoft;

/**
 * 
 * User: rath
 * Date: 04/11/2013
 * Time: 17:15
 * 
 */
public class XPathExpression implements Disposable {
	private boolean disposed = false;
	private final long p;

	XPathExpression(long p) {
		this.p = p;
	}

	@Override
	public void dispose() {
		if(!disposed) {
			disposed = true;
			disposeImpl();
		}
	}

	private native void disposeImpl();
}
