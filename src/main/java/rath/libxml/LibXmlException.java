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
		StringBuilder sb = new StringBuilder();
		sb.append("LibXmlException {code=");
		sb.append(code);
		sb.append(", message=");
		sb.append(getMessage());
		if( lineNumber!=0 || columnNumber!=0 ) {
			sb.append(", line=").append(lineNumber);
			sb.append(", column=").append(columnNumber);
		}
		sb.append("}");
		return sb.toString();
	}
}
