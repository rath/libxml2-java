# libxml2-java

libxml2-java is Java language binding for well-known [libxml2](http://xmlsoft.org/). 

## Document 

Javadoc is [available online](http://rath.github.io/libxml2-java/javadoc/).

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

 libxml2-java will free underlying native resources on Object.finalize() by default. It makes you not hassle with memory management issue. However If you have to claim it explicitly, Document.dispose(), XPathContext.dispose() and all nodes implement Disposable will do the job. Note that Docucment.dispose() will free all children nodes as well. 

 If you don't believe timing of `Object.finalize()` and calling dispose() method manually as I don't, libxml2-java allows you to handle memory de-allocation by calling autoDispose(). It will retain Disposable items to backend list until you claim `LibXml.disposeAutoRetainedItems()`. If it also makes you hassle, you can avoid it by calling `LibXml.setAutoRetainEveryDisposable`. This would retain every disposable objects automatically until you call `LibXml.disposeAutoRetainedItems()`. 
 The backend list holding disposable items is not thread-safe and is managed by internal thread-local-storage. so you need to call `LibXml.disposeAutoRetainedItems()` on the same thread as the thread allocated (retained) that items.

```java
Document doc = LibXml.parseString(xml).autoDispose();
// do your job freely.
LibXml.disposeAutoRetainedItems();
```

or

```java
LibXml.setAutoRetainEveryDisposable();
// use document, xpath without calling dispose() or autoDispose()
LibXml.disposeAutoRetainedItems();
```

Calling LibXml.printTcmallocStat() allows you to investigate current allocated native memory map by printing status to standard output. If you configure libxml2-java without [TCMalloc](http://goog-perftools.sourceforge.net/doc/tcmalloc.html), LibXml.printTcmallocStat() won't print anything.

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

* Use libxml2-java as default DocumentBuilder by passing *org.xmlsoft.jaxp.DocumentBuilderFactoryImpl* as *java.xml.parsers.DocumentBuilderFactory* system property. Then, it allows you to start coding with the standard JAXP API.

```java
DocumentBuilder builder = LibXml.createDocumentBuilderFactory().newDocumentBuilder();
// <?xml version="1.0"?><html><head /><body><p>Good morning</p><p>How are you?</p></body></html>
Document doc = builder.parse(new File("sample.xml"));
Assert.assertEquals("html", doc.getDocumentElement().getNodeName());
```
  
* XPath 

```java
String xml = "<?xml version=\"1.0\"?>";
xml += "<root>";
xml += "<item>Apple</item>";
xml += "<item tag=\"1\">Bear</item>";
xml += "<item>Cider</item>";
xml += "</root>";

Document doc = LibXml.parseString(xml);
XPathContext ctx = doc.createXPathContext();
XPathObject result = ctx.evaluate("//item[@tag=\"1\"]");

out.println(result.getFirstNode().getChildText()); // Bear
```

## Test 

### Compatibility with JAXP

#### SAX 

SAXParserFactory implementation has been tested with 

- Apache Ant 1.9
 - Build simple projects
 - Build with Ivy
 - Build android projects
- Apache Tomcat 7
 - Launched with web.xml, server.xml, context.xml, and *my* webapps works well as usual

by setting _org.xmlsoft.jaxp.SAXParserFactoryImpl_ as _javax.xml.parsers.SAXParserFactory_ system property then adding libxml2-java.jar on classpath. 

#### DOM

DocumentBuilderFactory implementation has been tested with  

- Spring Framework 3.2
 - Simple app using Spring Data JPA

by setting _org.xmlsoft.jaxp.DocumentBuilderFactoryImpl_ as _javax.xml.parsers.DocumentBuilderFactory_ system property then adding libxml2-java.jar on classpath.

### Unit tests 

- BasicTest: Test cases building DOM with XML and navigating dom tree
- JaxpTest: Test cases with DocumentBuilderFactory
- SaxTest: Test cases with bare and JSR SAX
- XPathTest: Test cases for XPath APIs.
- DomManipulationTest: Test cases for creating and update DOM.

## Notes

- Make sure libxml2 library is configured with --with-threads option.

## License

libxml2-java is licensed under MIT.
