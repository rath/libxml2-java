package org.xmlsoft.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.w3c.dom.Element;
import org.xmlsoft.Document;
import org.xmlsoft.LibXml;
import org.xmlsoft.Node;
import org.xmlsoft.jaxp.DocumentImpl;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.StringWriter;
import java.util.Arrays;

/**
 * User: rath
 * Date: 17/11/2013
 * Time: 17:14
 */
@RunWith(JUnit4.class)
public class DomManipulationTest {
	@Test
	public void testWithJaxp() throws Exception {
		// LibXml.setDefaultJAXPImplementation();

//		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilderFactory dbf = LibXml.createDocumentBuilderFactory();
		DocumentBuilder builder = dbf.newDocumentBuilder();
		org.w3c.dom.Document doc = builder.newDocument();
		doc.setXmlVersion("1.0");

		doc.appendChild(doc.createComment("ordinary items"));

		Element items = doc.createElement("items");
		doc.appendChild(items);

		for(String str : Arrays.asList("1", "2", "3", "헛둘")) {
			Element item = doc.createElement("item");
			item.appendChild(doc.createCDATASection(str));
			items.appendChild(item);

			Element itemWithNs = doc.createElementNS("http://foo.com/", "f:item");
			item.setAttribute("id", str);
			items.appendChild(itemWithNs);
		}

		items.appendChild(doc.createProcessingInstruction("php", "echo(1)"));

		Document impl = ((DocumentImpl) doc).getImpl();
		impl.save(new File("test.xml"), "UTF-8");

//		Transformer transformer = TransformerFactory.newInstance().newTransformer();
//		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//		transformer.transform(new DOMSource(doc), new StreamResult(System.out));
	}

	@Test
	public void testDocumentSaveWithOutputStream() throws Exception {
		Document doc = LibXml.createDocument();
		Node root = doc.createElement("items");
		doc.addChild(root);

		for(String str : Arrays.asList("1", "2", "3")) {
			Node item = doc.createElement("item");
			item.setAttribute("id", str);
			root.addChild(item);
		}

		StringWriter sw = new StringWriter();
		doc.save(sw, "UTF-8");
		String exported = sw.toString();
		Assert.assertEquals("", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
			"<items><item id=\"1\"/><item id=\"2\"/><item id=\"3\"/></items>\n", exported);
	}
}
