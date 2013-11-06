package rath.libxml;

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
		return "Namespace{" +
			"href='" + href + '\'' +
			", prefix='" + prefix + '\'' +
			'}';
	}
}
