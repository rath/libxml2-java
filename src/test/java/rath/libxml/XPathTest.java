package rath.libxml;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.w3c.dom.NodeList;
import rath.libxml.util.Utils;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;

/**
 * User: rath
 * Date: 16/11/2013
 * Time: 09:27
 */
@RunWith(JUnit4.class)
public class XPathTest {
	@Test
	public void testCompiled() throws Exception {
		XPathExpression expr = LibXml.compileXPath("//item");

		Document doc = LibXml.parseFile(new File("sample-xmls/rss-infoq.xml"));
		XPathContext context = doc.createXPathContext();
		XPathObject result = context.evaluate(expr);

		for(Node itemNode : result.nodeset) {
//			System.out.println(itemNode);
		}

		result.dispose();
		context.dispose();
		doc.dispose();

		expr.dispose();
	}

	@Test
	public void testPlain() throws Exception {
		Document doc = LibXml.parseFile(new File("sample-xmls/rss-infoq.xml"));
		XPathContext context = doc.createXPathContext();
		XPathObject result = context.evaluate("//item");
		for(Node itemNode : result.nodeset) {

		}
	}

	@Test
	public void testJaxpNodeset() throws Exception {
		// XPathFactory impl is not allowed to use another DocumentBuilderFactory implementations
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance("rath.libxml.jaxp.DocumentBuilderFactoryImpl", null);
		XPathFactory factory = XPathFactory.newInstance(XPathFactory.DEFAULT_OBJECT_MODEL_URI, "rath.libxml.jaxp.XPathFactoryImpl", null);
//		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//		XPathFactory factory = XPathFactory.newInstance();

		XPath xpath = factory.newXPath();
		org.w3c.dom.Document doc = dbf.newDocumentBuilder().parse(new File("sample-xmls/rss-infoq.xml"));

		NodeList nl = (NodeList)xpath.evaluate("//item[1]/title", doc, XPathConstants.NODESET);
		for(int i=0; i<nl.getLength(); i++) {
			org.w3c.dom.Node node = nl.item(i);
			String title = node.getTextContent();
			Assert.assertEquals("Oracle Releases Videos and Slides from the 2013 JVM Language Summit", title);
		}
		Assert.assertNotEquals("zero length result?", 0, nl.getLength());
	}

	@Test
	public void testJaxpString() throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance("rath.libxml.jaxp.DocumentBuilderFactoryImpl", null);
		XPathFactory factory = XPathFactory.newInstance(XPathFactory.DEFAULT_OBJECT_MODEL_URI, "rath.libxml.jaxp.XPathFactoryImpl", null);
//		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//		XPathFactory factory = XPathFactory.newInstance();

		XPath xpath = factory.newXPath();
		org.w3c.dom.Document doc = dbf.newDocumentBuilder().parse(new File("sample-xmls/rss-infoq.xml"));

		String title = (String) xpath.evaluate("//item[1]/title", doc, XPathConstants.STRING);
		Assert.assertEquals("Oracle Releases Videos and Slides from the 2013 JVM Language Summit", title);
	}

	@Test
	public void testNumberResult() throws Exception {
		String xml = "<?xml version=\"1.0\"?>" +
			"<items>" +
			"  <item id=\"one\"><value tag=\"1\" /></item>" +
			"  <item id=\"two\"><value tag=\"2\" /></item>" +
			"  <item id=\"three\"><value tag=\"3\" /></item>" +
			"</items>";

		Document doc = LibXml.parseString(xml);
		XPathContext ctx = doc.createXPathContext();
		XPathObject result = ctx.evaluate("//item[@id='two']/value/@tag");
		Assert.assertEquals("tag", 2.0D, result.castToNumber(), 0.0D);
		Assert.assertEquals("tag", "2", result.castToString());
	}

	@Test
	public void testNotFound() throws Exception {
		String xml = "<?xml version=\"1.0\"?>" +
			"<items>" +
			"  <item id=\"one\"><value tag=\"1\" /></item>" +
			"  <item id=\"two\"><value tag=\"2\" /></item>" +
			"  <item id=\"three\"><value tag=\"3\" /></item>" +
			"</items>";

		Document doc = LibXml.parseString(xml);
		XPathContext ctx = doc.createXPathContext();
		XPathObject result = ctx.evaluate("//item[@id='two']/value/@xxx");
		Assert.assertEquals(true, result.isEmpty());
	}

	@Test
	public void testJaxpCompiled() throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance("rath.libxml.jaxp.DocumentBuilderFactoryImpl", null);
		XPathFactory factory = XPathFactory.newInstance(XPathFactory.DEFAULT_OBJECT_MODEL_URI, "rath.libxml.jaxp.XPathFactoryImpl", null);

		org.w3c.dom.Document doc = dbf.newDocumentBuilder().parse(new File("sample-xmls/rss-infoq.xml"));

		XPath xpath = factory.newXPath();
		javax.xml.xpath.XPathExpression expr = xpath.compile("//item[1]/title");
		String title = (String) expr.evaluate(doc, XPathConstants.STRING);
		Assert.assertEquals("Oracle Releases Videos and Slides from the 2013 JVM Language Summit", title);
	}
}
