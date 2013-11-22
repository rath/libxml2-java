package org.xmlsoft;

/**
 * The class represents a context of XPath which is associated with a document.
 *
 * @author Jang-Ho Hwang, rath@xrath.com
 */
public class XPathContext implements Disposable {
	private boolean disposed = false;
	private final long p;
	private Document document;
	private Node contextNode;

	XPathContext(long p) {
		this.p = p;
		LibXml.retainAsConfig(this);
	}

	void setDocument(Document doc) {
		this.document = doc;
	}

	/**
	 * Add a namespace context establish to this context.
	 * @param namespace
	 */
	public void addNamespace(Namespace namespace) {
		addNamespaceImpl(namespace.getPrefix(), namespace.getHref());
	}

	private native void addNamespaceImpl(String prefix, String href);

	/**
	 * Evaluate an XPath expression as a string.
	 * @param expr The XPath expression.
	 * @return The result XPathObject of evaluating the expression.
	 */
	public XPathObject evaluate(String expr) {
		return evaluateImpl(expr);
	}

	private native XPathObject evaluateImpl(String expr);

	/**
	 * Evaluate an XPath expression as a compiled.
	 * @param expr The XPath expression
	 * @return The result XPathObject of evaluating the expression.
	 */
	public XPathObject evaluate(XPathExpression expr) {
		return evaluateCompiledImpl(expr);
	}

	private native XPathObject evaluateCompiledImpl(XPathExpression expr);

	/**
	 * Register this XPathContext object to the auto dispose manager.
	 * <p>You should call LibXml.disposeAutoRetainedItems() on the same thread after your logic has done.</p>
	 * @return this instance, for method chaining.
	 */
	public XPathContext autoDispose() {
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

	@Override
	protected void finalize() throws Throwable {
		dispose();
		super.finalize();
	}

	/**
	 * Set the context node with the given node.
	 * @param contextNode The starting context.
	 */
	public void setContextNode(Node contextNode) {
		this.contextNode = contextNode;
		setContextNodeImpl(contextNode);
	}

	private native void setContextNodeImpl(Node contextNode);

	/**
	 * Get the context node of this XPath.
	 * @return The starting context.
	 */
	public Node getContextNode() {
		return contextNode;
	}
}
