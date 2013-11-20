package org.xmlsoft;

/**
 * XPathExpressionException represents an error in an XPath expression.
 *
 * @author Jang-Ho Hwang, rath@xrath.com
 */
public class XPathExpressionException extends RuntimeException {
	/**
	 * Constructs a new XPathExpressionException.
	 */
	public XPathExpressionException() {

	}

	/**
	 * Constructs a new XPathExpressionException with the specified detail message.
	 */
	public XPathExpressionException(String message) {
		super(message);
	}
}
