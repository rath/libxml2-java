package org.xmlsoft;

/**
 * This class represents an XML attribute.
 *
 * @author Jang-Ho Hwang, rath@xrath.com
 */
public class Attribute {
	private Namespace ns;
	private String name;
	private String value;

	/**
	 * Create an Attribute object.
	 */
	public Attribute() {

	}

	/**
	 * Return namespace object of this attribute, null if the attribute don't have namespace.
	 * @return namespace object.
	 */
	public Namespace getNs() {
		return ns;
	}

	/**
	 * Check whether this attribute has namespace or not.
	 * @return true if namespace exists, false otherwise.
	 */
	public boolean hasNs() {
		return ns!=null;
	}

	/**
	 * Return the name of this attribute.
	 * @return name of this attribute.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Return the value of this attribute.
	 * @return value of this attribute.
	 */
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

	/**
	 * Create an attribute object with specified namespace, name and value.
	 * <p>There is no difference with using constructor and this.</p>
	 *
	 * @param ns namespace of creating attribute.
	 * @param name name of creating attribute.
	 * @param value value of creating attribute.
	 * @return created attribute object.
	 */
	public static Attribute createInstance(Namespace ns, String name, String value) {
		Attribute a = new Attribute();
		a.ns = ns;
		a.name = name;
		a.value = value;
		return a;
	}

	/**
	 * Return qualified name of this attribute.
	 * <p>If the attribute is associated a namespace, the qualified name would be
	 * <strong>prefix:name</strong>. Otherwise, the result will be same as getName().</p>
	 *
	 * @return qualified name of this attribute.
	 */
	public String getQName() {
		if( ns==null || ns.getPrefix()==null )
			return name;
		return ns.getPrefix() + ":" + name;
	}
}