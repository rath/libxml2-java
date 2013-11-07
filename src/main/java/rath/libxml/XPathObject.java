package rath.libxml;

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

	public NodeSet nodeset;
	public boolean booleanValue;
	public double floatValue;
	public String stringValue;

	XPathObject(long p) {
		this.p = p;
	}

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
}
