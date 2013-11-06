package rath.libxml;

/**
 * Created with IntelliJ IDEA.
 * User: rath
 * Date: 04/11/2013
 * Time: 17:15
 * To change this template use File | Settings | File Templates.
 */
public class XPathContext {
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
}
