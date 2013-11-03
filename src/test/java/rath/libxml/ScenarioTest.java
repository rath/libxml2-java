package rath.libxml;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: rath
 * Date: 03/11/2013
 * Time: 00:21
 * To change this template use File | Settings | File Templates.
 */
@RunWith(JUnit4.class)
public class ScenarioTest {
	@Test
	public void basicLoopFlow() throws IOException {
		File inputFile = new File("sample.xml");
		Assert.assertEquals("Sample input file existence", true, inputFile.exists());

		Document doc = null;
		doc = LibXml.parseFile(inputFile);
		Node root = doc.getRootElement();
		for (Node underStory : root) {
			if (underStory.getName().equals("storyinfo")) {
				for (Node authorNode : underStory) {
					if (authorNode.getName().equals("author")) {
						String authorName = authorNode.getChildText();
						System.out.printf("author.name : %s%n", authorName);
						System.out.printf("author.@type: %s%n", authorNode.getProp("type"));
					}
				}
			}
		}
	}
}
