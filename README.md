# libxml2-java

libxml2-java is Java language binding for well-known [libxml2](http://xmlsoft.org/). 
Current implementation of libxml2-java is **very limited at the moment**. It's very early stage of development. 


## Progress

- (50%) DOM 3
- (65%) SAX 2
- (20%) XPath 
- (40%) As JAXP(JSR 206) provider  

## Build instructions

You need essential build tools such as [Java Development Kit](http://en.wikipedia.org/wiki/Java_Development_Kit) 6 or higher, [Gradle](http://www.gradle.org), [GNU Make](http://www.gnu.org/software/make/) and most importantly you should have libxml2 development package on your system. 

If you need to specify jdk directory manually over system default location, then use --with-jdk option 

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

## Memory consideration

 libxml2-java will free underlying native resources on Object.finalize() by default. It makes you not hassle with memory management issue. However If you have to claim it explicitly, Document.dispose(), XPathContext.dispose() will do the job. Note that Docucment.dispose() will free all children nodes as well. 

## Examples 

* Print all child elements under the root node.

```java
String xml = "<?xml version=\"1.0\"?><root><item /><item /><item /></root>";

Document doc = LibXml.parseString(xml);
Node rootNode = doc.getRootElement();
for(Node node : rootNode) {
  out.printf("%s: type=%s%n", node.getName(), node.getType());
}
```

* Use libxml2-java as default DocumentBuilder by passing *rath.libxml.jaxp.DocumentBuilderFactoryImpl* as *java.xml.parsers.DocumentBuilderFactory* system property. Then, it allows you to start coding with the standard JAXP API.

```java
import org.w3c.dom.*;

DocumentBuilder builder = DocumentBuilderFactory.newInstance("rath.libxml.jaxp.DocumentBuilderFactoryImpl", null);
// file: ?xml version="1.0"?><html><head /><body><p>Good morning</p><p>How are you?</p></body></html>
Document doc = builder.parse(new File("sample.xml"));
Assert.assertEquals("html", doc.getDocumentElement().getNodeName());
```

  
* XPath 

```java
String xml = "<?xml version=\"1.0\"?><root><item>Apple</item><item tag=\"1\">Bear</item><item>Cider</item></root>";

Document doc = LibXml.parseString(xml);
XPathContext ctx = doc.createXPathContext();
XPathObject result = ctx.evaluate("//item[@tag=\"1\"]");

out.println(result.getFirstNode().getChildText()); // Bear
```

## Notes

- Make sure libxml2 library is configured with --with-threads option.
