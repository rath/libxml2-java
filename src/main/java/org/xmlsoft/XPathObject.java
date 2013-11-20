package org.xmlsoft;

/**
 * 
 * User: rath
 * Date: 04/11/2013
 * Time: 17:40
 * 
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
	}

	public boolean isEmpty() {
		return this.empty;
	}

	public String castToString() {
		return castToStringImpl();
	}

	private native String castToStringImpl();

	public double castToNumber() {
		return castToNumberImpl();
	}

	public int castToInt() {
		return (int)castToNumberImpl();
	}

	private native double castToNumberImpl();

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

	@Override
	public void dispose() {
		if(!disposed) {
			disposed = true;
			disposeImpl();
		}
	}

	private native void disposeImpl();

	public String toString() {
		return "[XPathObject] nodeset=" + nodeset.toString() + ", boolean=" + booleanValue +
			", float=" + floatValue + ", string=" + stringValue;
	}
}
