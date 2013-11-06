package rath.libxml;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.w3c.dom.*;
import org.w3c.dom.Document;
import rath.libxml.util.Utils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
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
}
