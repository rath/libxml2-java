package rath.libxml;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.w3c.dom.Element;
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
	public void test() throws Exception {
		// LibXml.setDefaultJAXPImplementation();

//		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilderFactory dbf = LibXml.createDocumentBuilderFactory();
		DocumentBuilder builder = dbf.newDocumentBuilder();
		org.w3c.dom.Document doc = builder.newDocument();
		doc.setXmlVersion("1.0");

		Element items = doc.createElement("items");
		doc.appendChild(items);

		for(String str : Arrays.asList("1", "2", "3")) {
			Element item = doc.createElement("item");
			item.appendChild(doc.createTextNode(str));
			items.appendChild(item);
		}

		Document impl = ((DocumentImpl) doc).getImpl();
		impl.save(new File("test.xml"), "UTF-8");

//		Transformer transformer = TransformerFactory.newInstance().newTransformer();
//		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//		transformer.transform(new DOMSource(doc), new StreamResult(System.out));
	}
}
