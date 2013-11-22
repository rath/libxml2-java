package org.xmlsoft.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.xmlsoft.LibXml;

/**
 * User: rath
 * Date: 16/11/2013
 * Time: 02:34
 */
@RunWith(JUnit4.class)
public class ContextEnd {

	@Test
	public void simple() throws Exception {
		LibXml.disposeAutoRetainedItems();
		LibXml.printTcmallocStat();
	}
}
