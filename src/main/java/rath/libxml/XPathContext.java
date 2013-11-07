package rath.libxml;

/**
 * 
 * User: rath
 * Date: 04/11/2013
 * Time: 17:15
 * 
 */
public class XPathContext implements Disposable {
	private boolean disposed = false;
	private final long p;
	private Document document;

	XPathContext(long p) {
		this.p = p;
	}

	void setDocument(Document doc) {
		this.document = doc;
	}

	public XPathExpression compileExpression(String expr) {
		return null; // TODO: Impl compileExpression
	}

	public XPathObject evaluate(String expr) {
		return evaluateImpl(expr);
	}

	private native XPathObject evaluateImpl(String expr);

	public void dispose() {
		if(!disposed) {
			disposed = true;
			disposeImpl();
		}
	}

	private native void disposeImpl();

	protected void finalize() throws Throwable {
		dispose();
		super.finalize();
	}
}
