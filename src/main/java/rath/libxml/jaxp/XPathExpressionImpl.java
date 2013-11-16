package rath.libxml.jaxp;

import org.xml.sax.InputSource;
import rath.libxml.Node;
import rath.libxml.XPathContext;
import rath.libxml.XPathExpression;
import rath.libxml.XPathObject;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

/**
 * User: rath
 * Date: 16/11/2013
 * Time: 14:26
 */
public class XPathExpressionImpl implements javax.xml.xpath.XPathExpression {
	private final XPathExpression impl;

	public XPathExpressionImpl(XPathExpression impl) {
		this.impl = impl;
	}

	@Override
	public Object evaluate(Object item, QName qName) throws XPathExpressionException {
		if (!(item instanceof rath.libxml.jaxp.NodeImpl ||
			  item instanceof rath.libxml.jaxp.DocumentImpl)) {
			throw new UnsupportedOperationException("starting context should be libxml2 node object");
		}

		DocumentImpl document = null;
		Node internalContext = null;

		if( item instanceof NodeImpl ) {
			document = (DocumentImpl) ((NodeImpl)item).getOwnerDocument();
			internalContext = ((NodeImpl)item).impl;
		} else if( item instanceof DocumentImpl ) {
			document = (DocumentImpl)item;
			internalContext = document.getImpl();
		}

		XPathContext context = document.getImpl().createXPathContext();
		context.setContextNode(internalContext);
		XPathObject result = context.evaluate(impl);
		Object ret = XPathImpl.filterXPathObjectToJaxpObject(document, qName, result);
		result.dispose();
		return ret;
	}

	@Override
	public String evaluate(Object item) throws XPathExpressionException {
		return (String) evaluate(item, XPathConstants.STRING);
	}

	@Override
	public Object evaluate(InputSource inputSource, QName qName) throws XPathExpressionException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String evaluate(InputSource inputSource) throws XPathExpressionException {
		throw new UnsupportedOperationException();
	}
}
