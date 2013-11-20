package org.xmlsoft;

/**
 * 
 * User: rath
 * Date: 03/11/2013
 * Time: 04:13
 * 
 */
public class Namespace {
	private String href;
	private String prefix;

	public Namespace() {

	}

	public Namespace(String href, String prefix) {
		setHref(href);
		setPrefix(prefix);
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		if(href==null)
			throw new NullPointerException("href cannot be null");
		this.href = href;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[Namespace] ");
		if( prefix!=null ) {
			sb.append(prefix);
			sb.append(" ");
		}
		sb.append(href);
		return sb.toString();
	}
}
