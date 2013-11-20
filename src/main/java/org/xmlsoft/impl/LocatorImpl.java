package org.xmlsoft.impl;

import org.xml.sax.Locator;

/**
 * User: rath
 * Date: 09/11/2013
 * Time: 23:08
 */
public class LocatorImpl implements Locator {
	private final long p; //

	public LocatorImpl(long p) {
		this.p = p;
	}

	@Override
	public String getPublicId() {
		String str = getPublicIdImpl();
		return str;
	}

	private native String getPublicIdImpl();

	@Override
	public String getSystemId() {
		String str = getSystemIdImpl();
		return str;
	}

	private native String getSystemIdImpl();

	@Override
	public int getLineNumber() {
		int no = getLineNumberImpl();
		return no;
	}

	private native int getLineNumberImpl();

	@Override
	public int getColumnNumber() {
		int no = getColumnNumberImpl();
		return no;
	}

	private native int getColumnNumberImpl();
}
