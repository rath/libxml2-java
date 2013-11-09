package rath.libxml;

import org.apache.commons.lang.mutable.MutableInt;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParserFactory;

import java.io.ByteArrayInputStream;

import static org.hamcrest.core.Is.is;

/**
 * User: rath
 * Date: 08/11/2013
 * Time: 21:34
 */
@RunWith(JUnit4.class)
public class SaxTest {
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
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.newSAXParser().parse(
			new ByteArrayInputStream(xml.getBytes("UTF-8")), new DefaultHandler(){
			@Override
			public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
				// "", head, head,
				// "http://f.com/", html, f:html
				System.out.println("start-e: " + uri + ", " + localName + ", " + qName + ", " + attributes);
			}
		});
	}

	@Test
	public void simple() {
		String xml = "<?xml version=\"1.0\"?>" +
			"<f:html lang=\"en\" xmlns:f=\"http://f.com/\">" +
			"<head>" +
			"  <title>Title</title>" +
			"</head>" +
			"<body>" +
			"  <h1>バットマン</h1>" +
			"  <p>He said he would do it as soon as possible</p>" +
			"</body>" +
			"</html>";

		final MutableInt checkDoc = new MutableInt(0);
		final MutableInt checkChars = new MutableInt(0);
		LibXml.parseSAX(xml, new SAXAdapter() {
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
			public void startElement(String uri, String localName, String qName, Attributes atts) {
				// "", head, head,
				// "http://f.com/", html, f:html
				System.out.println("start-e: " + uri + ", " + localName + ", " + qName + ", " + atts);
			}

			@Override
			public void endElement(String uri, String localName, String qName) {
				System.out.println("end  -e: " + uri + ", " + localName + ", " + qName);
			}

			@Override
			public void endDocument() {
				checkDoc.add(10);
			}
		}, 0);

		Assert.assertEquals(11, checkDoc.getValue());
		Assert.assertEquals(58, checkChars.getValue());
	}

	static class SAXAdapter implements SAXHandler {
		@Override
		public void startDocument() {
		}

		@Override
		public void endDocument() {
		}

		@Override
		public void startPrefixMapping(String prefix, String uri) {
		}

		@Override
		public void endPrefixMapping(String prefix) {
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes atts) {
		}

		@Override
		public void endElement(String uri, String localName, String qName) {
		}

		@Override
		public void characters(char[] ch, int start, int length) {
		}

		@Override
		public void ignorableWhitespace(char[] ch, int start, int length) {
		}

		@Override
		public void processingInstruction(String target, String data) {
		}

		@Override
		public void skippedEntity(String name) {
		}

		@Override
		public void warning(SAXParseException exception) {
		}

		@Override
		public void error(SAXParseException exception) {
		}

		@Override
		public void fatalError(SAXParseException exception) {
		}
	}
}
