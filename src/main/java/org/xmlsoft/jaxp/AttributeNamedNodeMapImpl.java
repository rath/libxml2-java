package org.xmlsoft.jaxp;

import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xmlsoft.Attribute;

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
	private final org.xmlsoft.Node element;
	private Map<String,AttrImpl> nodeMap = new HashMap<String,AttrImpl>();
	private Map<String,AttrImpl> nodeNsMap = new HashMap<String,AttrImpl>();
	private List<AttrImpl> nodes = new ArrayList<AttrImpl>();

	public AttributeNamedNodeMapImpl(ElementImpl node) {
		this.element = node.impl;

		for(Attribute attr : element.getAttributeNodes()) {
			AttrImpl ai = new AttrImpl(attr);
			ai.setOwnerElement(node);
			nodes.add(ai);
			if(attr.hasNs()) {
				nodeNsMap.put(buildNsName(attr), ai);
			} else {
				nodeMap.put(attr.getName(), ai);
			}
		}
	}

	private String buildNsName(Attribute a) {
		return a.getName() + ":" + a.getNs().getHref();
	}

	private String buildNsName(String uri, String localName) {
		return localName + ":" + uri;
	}

	@Override
	public Node getNamedItem(String name) {
		Node ret = nodeMap.get(name);
		System.out.println("# ANNM.getNamedItem("+name+"): " + ret);
		return ret;
	}

	@Override
	public Node getNamedItemNS(String namespaceURI, String localName) throws DOMException {
		Node ret = nodeNsMap.get(buildNsName(namespaceURI, localName));
		System.out.println("# ANNM.getNamedItemNS(" + namespaceURI + "/" + localName +"): " + ret);
		return ret;
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
	public Node setNamedItemNS(Node node) throws DOMException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Node removeNamedItemNS(String namespaceURI, String localName) throws DOMException {
		throw new UnsupportedOperationException();
	}
}
