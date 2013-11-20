package org.xmlsoft.jaxp;

import javax.xml.xpath.*;

/**
 * User: rath
 * Date: 16/11/2013
 * Time: 11:22
 */
public class XPathFactoryImpl extends XPathFactory {

	@Override
	public boolean isObjectModelSupported(String objectModel) {
		if(objectModel==null)
			throw new NullPointerException();
		if(objectModel.length()==0)
			throw new IllegalArgumentException("objectModel cannot be empty");
		if(objectModel.equals(XPathConstants.DOM_OBJECT_MODEL))
			return true;
		throw new UnsupportedOperationException("objectModel=" + objectModel);
	}

	@Override
	public void setFeature(String name, boolean value) throws XPathFactoryConfigurationException {
		throw new UnsupportedOperationException("setFeature(" + name + ", " + value + ")");
	}

	@Override
	public boolean getFeature(String name) throws XPathFactoryConfigurationException {
		throw new UnsupportedOperationException("getFeature(" + name + ")");
	}

	@Override
	public void setXPathVariableResolver(XPathVariableResolver resolver) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setXPathFunctionResolver(XPathFunctionResolver resolver) {
		throw new UnsupportedOperationException();
	}

	@Override
	public XPath newXPath() {
		return new XPathImpl();
	}
}
