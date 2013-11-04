package rath.libxml;

/**
 * Created with IntelliJ IDEA.
 * User: rath
 * Date: 04/11/2013
 * Time: 17:40
 * To change this template use File | Settings | File Templates.
 */
public class XPathObject {
	public NodeSet nodeset;
	public boolean booleanValue;
	public double floatValue;
	public String stringValue;

	private final long p;
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
}
