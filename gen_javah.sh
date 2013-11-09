#!/bin/sh
gradle --daemon classes
javah -d src/main/c/autogen -classpath build/classes/main \
	rath.libxml.LibXml \
	rath.libxml.Document \
	rath.libxml.Node \
	rath.libxml.XPathContext \
	rath.libxml.XPathObject \
	rath.libxml.XPathExpression \
  rath.libxml.impl.LocatorImpl
