package org.xmlsoft.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.w3c.dom.*;
import org.xmlsoft.*;
import org.xmlsoft.Document;
import org.xmlsoft.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: rath
 * Date: 16/11/2013
 * Time: 04:09
 */
@RunWith(JUnit4.class)
public class RssTest {
	@Test
	public void testWithLibXml() throws Exception {
		File sampleFile = new File("sample-xmls/rss-infoq.xml");
		for (int i = 0; i < 20; i++) {
			Document doc = LibXml.parseFile(sampleFile);

			XPathContext context = doc.createXPathContext();
			XPathObject result = context.evaluate("//item");
			for (Node itemNode : result.nodeset) {
				long l0 = System.nanoTime();
				RssItem.build(itemNode);
				long l1 = System.nanoTime();
				System.out.println((l1 - l0) + " ns");
			}
			result.dispose();
			context.dispose();
			doc.dispose();
		}
	}

	@Test
	public void testWithJAXP() throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		XPathFactory xf = XPathFactory.newInstance();
//		DocumentBuilderFactory dbf = LibXml.createDocumentBuilderFactory();
//		XPathFactory xf = LibXml.createXPathFactory();

		File sampleFile = new File("sample-xmls/rss-infoq.xml");
		for (int i = 0; i < 20; i++) {
			DocumentBuilder db = dbf.newDocumentBuilder();
			org.w3c.dom.Document doc = db.parse(sampleFile);

			XPath xpath = xf.newXPath();
			NodeList nl = (NodeList) xpath.evaluate("//item", doc, XPathConstants.NODESET);
			for(int x=0; x<nl.getLength(); x++) {
				org.w3c.dom.Node n = nl.item(x);
				long l0 = System.nanoTime();
				RssItem.build(nl.item(x));
				long l1 = System.nanoTime();
				System.out.println((l1 - l0) + " ns");
			}

		}
	}

	static class RssItem {
		static DateFormat fmtDate = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
		public String title;
		public String link;
		public String description;
		public List<String> categories = new ArrayList<String>();
		public Date pubDate;
		public String guid;

		public static RssItem build(Node node) throws ParseException {
			RssItem item = new RssItem();
			Node child = node.children();
			while(child!=null) {
				if( child.getType()==Node.TYPE_ELEMENT ) {
//					String name = child.getName();
//					String value = child.getChildText();
//					fillFields(item, name, value);
				}
				child = child.getNext();
			}
			return item;
		}

		public static RssItem build(org.w3c.dom.Node node) throws ParseException {
			RssItem item = new RssItem();
			NodeList nl = node.getChildNodes();
			for(int i=0; i<nl.getLength(); i++) {
				org.w3c.dom.Node child = nl.item(i);
				if( child.getNodeType()==org.w3c.dom.Node.ELEMENT_NODE ) {
//					String name = child.getNodeName();
//					String value = child.getTextContent();
//					fillFields(item, name, value);
				}
			}
			return item;
		}

		private static void fillFields(RssItem item, String name, String value) throws ParseException {
			if(name.equals("title"))
				item.title = value;
			else if(name.equals("link"))
				item.link = value;
			else if(name.equals("description"))
				item.description = value;
			else if(name.equals("category"))
				item.categories.add(value);
			else if(name.equals("pubDate"))
				item.pubDate = null;//fmtDate.parse(value);
			else if(name.equals("guid"))
				item.guid = value;
		}

		@Override
		public String toString() {
			return "RssItem{" +
				"title='" + title + '\'' +
				", link='" + link + '\'' +
				", description='" + description + '\'' +
				", categories=" + categories +
				", pubDate=" + pubDate +
				", guid='" + guid + '\'' +
				'}';
		}
	}
}
