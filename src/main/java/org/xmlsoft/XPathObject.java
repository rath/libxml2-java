package org.xmlsoft;

/**
 * XPathObject represents the result of evaluated XPath expression.
 *
 * @author Jang-Ho Hwang, rath@xrath.com
 */
public class XPathObject implements Disposable {
	private final long p;
	private boolean disposed = false;

	private boolean empty = false;
	public NodeSet nodeset;
	public boolean booleanValue;
	public double floatValue;
	public String stringValue;

	XPathObject(long p, boolean empty) {
		this.p = p;
		this.empty = empty;

		LibXml.retainAsConfig(this);
	}

	/**
	 *
	 * @return
	 */
	public boolean isEmpty() {
		return this.empty;
	}

	/**
	 * Cast the result value of this XPathObject as string regardless of its underlying type.
	 * @return string value of this XPathObject.
	 */
	public String castToString() {
		return castToStringImpl();
	}

	private native String castToStringImpl();

	/**
	 * Cast the result value of this XPathObject as double regardless of its underlying type.
	 * @return double value of this XPathObject.
	 */
	public double castToNumber() {
		return castToNumberImpl();
	}

	/**
	 * Cast the result value of this XPathObject as integer regardless of its underlying type.
	 * @return integer value of this XPathObject.
	 */
	public int castToInt() {
		return (int)castToNumberImpl();
	}

	private native double castToNumberImpl();

	/**
	 * Cast the result value of this XPathObject as boolean regardless of its underlying type.
	 * @return boolean value of this XPathObject.
	 */
	public boolean castToBoolean() {
		return castToBooleanImpl();
	}

	private native boolean castToBooleanImpl();

	/**
	 * Convenience method for accessing first node of result.
	 * Please be aware of calling .getNext() on returned Node, it's just next sibling of the first result.
	 * @return first node of nodeset.
	 */
	public Node getFirstNode() {
		if( nodeset==null )
			return null;
		if( nodeset.getSize()==0 )
			return null;
		return nodeset.getFirstNode();
	}

	/**
	 * Register this XPathObject to the auto dispose manager.
	 * <p>You should call LibXml.disposeAutoRetainedItems() on the same thread after your logic has done.</p>
	 * @return this instance, for method chaining.
	 */
	public XPathObject autoDispose() {
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
	public String toString() {
		return "[XPathObject] nodeset=" + nodeset.toString() + ", boolean=" + booleanValue +
			", float=" + floatValue + ", string=" + stringValue;
	}
}
