package org.xmlsoft;

/**
 * <p>The class represents a XML namespace.</p>
 *
 * <p>Namespace object is not associated with underlying native resource</p>
 * @author Jang-Ho Hwang, rath@xrath.com
 */
public class Namespace {
	private String href;
	private String prefix;

	/**
	 * Create a namespace object.
	 */
	public Namespace() {

	}

	/**
	 * Create a namespace object with given namespace URI and prefix.
	 * @param href namespace URI
	 * @param prefix prefix
	 */
	public Namespace(String href, String prefix) {
		setHref(href);
		setPrefix(prefix);
	}

	/**
	 * Return the namespace URI of this object.
	 * @return namespace URI
	 */
	public String getHref() {
		return href;
	}

	/**
	 * Set the namespace URI of this object to the given href.
	 * @param href namespace URI
	 */
	public void setHref(String href) {
		if(href==null)
			throw new NullPointerException("href cannot be null");
		this.href = href;
	}

	/**
	 * Return the prefix of this object.
	 * @return prefix if exists, null otherwise.
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * Set the prefix of this object.
	 * @param prefix prefix of this namespace.
	 */
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
