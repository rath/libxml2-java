# libxml2-java

libxml2-java is Java language binding for well known [libxml2](http://xmlsoft.org/). 

## Features

What you can do with libxml2-java is **very limited at the moement**. It is very early stage of development. It allows you to do the followings 

- Parsing XML document as file or string
- Walking on DOM tree  

## Features coming soon

- XPath support 
- JAXP(JSR 206) provider  
- Modifying DOM tree (xmlNewXXX functions) and update it

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


