package rath.libxml;

import org.apache.commons.lang.mutable.MutableBoolean;
import org.apache.commons.lang.mutable.MutableInt;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import rath.libxml.util.Utils;

import javax.xml.parsers.SAXParserFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;

/**
 * User: rath
 * Date: 08/11/2013
 * Time: 21:34
 */
@RunWith(JUnit4.class)
public class SaxTest {
	@Test
	public void simpleByDefault() throws Exception {
		String xml = "<?xml version=\"1.0\"?>" +
			"<f:html lang=\"en\" xmlns:f=\"http://f.com/\">" +
			"<head>" +
			"  <title>Title</title>" +
			"</head>" +
			"<body>" +
			"  <h1>バットマン</h1>" +
			"  <p>He said he would do it as soon as possible</p>" +
			"</body>" +
			"</f:html>";
		SAXParserFactory factory = SAXParserFactory.newInstance("rath.libxml.jaxp.SAXParserFactoryImpl", null);
		factory.setNamespaceAware(true);
		factory.newSAXParser().parse(
//			new ByteArrayInputStream(xml.getBytes("UTF-8")), new DefaultHandler(){
			new InputSource(new StringReader(xml)), new DefaultHandler(){
			Locator domLocator = null;
			@Override
			public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
				// "", head, head,
				// "http://f.com/", html, f:html
				System.out.println("start-e: " + uri + ", " + localName + ", " + qName + ", " + attributes);
				System.out.println("column no: " + domLocator.getColumnNumber());
				// 64, 70, 79, 105, 111, 126
			}

			@Override
			public void setDocumentLocator(Locator locator) {
				this.domLocator = locator;
			}
		});
	}

//	@Test
	public void test() throws Exception {
		URL url = new URL("file:/Users/rath/work/rath-adrenaline/build.xml");
		url.openConnection().getInputStream().read();
	}

	@Test
	public void parseBySystemId() throws Exception {
		String systemId = "file:/Users/rath/work/rath-adrenaline/build.xml";
		systemId = "jar:file:/Users/rath/sdks/ant/lib/ant.jar!/org/apache/tools/ant/antlib.xml";
		LibXml.parseSAXSystemId(systemId, new SAXAdapter() {
			@Override
			public void startElement(String uri, String localName, String qName, Attributes atts) {
				System.out.println(qName);
			}
		}, 0);
	}

	@Test
	public void simple() throws IOException {
		String xml = "<?xml version=\"1.0\"?>" +
			"<!DOCTYPE sample PUBLIC \"PUBLICID\" \"SYSTEMID\">" +
			"<f:html lang=\"en\" f:version=\"5\" xmlns:f=\"http://f.com/\">" +
			"<head>" +
			"  <title>Title</title>" +
			"</head>" +
			"<b:body xmlns:b=\"http://body.com/\">" +
			"  <b:h1>バットマン</b:h1>" +
			"  <?php echo $a ?>" +
			"  <b:p>He said he would do it as soon as possible</b:p>" +
			"</b:body>" +
			"</f:html>";

		final MutableBoolean checkPI = new MutableBoolean(false);
		final MutableInt checkDoc = new MutableInt(0);
		final MutableInt checkChars = new MutableInt(0);
		final List<String> checkElemStart = new ArrayList<String>();
		final List<String> checkElemEnd = new ArrayList<String>();

		File f = new File("test.xml");
		Utils.writeFile(f, xml);
		LibXml.parseSAX(f, new SAXHandler() {
			public Locator domLocator;

			@Override
			public void setDocumentLocator(Locator locator) {
				this.domLocator = locator;
			}

			@Override
			public void startDocument() {
				checkDoc.add(1);
			}

			@Override
			public void characters(char[] ch, int start, int length) {
//				System.out.println("CHARACTER: " + new String(ch, start, length));
				checkChars.add(length);
			}

			@Override
			public void ignorableWhitespace(char[] ch, int start, int length) {
				// TODO: call me
				System.out.println("WHITESPACE: #" + new String(ch, start, length) + "#");
			}

			@Override
			public void startPrefixMapping(String prefix, String uri) {
//				System.out.println("startPrefixMapping: " + prefix + ", " + uri);
			}

			@Override
			public void endPrefixMapping(String prefix) {
//				System.out.println("endPrefixMapping: " + prefix);
			}

			@Override
			public void startElement(String uri, String localName, String qName, Attributes atts) {
				checkElemStart.add(localName);
//				System.out.println("<" + qName + ">");
//				System.out.println("Line  : " + domLocator.getLineNumber());
//				System.out.println("Column: " + domLocator.getColumnNumber());
				System.out.printf("publicId:%s, systemId:%s%n", domLocator.getPublicId(), domLocator.getSystemId());
			}

			@Override
			public void endElement(String uri, String localName, String qName) {
				checkElemEnd.add(localName);
//				System.out.println("</" + qName + ">");
			}

			@Override
			public void processingInstruction(String target, String data) {
				checkPI.setValue(true);
//				System.out.println("<?" + target + " " + data + "?>");
			}

			@Override
			public void warning(LibXmlException exception) {
				System.out.println("* Warning: " + exception);
				exception.printStackTrace();
			}

			@Override
			public void error(LibXmlException exception) {
				System.out.println("* Error: " + exception);
				exception.printStackTrace();
			}

			@Override
			public void fatalError(LibXmlException exception) {
				System.out.println("* Fatal: " + exception);
				exception.printStackTrace();
			}

			@Override
			public void notationDecl(String name, String publicId, String systemId) {
				// TODO: test notationDecl
			}

			@Override
			public void unparsedEntityDecl(String name, String publicId, String systemId, String notationName) {
				// TODO: test unparsedEntityDecl
			}

			@Override
			public void endDocument() {
				checkDoc.add(10);
			}
		}, 0);
		f.delete();

		Assert.assertEquals("document start/end", 11, checkDoc.getValue());
		Assert.assertEquals("characters count", 60, checkChars.getValue());
		Assert.assertEquals("element start", Arrays.asList("html", "head", "title", "body", "h1", "p"), checkElemStart);
		Assert.assertEquals("element end", Arrays.asList("title", "head", "h1", "p", "body", "html"), checkElemEnd);
		Assert.assertEquals("processing instruction", true, checkPI.getValue());
	}
}
