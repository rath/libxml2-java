package rath.libxml;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;

/**
 * User: rath
 * Date: 16/11/2013
 * Time: 09:27
 */
@RunWith(JUnit4.class)
public class XPathTest {
	@Test
	public void test() throws Exception {
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
}
