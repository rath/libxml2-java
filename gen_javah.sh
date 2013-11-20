#!/bin/sh
#gradle --daemon classes
javah -force -d src/main/c/autogen -classpath build/classes/main \
	org.xmlsoft.LibXml \
	org.xmlsoft.Document \
	org.xmlsoft.Node \
	org.xmlsoft.XPathContext \
	org.xmlsoft.XPathObject \
	org.xmlsoft.XPathExpression \
	org.xmlsoft.impl.LocatorImpl
