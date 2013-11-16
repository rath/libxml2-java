package rath.libxml;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.zip.GZIPOutputStream;

/**
 * User: rath
 * Date: 03/11/2013
 * Time: 00:21
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
	public void testParseSystemId() throws IOException {
		String systemId = "file:build.xml";
		Document doc = LibXml.parseSystemId(systemId);
		String rootElementTagName = doc.getRootElement().getName();
		Assert.assertEquals("project", rootElementTagName);
		doc.dispose();
	}

	@Test
	public void testParseInputStream() throws IOException {
		FileInputStream fis = new FileInputStream(new File("build.xml"));
		Document doc = LibXml.parseInputStream(fis);
		String rootElementTagName = doc.getRootElement().getName();
		Assert.assertEquals("project", rootElementTagName);
		doc.dispose();
		fis.close();
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
//		doc.dispose();
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
		doc.dispose();
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

		GZIPOutputStream gout = new GZIPOutputStream(new FileOutputStream(f));
		gout.write(xml.getBytes());
		gout.flush();
		gout.close();

		Document doc = LibXml.parseFile(f);
		Assert.assertEquals("bean", doc.getRootElement().getName());
		doc.dispose();
	}

	@Test
	public void testNodeWalk() {
		String xml = "<?xml version=\"1.0\"?> <me-root><first />안녕하세요<second /><third /></me-root>";

		Document doc = LibXml.parseString(xml);
		Node root = doc.getRootElement();
		Node textNode = root.children().getNext().getNext().getPrevious();
		Assert.assertEquals("Previous", Node.Type.TEXT, textNode.getType());
		Assert.assertEquals("Last", "third", root.getLast().getName());
		Assert.assertEquals("Parent", "me-root", root.getLast().getParent().getName());
	}

	@Test
	public void testFindDocumentAtNode() {
		String xml = "<?xml version=\"1.0\"?> <me-root><first />안녕하세요<second /><third /></me-root>";

		Document doc = LibXml.parseString(xml);
		Document doc2 = doc.getRootElement().children().getNext().getNext().getDocument();

		Assert.assertEquals(doc, doc2);
	}



	@Test
	public void xpath1() {
		String xml = "<?xml version=\"1.0\"?> <me-root><first><sub value=\"10\"/><sub value=\"20\"/></first><second value=\"20\"/><third /></me-root>";

		Document doc = LibXml.parseString(xml);
		XPathContext ctx = doc.createXPathContext();
		XPathObject result = ctx.evaluate("//sub");
		for(Node node : result.nodeset) {
//			System.out.println(node.getName() + " value=" + node.getAttribute("value"));
		}
		Assert.assertEquals(2, result.nodeset.getSize());
	}

	@Test
	public void xpath2() {
		String xml = "<?xml version=\"1.0\"?> <me-root><first><sub value=\"10\">HELLO</sub><sub value=\"20\" /></first><second value=\"20\"/><third /></me-root>";

		Document doc = LibXml.parseString(xml);
		XPathContext ctx = doc.createXPathContext();
		XPathObject result = ctx.evaluate("//sub[@value=\"10\"]/text()");

		Assert.assertEquals("HELLO", result.getFirstNode().getText());
	}

	@Test
	public void xpathNavigate() {
		String xml = "<?xml version=\"1.0\"?> <me-root><first><sub value=\"10\">HELLO</sub><sub value=\"20\" /></first><second value=\"20\"/><third /></me-root>";

		Document doc = LibXml.parseString(xml);
		XPathContext ctx = doc.createXPathContext();
		XPathObject result = ctx.evaluate("//sub[@value>15]");

		Node n = result.getFirstNode().getParent().getNext();
		Assert.assertEquals("20", n.getAttribute("value"));
		Assert.assertEquals("second", n.getName());
	}

	@Test
	public void xpathQueryWithNamespace() {
		String xml = "<?xml version=\"1.0\"?>";
		xml += "<root xmlns=\"http://root.com/\" xmlns:g=\"http://github.com/\">";
		xml += "  <g:item>item name</g:item>";
		xml += "  <user>user name</user>";
		xml += "</root>";

		Document doc = LibXml.parseString(xml);

		XPathContext ctx = doc.createXPathContext();
		ctx.addNamespace(new Namespace("http://root.com/", "default"));
		ctx.addNamespace(new Namespace("http://github.com/", "g"));
		XPathObject result = ctx.evaluate("//g:item/text()");
		Assert.assertEquals("item name", result.getFirstNode().getText());
		result = ctx.evaluate("//default:user");
		Assert.assertEquals("user name", result.getFirstNode().getChildText());
	}

	@Test
	public void testInternalError() {
		String xml = "<?xml version=\"1.0\"?>";
		xml += "<root xmlns=\"http://root.com/\" xmlns:g=\"http://github.com/\">";
		xml += "  <g:item>item name</g:item>";
		xml += "  <user>user name</user>";
		xml += "</root>";

		Document doc = LibXml.parseString(xml);

		XPathContext ctx = doc.createXPathContext();
		try {
			ctx.addNamespace(new Namespace("http://root.com/", null));
			ctx.evaluate("//user");
			Assert.assertEquals("Expected internal error, but you just passed", true, false);
		} catch( LibXmlException e ) {
			Assert.assertEquals("xmlXPathRegisterNs", e.getMessage());
		} finally {
			ctx.dispose();
		}
	}

	@Test
	public void testGetAttributeNodes() {
		String xml = "<?xml version=\"1.0\"?>" +
			"<beans:bean xmlns=\"http://www.springframework.org/schema/security\" " +
			"  xmlns:beans=\"http://www.springframework.org/schema/beans\" " +
			"  xmlns:context=\"http://www.springframework.org/schema/context\" >" +
			"  <context:component-scan base-package=\"rath.libxml\" />" +
			"  <beans:bean id=\"testId\" beans:class=\"foo.bar.TestClass\" />" +
			"  <hello-world />" +
			"</beans:bean>";

		Document doc = LibXml.parseString(xml);
		XPathContext ctx = doc.createXPathContext();
		ctx.addNamespace(new Namespace("http://www.springframework.org/schema/beans", "beans"));
		Node bean = ctx.evaluate("//beans:bean").nodeset.getNodeAt(1);
		int conditionCount = 0;
		for(Attribute attr : bean.getAttributeNodes()) {
			if( attr.getName().equals("id") ) {
				Assert.assertEquals("id", "testId", attr.getValue());
				conditionCount++;
			}
			if( attr.getName().equals("class") ) {
				Assert.assertEquals("prefix of class", "beans", attr.getNs().getPrefix());
				conditionCount++;
			}
		}
		Assert.assertEquals("Checked all?", 2, conditionCount);

		Assert.assertEquals("attribute id", "testId", bean.getAttribute("id"));
		Assert.assertEquals("attribute class", "foo.bar.TestClass", bean.getAttribute("class"));
	}
}
