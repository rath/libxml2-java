package rath.libxml;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: rath
 * Date: 01/11/2013
 * Time: 12:21
 * To change this template use File | Settings | File Templates.
 */
public class Test {
	public static void main(String[] args) throws Exception {
		testSpringBeans();
	}

	public static void testSpringBeans() throws Exception {
		File inputFile = new File("samples/sample-springbeans.xml");
		Document doc = LibXml.parseFile(inputFile);
		for(Node node : doc.getRootElement()) {
//			System.out.println(node + " ::ns=" + node.getNamespace());
			if(node.getName().equals("bean")) {
				System.out.println("Bean: id=" + node.getProp("id") + ", class=" + node.getProp("class"));
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
