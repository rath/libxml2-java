package rath.libxml.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.w3c.dom.Element;
import rath.libxml.Document;
import rath.libxml.LibXml;
import rath.libxml.Node;
import rath.libxml.jaxp.DocumentImpl;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
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

		doc.save(System.out, "UTF-8");
	}
}
