package rath.libxml.jaxp;

import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: rath
 * Date: 10/11/2013
 * Time: 08:38
 */
public class AttributeNamedNodeMapImpl implements NamedNodeMap {
	private final rath.libxml.Node element;
	private Map<String, Node> nodeMap = new HashMap<String,Node>();
	private List<Node> nodes = new ArrayList<Node>();

	public AttributeNamedNodeMapImpl(rath.libxml.Node node) {
		this.element = node;

		for(String name : node.getAttributeNames()) {
			String value = node.getAttribute(name);

			AttrImpl attr = new AttrImpl(name, value);
			nodes.add(attr);
			nodeMap.put(name, attr);
		}
	}

	@Override
	public Node getNamedItem(String name) {
		return nodeMap.get(name);
	}

	@Override
	public Node setNamedItem(Node node) throws DOMException {
		throw new UnsupportedOperationException(node.getClass().getName());
	}

	@Override
	public Node removeNamedItem(String name) throws DOMException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Node item(int i) {
		return nodes.get(i);
	}

	@Override
	public int getLength() {
		return nodes.size();
	}

	@Override
	public Node getNamedItemNS(String namespaceURI, String localName) throws DOMException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Node setNamedItemNS(Node node) throws DOMException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Node removeNamedItemNS(String namespaceURI, String localName) throws DOMException {
		throw new UnsupportedOperationException();
	}
}
