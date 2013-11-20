package org.xmlsoft;

import java.util.*;

/**
 * The class represents a set of node, usually returned by xpath result.
 *
 * @author Jang-Ho Hwang, rath@xrath.com
 */
public class NodeSet implements Iterable<Node> {
	private final long p;
	private int size;
	private List<Node> nodes = new ArrayList<Node>();

	NodeSet(long p) {
		this.p = p;
	}

	/**
	 * Get size of this set.
	 * @return size of this set.
	 */
	public int getSize() {
		return this.size;
	}

	/**
	 * Convenience method for a set containing only one node.
	 * @return first node, null if this set is empty.
	 */
	public Node getFirstNode() {
		if(nodes.size()==0)
			return null;
		return nodes.get(0);
	}

	/**
	 * Called by JNI
	 * @param node
	 */
	void addNode(Node node) {
		nodes.add(node);
	}

	/**
	 * Get a list representation of this set.
	 * @return
	 */
	public List<Node> getNodes() {
		return this.nodes;
	}

	/**
	 * Get n-th node of this set.
	 * @param index index to getting the node.
	 * @return node located in the given index.
	 */
	public Node getNodeAt(int index) {
		return nodes.get(index);
	}

	/**
	 * Returns an iterator of this set.
	 * @return an Iterator.
	 * */
	@Override
	public Iterator<Node> iterator() {
		return nodes.iterator();
	}

	@Override
	public String toString() {
		return "[NodeSet] size=" + this.size + ", items=" + nodes.toString();
	}
}
