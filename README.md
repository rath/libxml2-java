# libxml2-java

libxml2-java is Java language binding for well known [libxml2](http://xmlsoft.org/). 

## Features

What you can do with libxml2-java is **very limited at the moment**. It is very early stage of development. It allows you to do the followings 

- Parsing XML document as file or string
- Walking on DOM tree  

## Features coming soon

- XPath support 
- JAXP(JSR 206) provider  
- Modifying DOM tree (xmlNewXXX functions) and update it

## Build instructions

You need essential build tools such as [Java Development Kit](http://en.wikipedia.org/wiki/Java_Development_Kit) 6 or higher, [Gradle](http://www.gradle.org), [GNU Make](http://www.gnu.org/software/make/) and most importantly you should have libxml2 development package on your system. 

If you need to specify jdk directory instead of system default location, then use --with-jdk option 

	./configure --with-jdk=/opt/local/java

Otherwise, configure script would try to detect where JDK is installed on your system.

### Mac OS X
	$ sudo port install libxml2
	$ ./configure 
	$ gradle build 
### Ubuntu
	$ sudo apt-get install libxml2-dev
	$ ./configure 
	$ gradle build
### CentOS
	$ sudo yum install libxml2-devel
	$ ./configure 
	$ gradle build

While you freely run _make_ command many times on your own hand, this step is not needed. On running gradle build script, a task named _processNativeResources_ will execute _make_. 

## Examples 

Print all child elements under the root node.

```java
Document doc = LibXml.parseFile(new File("sample.xml"));
Node rootNode = doc.getRootElement();
for(Node node : rootNode) {
  out.printf("%s: type=%s%n", node.getName(), node.getType());
}
```

Use libxml2-java as default DocumentBuilder by modifying system property.

	java -Djava.xml.parsers.DocumentBuilderFactory=rath.libxml.jaxp.DocumentBuilderFactory ... 


