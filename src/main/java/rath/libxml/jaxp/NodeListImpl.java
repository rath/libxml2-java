package rath.libxml.jaxp;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * User: rath
 * Date: 06/11/2013
 * Time: 05:46
 * 
 */
public class NodeListImpl implements NodeList {
	private List<Node> list = new ArrayList<Node>();
	private Document owner;

	NodeListImpl(Document owner, rath.libxml.Node n) {
		while(true) {
			list.add(new NodeImpl(owner, n));
			n = n.getNext();
			if( n==null )
				break;
		}
	}

	@Override
	public Node item(int i) {
		return list.get(i);
	}

	@Override
	public int getLength() {
		return list.size();
	}
}
