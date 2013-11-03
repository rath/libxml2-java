#!/bin/sh
gradle compileJava
javah -d src/main/c -classpath build/classes/main \
	rath.libxml.LibXml \
	rath.libxml.Document \
	rath.libxml.Node
