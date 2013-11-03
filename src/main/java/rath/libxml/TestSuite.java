package rath.libxml;

import java.io.File;

public class TestSuite {
  public static void main( String[] args ) throws Exception {
    // story>storyinfo>author>name 가져오기 
    Document doc = LibXml.parseFile(new File("sample.xml"));
    Node root = doc.getRootElement();
    for (Node underStory : root) {
      if (underStory.getName().equals("storyinfo")) {
        for (Node authorNode : underStory) {
          if (authorNode.getName().equals("author")) {
            // Node.getChildText(boolean formatted);
            String authorName = authorNode.getChildText();
            System.out.printf("author.name : %s%n", authorName);
            System.out.printf("author.@type: %s%n", authorNode.getProp("type"));
          }
        }
      }
    }

    //root.children() returns Iterator?
    // Node n = root.children().next();
  }

  static void notepad() {
    /*
    LXDocument doc = LibXml.parseFile(new File("sample.xml");
    // LibXml.parseString(String) returns LXDocument 
    // LibXml.parseString(String str, String baseDocumentUrl, String encoding(or null), xmlParseOption
    LXElement elem;
    LXNode node;
    
    LXElement rootNode = doc.getRootElement();
    String name = rootNode.getName(); // <story>
    rootNode.getType(); // xmlElementType:int
    LXNode nodes = rootNode.getChildren(); // get first child
    nodes.getNext(); // TODO: 애매하다 시발
    nodes.getPrevious();
    nodes.getDocument(); // as LXDocument 
    nodes.getName(); // element name or .. 
    nodes.getLast();

    nodes.getString(boolean formatted);
    nodes.getString(); // default is true (formatted)

    nodes.getProp(String attributeName); 
    */
  }
}
