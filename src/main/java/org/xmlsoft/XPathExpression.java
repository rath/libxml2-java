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
