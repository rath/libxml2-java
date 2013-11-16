package rath.libxml.jaxp;

import org.w3c.dom.*;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import rath.libxml.*;
import rath.libxml.Node;
import rath.libxml.util.Utils;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.xpath.*;
import javax.xml.xpath.XPathExpression;
import java.io.IOException;

/**
 * User: rath
 * Date: 16/11/2013
 * Time: 11:26
 */
public class XPathImpl implements XPath {
	@Override
	public void reset() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setXPathVariableResolver(XPathVariableResolver resolver) {
		throw new UnsupportedOperationException();
	}

	@Override
	public XPathVariableResolver getXPathVariableResolver() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setXPathFunctionResolver(XPathFunctionResolver resolver) {
		throw new UnsupportedOperationException();
	}

	@Override
	public XPathFunctionResolver getXPathFunctionResolver() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setNamespaceContext(NamespaceContext namespaceContext) {
		throw new UnsupportedOperationException();
	}

	@Override
	public NamespaceContext getNamespaceContext() {
		throw new UnsupportedOperationException();
	}

	@Override
	public XPathExpression compile(String expr) throws XPathExpressionException {
		return new XPathExpressionImpl(LibXml.compileXPath(expr));
	}

	@Override
	public Object evaluate(String expr, Object item, QName qName) throws XPathExpressionException {
		if (!(item instanceof rath.libxml.jaxp.NodeImpl ||
			  item instanceof rath.libxml.jaxp.DocumentImpl)) {
			throw new UnsupportedOperationException("starting context should be libxml2 node object");
		}

		DocumentImpl document = null;
		Node contextNode = null;

		if( item instanceof NodeImpl ) {
			document = (DocumentImpl) ((NodeImpl)item).getOwnerDocument();
			contextNode = ((NodeImpl)item).impl;
		} else if( item instanceof DocumentImpl ) {
			document = (DocumentImpl)item;
		}

		XPathContext ctx = document.getImpl().createXPathContext();
		ctx.setContextNode(contextNode);
		XPathObject result;
		try {
			result = ctx.evaluate(expr);
		} catch( InvalidXPathExpressionException e ) {
			throw new XPathExpressionException(e);
		}

		Object ret = filterXPathObjectToJaxpObject(document, qName, result);
		result.dispose();
		return ret;
	}

	static Object filterXPathObjectToJaxpObject(DocumentImpl document, QName qName, XPathObject result) {
		Object ret;
		if( qName==XPathConstants.NODESET )
			ret = new NodeListImpl(document, result.nodeset);
		else
		if( qName==XPathConstants.STRING )
			ret = result.castToString();
		else
		if( qName==XPathConstants.NUMBER )
			ret = result.castToNumber();
		else
		if( qName==XPathConstants.BOOLEAN )
			ret = result.castToBoolean();
		else
			throw new UnsupportedOperationException();
		return ret;
	}

	@Override
	public String evaluate(String expr, Object item) throws XPathExpressionException {
		return String.valueOf(evaluate(expr, item, XPathConstants.STRING));
	}

	@Override
	public Object evaluate(String expr, InputSource inputSource, QName qName) throws XPathExpressionException {
		rath.libxml.Document doc = null;
		try {
			doc = LibXml.parseString(Utils.loadInputSource(inputSource));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return evaluate(expr, new DocumentImpl(doc), qName);
	}

	@Override
	public String evaluate(String expr, InputSource inputSource) throws XPathExpressionException {
		return (String) evaluate(expr, inputSource, XPathConstants.STRING);
	}
}
