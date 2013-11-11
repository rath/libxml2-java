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
import java.net.URL;
import java.util.*;

/**
 *
 * User: rath
 * Date: 06/11/2013
 * Time: 04:48
 *
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
	public void testRootElement() throws Exception {
		String xml = "<?xml version=\"1.0\"?><html><head><title>TITLE</title></head><body><p>Good morning</p><p>How are you?</p></body></html>";
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
		Element root = doc.getDocumentElement();
		Assert.assertEquals("html", root.getNodeName());
	}

	@Test
	public void testElementsByTagNameWithoutNamespace() throws Exception {
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

//	@Test
	public void testElementsByTagNameWithNamespace() throws Exception {
		builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setNamespaceAware(true);

		String xml = "<?xml version=\"1.0\"?>" +
			"<t:root xmlns=\"http://void.com/\" xmlns:t=\"http://t.com/\" id=\"stella\" t:type=\"police\">" +
			"<t:item id=\"a\"/>" +
			"<child id=\"1\"/>" +
			"<t:item id=\"b\"/>" +
			"<child id=\"2\"/>" +
			"</t:root>";

		// TODO: Can I utilize xmlSearchNs or xmlSearchNsByHref? the problem is it searchs a specific node and recurse up.
		// I don't have such a node

		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));
		Element root = doc.getDocumentElement();

		List<String> expectedList = Arrays.asList("1", "2");
		List<String> actualList = new ArrayList<String>();
		NodeList nl = root.getElementsByTagNameNS("http://void.com/", "child");
		for(int i=0; i<nl.getLength(); i++) {
			Element elem = (Element) nl.item(i);
			actualList.add(elem.getAttribute("id"));
		}
		Assert.assertArrayEquals("elementsByTagName", expectedList.toArray(), actualList.toArray());

		expectedList = Arrays.asList("a", "b");
		actualList.clear();
		nl = root.getElementsByTagNameNS("http://t.com/", "item");
		for(int i=0; i<nl.getLength(); i++) {
			Element elem = (Element) nl.item(i);
			actualList.add(elem.getAttribute("id"));
		}
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
		Assert.assertEquals("c", ((Element) root.getLastChild()).getTagName());
		Assert.assertEquals("b", ((Element) root.getFirstChild().getNextSibling()).getTagName());
		Assert.assertEquals("b", ((Element) root.getLastChild().getPreviousSibling()).getTagName());
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
			"<t:item id=\"a\"/>" +
			"<child id=\"1\"/>" +
			"<t:item id=\"b\"/>" +
			"<child id=\"2\"/>" +
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

	@Test
	public void testAddRemoveChild() throws Exception {
//		builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setNamespaceAware(true);

		String xml = "<?xml version=\"1.0\"?>" +
			"<root>" +
			"<item id=\"a\"/>" +
			"<child id=\"1\"/>" +
			"<item id=\"b\"/>" +
			"<child id=\"2\"/>" +
			"</root>";

		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));
		Element root = doc.getDocumentElement();

		Element newElem = doc.createElement("tail");
		newElem.setAttribute("id", "3");
		Node appended = root.appendChild(newElem);

		Assert.assertEquals("added element",  "tail", root.getLastChild().getNodeName());
		Assert.assertEquals("added attribute",  "3", ((Element)root.getLastChild()).getAttribute("id"));

		root.setAttribute("id", "root");
		Assert.assertEquals("root attribute set", "root", root.getAttribute("id"));
		root.removeAttribute("id");
		Assert.assertEquals("root attribute remove", null, root.getAttribute("id"));

		root.removeChild(appended);
		Assert.assertEquals("removed element", "child", root.getLastChild().getNodeName());
		Assert.assertEquals("removed element", "2", ((Element) root.getLastChild()).getAttribute("id"));
	}

	@Test
	public void testAddText() throws Exception {
//		builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setNamespaceAware(true);

		String xml = "<?xml version=\"1.0\"?>" +
			"<root>" +
			"<item id=\"a\"/>" +
			"<child id=\"1\"/>" +
			"<item id=\"b\"/>" +
			"<child id=\"2\"/>" +
			"</root>";

		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));
		Element root = doc.getDocumentElement();
		Text text = doc.createTextNode("hello world");
		root.insertBefore(text, root.getFirstChild().getNextSibling());

		Assert.assertEquals("append text", "hello world", root.getChildNodes().item(1).getTextContent());
	}

	@Test
	public void testgetAttributesWithNamedNodeMap() throws Exception {
//		builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setNamespaceAware(true);

		File f = new File(System.getProperty("user.dir"), "src/test/resources/sample-springbeans.xml");
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document doc = builder.parse(f);
		Element root = doc.getDocumentElement();
//		System.out.println("* current impl:  " + doc.getClass().getName());

		// id, class
		Map<String, String> actualMap = new HashMap<String, String>();

		NodeList list = root.getChildNodes();
		for(int i=0; i<list.getLength(); i++) {
			Node node = list.item(i);
			if( node instanceof Element ) {
				Element elem = (Element)node;
				if( elem.getTagName().equals("beans:bean") ) {
//					System.out.println("bean[" + elem.getAttribute("id") + "]");
					NamedNodeMap map = elem.getAttributes();
					String valueId = null;
					String valueClass = null;
					for(int x=0; x<map.getLength(); x++) {
						Node attr = map.item(x);
						if( attr.getNodeName().equals("id") )
							valueId = attr.getNodeValue();
						if( attr.getNodeName().equals("class") )
							valueClass = attr.getNodeValue();
					}
					if(valueId!=null && valueClass!=null)
						actualMap.put(valueId, valueClass);

				}
			}
		}

		Map<String, String> expected = new HashMap<String, String>();
		expected.put("authenticationManager", "org.springframework.security.providers.ProviderManager");
		expected.put("daoAuthenticationProvider", "org.springframework.security.providers.dao.DaoAuthenticationProvider");
		expected.put("loggerListener", "org.springframework.security.event.authentication.LoggerListener");
		Assert.assertEquals("Attributes with NamedNodeMap", expected, actualMap);
	}

	@Test
	public void testNodeValueOnTextNode() throws Exception {
		String xml = "<?xml version=\"1.0\"?>" +
			"<root>" +
			"  <property>Hello World</property>" +
			"</root>";

//    builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setNamespaceAware(true);

		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document doc = builder.parse(Utils.createInputSource(xml));
		Element root = doc.getDocumentElement();
		NodeList list = root.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			if (node instanceof Element) {
				Element elem = (Element) node;
				Node textNode = elem.getChildNodes().item(0);
				Assert.assertEquals("Text.getNodeValue", textNode.getNodeValue(), textNode.getTextContent());
				return;
			}
		}
		Assert.assertEquals("Text.getNodeValue", true, false);
	}

}
