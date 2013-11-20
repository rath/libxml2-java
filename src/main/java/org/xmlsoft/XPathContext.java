package org.xmlsoft;

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
	private Node contextNode;

	XPathContext(long p) {
		this.p = p;
	}

	void setDocument(Document doc) {
		this.document = doc;
	}

	/**
	 *
	 * @param namespace
	 */
	public void addNamespace(Namespace namespace) {
		addNamespaceImpl(namespace.getPrefix(), namespace.getHref());
	}

	private native void addNamespaceImpl(String prefix, String href);

	public XPathObject evaluate(String expr) {
		return evaluateImpl(expr);
	}

	private native XPathObject evaluateImpl(String expr);

	public XPathObject evaluate(XPathExpression expr) {
		return evaluateCompiledImpl(expr);
	}

	private native XPathObject evaluateCompiledImpl(XPathExpression expr);

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

	public void setContextNode(Node contextNode) {
		this.contextNode = contextNode;
		setContextNodeImpl(contextNode);
	}

	private native void setContextNodeImpl(Node contextNode);

	public Node getContextNode() {
		return contextNode;
	}
}
