package rath.libxml;

/**
 * User: rath
 * Date: 07/11/2013
 * Time: 21:41
 */
public class LibXmlException extends RuntimeException {
	private int code;
	private int lineNumber;
	private int columnNumber;

	public LibXmlException() {

	}

	public LibXmlException(String message) {
		super(message.trim());
	}

	public LibXmlException(int code, String message) {
		this(message);
		setCode(code);
	}

	public LibXmlException(int code, String message, int line, int column) {
		this(code, message);
		this.lineNumber = line;
		this.columnNumber = column;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return this.code;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public int getColumnNumber() {
		return columnNumber;
	}

	@Override
	public String toString() {
		return "LibXmlException{" +
			"code=" + code +
			", message=" + getMessage() +
			", lineNumber=" + lineNumber +
			", columnNumber=" + columnNumber +
			'}';
	}
}
