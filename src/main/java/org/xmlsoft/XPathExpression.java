package org.xmlsoft;

/**
 * <p>XPathExpression provides access to compiled XPath expressions.</p>
 *
 * <p>This class can not do anything by itself. To get an instance of XPathExpression,
 * use LibXml.compileXPath(String expr), and to use the compiled XPathExpression,
 * use XPathContext.evaluate(XPathExpression expr).</p>
 *
 * @author Jang-Ho Hwang, rath@xrath.com
 */
public class XPathExpression implements Disposable {
	private boolean disposed = false;
	private final long p;

	XPathExpression(long p) {
		this.p = p;
		LibXml.retainAsConfig(this);
	}

	/**
	 * Register this XPathExpression object to the auto dispose manager.
	 * <p>You should call LibXml.disposeAutoRetainedItems() on the same thread after your logic has done.</p>
	 * @return this instance, for method chaining.
	 */
	public XPathExpression autoDispose() {
		LibXml.retain(this);
		return this;
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
