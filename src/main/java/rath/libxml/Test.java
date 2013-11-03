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
		File inputFile = new File("sample.xml");

		Document doc = null;
		doc = LibXml.parseFile(inputFile);
		Node root = doc.getRootElement();
		for (Node underStory : root) {
			if (underStory.getName().equals("storyinfo")) {
				for (Node authorNode : underStory) {
					if (authorNode.getName().equals("author")) {
						String authorName = authorNode.getChildText(false);
						System.out.printf("author.name : %s%n", authorName.trim());
						System.out.printf("author.@type: %s%n", authorNode.getProp("type"));
					}
				}
			}
		}
	}
}
