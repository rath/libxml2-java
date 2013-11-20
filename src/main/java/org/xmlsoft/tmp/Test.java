package org.xmlsoft.tmp;

import org.xmlsoft.*;

import java.io.File;

/**
 * 
 * User: rath
 * Date: 01/11/2013
 * Time: 12:21
 * 
 */
public class Test {
	public static void main(String[] args) throws Exception {
		testXPathNavigate();
	}

	public static void testXPathNavigate() throws Exception {
		String xml = "<?xml version=\"1.0\"?> <me-root><first><sub value=\"10\">HELLO</sub><sub value=\"20\" /></first><second value=\"20\"/><third /></me-root>";

		Document doc = LibXml.parseString(xml);
		XPathContext ctx = doc.createXPathContext();
		XPathObject result = ctx.evaluate("//sub[@value=10]");

		Node n = result.getFirstNode().getParent().getNext();
	}

	public static void testSpringBeans() throws Exception {
		File inputFile = new File("samples/sample-springbeans.xml");
		Document doc = LibXml.parseFile(inputFile);
		for(Node node : doc.getRootElement()) {
//			System.out.println(node + " ::ns=" + node.getNamespace());
			if(node.getName().equals("bean")) {
				System.out.println("Bean: " + node.getAttributeMap());
			}
		}
	}

	public static void testSample() throws Exception {
		File inputFile = new File("sample.xml");

		Document doc = null;
		doc = LibXml.parseFile(inputFile);
		Node root = doc.getRootElement();
		for (Node underStory : root) {
			if (underStory.getName().equals("storyinfo")) {
				for (Node authorNode : underStory) {
					if (authorNode.getName().equals("author")) {
						System.out.printf("author.name : %s%n", authorNode.getChildText().trim());
						System.out.printf("author.@type: %s%n", authorNode.getProp("type"));
					}
				}
			}
		}
	}
}
