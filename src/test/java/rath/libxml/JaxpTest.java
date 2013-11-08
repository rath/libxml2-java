package rath.libxml;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.w3c.dom.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import rath.libxml.util.Utils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rath
 * Date: 06/11/2013
 * Time: 04:48
 * To change this template use File | Settings | File Templates.
 */
@RunWith(JUnit4.class)
public class JaxpTest {
	private DocumentBuilderFactory builderFactory;

	@Before
	public void setUp() {
		builderFactory = DocumentBuilderFactory.newInstance("rath.libxml.jaxp.DocumentBuilderFactoryImpl", null);
//		builderFactory = DocumentBuilderFactory.newInstance();
	}

	@After
	public void tearDown() {

	}

	@Test
	public void parseRootElement() throws Exception {
		String xml = "<?xml version=\"1.0\"?><html><head><title>TITLE</title></head><body><p>Good morning</p><p>How are you?</p></body></html>";
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
		Element root = doc.getDocumentElement();
		Assert.assertEquals("html", root.getNodeName());
	}

	@Test
	public void parseElementsByTagName() throws Exception {
		String xml = "<?xml version=\"1.0\"?><html><head><title>TITLE</title></head><body><p>Good morning</p><p>How are you?</p></body></html>";
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
		NodeList plist = doc.getElementsByTagName("p");
		List<String> pTexts = new ArrayList<String>();
		for(int i=0; i<plist.getLength(); i++) {
			pTexts.add(plist.item(i).getFirstChild().getTextContent());
		}
		Assert.assertEquals(pTexts, Arrays.asList("Good morning", "How are you?"));
	}

	@Test
	public void parseRootElementWithFile() throws Exception {
		String xml = "<?xml version=\"1.0\"?><html><head /><body><p>Good morning</p><p>How are you?</p></body></html>";
		File f = new File(System.getProperty("java.io.tmpdir"), "test.xml");
		Utils.writeFile(f, xml);
		f.deleteOnExit();

		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document doc = builder.parse(f);
		Element root = doc.getDocumentElement();
		Assert.assertEquals("html", root.getNodeName());
	}

	@Test
	public void parseRootElementWithInputStream() throws Exception {
		String xml = "<?xml version=\"1.0\"?><html><head /><body><p>Good morning</p><p>How are you?</p></body></html>";

		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));
		Element root = doc.getDocumentElement();
		Assert.assertEquals("html", root.getNodeName());
	}

	@Test
	public void loopCheckElementNameAndAttribute() throws ParserConfigurationException, IOException, SAXException {
//		builderFactory = DocumentBuilderFactory.newInstance();
		String xml = "<?xml version=\"1.0\"?>" +
			"<root>" +
			"<item name=\"a\" />" +
			"<item name=\"b\" />" +
			"<item name=\"c\" />" +
			"</root>";

		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));
		Element root = doc.getDocumentElement();
		NodeList nodeList = root.getChildNodes();
		char ch = 'a';
		for(int i=0; i<nodeList.getLength(); i++) {
			Element node = (Element) nodeList.item(i);
			Assert.assertEquals("node name", "item", node.getNodeName());
			Assert.assertEquals("attribute value", String.valueOf(ch), node.getAttribute("name"));
			ch += 1;
		}
	}

	// firstchild, lastchild, nextsibling, previoussibling, parent
	@Test
	public void testNavigate() throws Exception {
		String xml = "<?xml version=\"1.0\"?>" +
			"<root>" +
			"<a/>" +
			"<b/>" +
			"<c/>" +
			"</root>";

		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));
		Element root = doc.getDocumentElement();
		Assert.assertEquals("a", ((Element)root.getFirstChild()).getTagName());
		Assert.assertEquals("c", ((Element)root.getLastChild()).getTagName());
		Assert.assertEquals("b", ((Element)root.getFirstChild().getNextSibling()).getTagName());
		Assert.assertEquals("b", ((Element)root.getLastChild().getPreviousSibling()).getTagName());
		Assert.assertEquals("root", ((Element) root.getFirstChild().getParentNode()).getTagName());
	}

	private void printNode(Node node) {
		System.out.println("NamespaceURI: " + node.getNamespaceURI());
		System.out.println("LocalName: " + node.getLocalName());
		System.out.println("Prefix: " + node.getPrefix());
		System.out.println("NodeName: " + node.getNodeName());
	}

	@Test
	public void testNamespaceURIPrefixLocalName() throws Exception {
//		builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setNamespaceAware(true);

		String xml = "<?xml version=\"1.0\"?>" +
			"<t:root xmlns=\"http://void.com/\" xmlns:t=\"http://t.com/\">" +
			"<t:item/>" +
			"<child />" +
			"<t:item/>" +
			"</t:root>";
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));
		Element root = doc.getDocumentElement();

		Assert.assertEquals("namespace uri", "http://t.com/", root.getNamespaceURI());
		Assert.assertEquals("local name", "root", root.getLocalName());
		Assert.assertEquals("prefix", "t", root.getPrefix());
		Assert.assertEquals("node name", "t:root", root.getNodeName());
	}

	@Test
	public void testParseExceptionThrownWithLocator() throws Exception {
//		builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setNamespaceAware(true);

		String xml = "<?xml version=\"1.0\"?>" +
			"<t:root xmlns=\"http://void.com/\" xmlns:t=\"http://t.com/\">" +
			"<t:item/>" +
			"<child />" +
			"<t:item/>" +
			"</root>";

		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		try {
			builder.parse(new ByteArrayInputStream(xml.getBytes()));
			Assert.assertEquals(false, true);
		} catch( SAXParseException e ) {
			Assert.assertEquals("public id", null, e.getPublicId());
			Assert.assertEquals("system id", null, e.getSystemId());
			Assert.assertEquals("line", 1, e.getLineNumber());
		}
	}

	@Test
	public void testElementGetAPIs() throws Exception {
//		builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setNamespaceAware(true);

		String xml = "<?xml version=\"1.0\"?>" +
			"<t:root xmlns=\"http://void.com/\" xmlns:t=\"http://t.com/\" id=\"stella\" t:type=\"police\">" +
			"<t:item/>" +
			"<child />" +
			"<t:item/>" +
			"</t:root>";

		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));
		Element root = doc.getDocumentElement();
		Assert.assertEquals("tagName", "t:root", root.getTagName());
		Assert.assertEquals("attribute", "stella", root.getAttribute("id"));
		Assert.assertEquals("attributeNS", "police", root.getAttributeNS("http://t.com/", "type"));
		Assert.assertEquals("attribute(has)", true, root.hasAttribute("id"));
		Assert.assertEquals("attribute(has)", false, root.hasAttribute("__id__"));
		Assert.assertEquals("attributeNS(has)", true, root.hasAttributeNS("http://t.com/", "type"));
		Assert.assertEquals("attributeNS(has)", false, root.hasAttributeNS("http://t.com/", "tipe"));
	}
}
