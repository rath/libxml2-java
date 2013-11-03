package rath.libxml;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.zip.GZIPOutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: rath
 * Date: 03/11/2013
 * Time: 00:21
 * To change this template use File | Settings | File Templates.
 */
@RunWith(JUnit4.class)
public class BasicTest {

	@Test
	public void testGetRootElement() {
		String xml = "<?xml version=\"1.0\"?> <me-root><first><second /></first></me-root>";

		Document doc = LibXml.parseString(xml);
		Assert.assertNotNull("LibXml.parseString returns null", doc);
		Node rootNode = doc.getRootElement();
		Assert.assertNotNull("Document.getRootElement returns null", rootNode);
		Assert.assertEquals("me-root", rootNode.getName());
	}

	@Test
	public void testNamespace() {
		String xml = "<?xml version=\"1.0\"?> <beans:bean xmlns=\"http://www.springframework.org/schema/security\" " +
			"xmlns:beans=\"http://www.springframework.org/schema/beans\" " +
			"xmlns:context=\"http://www.springframework.org/schema/context\" >" +
			"<context:component-scan base-package=\"rath.libxml\" />" +
			"<beans:bean id=\"testId\" class=\"foo.bar.TestClass\" />" +
			"<hello-world />" +
			"</beans:bean>";

		Document doc = LibXml.parseString(xml);
		Node root = doc.getRootElement();
		Assert.assertEquals("Get Namespace URI", "http://www.springframework.org/schema/beans", root.getNamespace().getHref());
		Assert.assertEquals("Get Namespace prefix", "beans", root.getNamespace().getPrefix());
		Assert.assertEquals("Default namespace URI", "http://www.springframework.org/schema/security", root.children().getNext().getNext().getNamespace().getHref());
		Assert.assertEquals("Default namespace prefix", null, root.children().getNext().getNext().getNamespace().getPrefix());
	}

	@Test
	public void testGetAttribute() {
		String xml = "<?xml version=\"1.0\"?> <me-root id=\"hola\"><first><second /></first></me-root>";

		Document doc = LibXml.parseString(xml);
		Assert.assertEquals("Get attribute", "hola", doc.getRootElement().getAttribute("id"));
	}

	@Test
	public void testGetAttributeNames() {
		String xml = "<?xml version=\"1.0\"?> <me-root id=\"hola\" class=\"greeting\"><first><second /></first></me-root>";

		Document doc = LibXml.parseString(xml);
		Assert.assertEquals("Get attribute names",
			Arrays.asList("id", "class"),
			doc.getRootElement().getAttributeNames());
	}

	@Test
	public void testChildrenNames() {
		String xml = "<?xml version=\"1.0\"?> <me-root><first /><second /><third /></me-root>";

		Document doc = LibXml.parseString(xml);
		Node childs = doc.getRootElement().children();
		Assert.assertEquals("1st name", "first", childs.getName());
		Assert.assertEquals("2nd name", "second", childs.getNext().getName());
		Assert.assertEquals("3rd name", "third", childs.getNext().getNext().getName());
	}

	@Test
	public void testChildrenIterable() {
		String xml = "<?xml version=\"1.0\"?> <me-root><first /><second /><third /></me-root>";

		List<String> expects = Arrays.asList("first", "second", "third");
		int index = 0;

		Document doc = LibXml.parseString(xml);
		for(Node child : doc.getRootElement()) {
			Assert.assertEquals(expects.get(index), child.getName());
			index++;
		}
		Assert.assertEquals("Validate iteration count", expects.size(), index);
	}

	@Test
	public void testGetNodeType() {
		String xml = "<?xml version=\"1.0\"?> <me-root><first />Hello<second /><third /></me-root>";

		Document doc = LibXml.parseString(xml);
		Node root = doc.getRootElement();
		Assert.assertEquals("Document", Node.Type.DOCUMENT, doc.getType());
		Assert.assertEquals("Element", Node.Type.ELEMENT, root.getType());
		Assert.assertEquals("Text", Node.Type.TEXT, root.children().getNext().getType());
		doc.dispose();
	}

	@Test
	public void testGetTextNode() {
		String xml = "<?xml version=\"1.0\"?> <me-root><first />Hello<second /><third /></me-root>";

		Document doc = LibXml.parseString(xml);
		Node root = doc.getRootElement();
		Assert.assertEquals("Hello", root.getChildText());
	}

	@Test
	public void testGetWideTextNode() {
		String xml = "<?xml version=\"1.0\"?> <me-root><first />안녕하세요<second /><third /></me-root>";

		Document doc = LibXml.parseString(xml);
		Node root = doc.getRootElement();
		Assert.assertEquals("안녕하세요", root.getChildText());
	}

	@Test
	public void testReadCompressedXml() throws IOException {
		File f = new File(System.getProperty("java.io.tmpdir"), "compressed.gz");
		f.deleteOnExit();

		String xml = "<?xml version=\"1.0\"?> <beans:bean xmlns=\"http://www.springframework.org/schema/security\" " +
			"xmlns:beans=\"http://www.springframework.org/schema/beans\" " +
			"xmlns:context=\"http://www.springframework.org/schema/context\" >" +
			"<context:component-scan base-package=\"rath.libxml\" />" +
			"<beans:bean id=\"testId\" class=\"foo.bar.TestClass\" />" +
			"<hello-world />" +
			"</beans:bean>";

		GZIPOutputStream gout = new GZIPOutputStream(new FileOutputStream(f), true);
		gout.write(xml.getBytes());
		gout.close();

		Document doc = LibXml.parseFile(f);
		Assert.assertEquals("bean", doc.getRootElement().getName());
		doc.dispose();
	}

	@Test
	public void basicLoopFlow() throws IOException {
		File inputFile = new File("samples/sample.xml");
		Assert.assertEquals("Sample input file existence", true, inputFile.exists());

		Document doc = LibXml.parseFile(inputFile);
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
