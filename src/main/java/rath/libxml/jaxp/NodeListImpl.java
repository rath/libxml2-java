package rath.libxml.jaxp;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import rath.libxml.NodeSet;

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

	NodeListImpl(Document owner, rath.libxml.Node n) {
		if (n != null) {
			while (true) {
				list.add(NodeImpl.createByType(owner, n));
				n = n.getNext();
				if (n == null)
					break;
			}
		}
	}

	public NodeListImpl(Document owner, NodeSet nodeset) {
		for(rath.libxml.Node node : nodeset.getNodes()) {
			list.add(NodeImpl.createByType(owner, node));
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
