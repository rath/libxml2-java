#!/bin/sh
gradle --daemon classes
javah -d src/main/c -classpath build/classes/main \
	rath.libxml.LibXml \
	rath.libxml.Document \
	rath.libxml.Node \
	rath.libxml.XPathContext \
	rath.libxml.XPathExpression
