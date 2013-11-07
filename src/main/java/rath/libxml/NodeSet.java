package rath.libxml;

import java.util.*;

/**
 * 
 * User: rath
 * Date: 04/11/2013
 * Time: 17:42
 * 
 */
public class NodeSet implements Iterable<Node> {
	private final long p;
	private int size;
	private List<Node> nodes = new ArrayList<Node>();

	NodeSet(long p) {
		this.p = p;
	}

	public int getSize() {
		return this.size;
	}

	public Node getFirstNode() {
		return nodes.get(0);
	}

	/**
	 * Called by JNI
	 * @param node
	 */
	void addNode(Node node) {
		nodes.add(node);
	}

	public List<Node> getNodes() {
		return this.nodes;
	}

	public Node getNodeAt(int index) {
		return nodes.get(index);
	}

	@Override
	public Iterator<Node> iterator() {
		return nodes.iterator();
	}

	public String toString() {
		return "[NodeSet] size=" + this.size + ", items=" + nodes.toString();
	}
}
