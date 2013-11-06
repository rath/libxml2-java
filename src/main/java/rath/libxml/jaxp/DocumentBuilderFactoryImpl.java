package rath.libxml.jaxp;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created with IntelliJ IDEA.
 * User: rath
 * Date: 06/11/2013
 * Time: 04:40
 * To change this template use File | Settings | File Templates.
 */
public class DocumentBuilderFactoryImpl extends DocumentBuilderFactory {

	@Override
	public DocumentBuilder newDocumentBuilder() throws ParserConfigurationException {
		return new DocumentBuilderImpl();
	}

	@Override
	public void setAttribute(String name, Object value) throws IllegalArgumentException {
		System.out.println("DocumentBuildertFactoryImpl.setAttribute(" + name + ", " + value + ")");
	}

	@Override
	public Object getAttribute(String name) throws IllegalArgumentException {
		System.out.println("DocumentBuildertFactoryImpl.getAttribute(" + name + ")");
		return null;
	}

	@Override
	public void setFeature(String name, boolean value) throws ParserConfigurationException {
		System.out.println("DocumentBuildertFactoryImpl.setFeature(" + name + ", " + value + ")");
	}

	@Override
	public boolean getFeature(String name) throws ParserConfigurationException {
		System.out.println("DocumentBuildertFactoryImpl.getFeature(" + name + ")");
		return false;
	}
}
