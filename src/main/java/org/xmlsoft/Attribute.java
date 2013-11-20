package org.xmlsoft;

/**
 *
 * User: rath
 * Date: 10/11/2013
 * Time: 09:29
 */
public class Attribute {
	private Namespace ns;
	private String name;
	private String value;

	public Namespace getNs() {
		return ns;
	}

	public boolean hasNs() {
		return ns!=null;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "Attribute{" +
			"ns=" + ns +
			", name='" + name + '\'' +
			", value='" + value + '\'' +
			'}';
	}

	public static Attribute createInstance(Namespace ns, String name, String value) {
		Attribute a = new Attribute();
		a.ns = ns;
		a.name = name;
		a.value = value;
		return a;
	}

	public String getQName() {
		if( ns==null || ns.getPrefix()==null )
			return name;
		return ns.getPrefix() + ":" + name;
	}
}