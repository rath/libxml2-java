package rath.libxml;

/**
 * Created with IntelliJ IDEA.
 * User: rath
 * Date: 04/11/2013
 * Time: 20:06
 * To change this template use File | Settings | File Templates.
 */
public class InvalidXPathExpressionException extends RuntimeException {
	public InvalidXPathExpressionException() {

	}

	public InvalidXPathExpressionException(String message) {
		super(message);
	}
}
