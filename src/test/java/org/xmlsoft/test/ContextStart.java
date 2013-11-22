package org.xmlsoft.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.xmlsoft.LibXml;

/**
 * User: rath
 * Date: 22/11/2013
 * Time: 14:54
 */
@RunWith(JUnit4.class)
public class ContextStart {
	@Test
	public void foo() {
		LibXml.setAutoRetainDisposable();
	}
}
