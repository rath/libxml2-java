package rath.libxml;

import rath.libxml.impl.SAXHandlerEngine;
import rath.libxml.jaxp.DocumentBuilderFactoryImpl;
import rath.libxml.jaxp.SAXParserFactoryImpl;
import rath.libxml.jaxp.XPathFactoryImpl;
import rath.libxml.util.Utils;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

/**
 * <p>This class includes various static method for parsing and building XML document.</p>
 *
 * @author Jang-Ho Hwang, rath@xrath.com
 */
public class LibXml {
	static {
		loadNativeLibrary();
	}

	private static Logger logger = Logger.getLogger(LibXml.class.getName());

	private static synchronized void loadNativeLibrary() {
		String fullname = System.mapLibraryName("hello");
		String suffix = fullname.substring(fullname.lastIndexOf('.'));
		if (suffix.equalsIgnoreCase(".jnilib")) suffix = ".dylib";
		String libname = "libxml2j" + suffix;

		File targetLibrary = null;

		File localXcodeLibrary = new File("/Users/rath/Library/Developer/Xcode/DerivedData/libxml2-java-fkrlovtvxnmqhfhhoxmlslcqmtjx/Build/Products/Debug/" + libname);
		if( localXcodeLibrary.exists() && localXcodeLibrary.lastModified()+(1000L*60L*30L) > System.currentTimeMillis()) {
			targetLibrary = localXcodeLibrary;
			System.out.println("*****************************************");
			System.out.println("* Using libxml2j.dylib built on Xcode 5 *");
			System.out.println("*****************************************");
		}

		File localLibrary = new File("src/main/c/" + libname);
		if( targetLibrary==null && localLibrary.exists() ) {
			targetLibrary = localLibrary;
		}

		if( targetLibrary==null ) {
			String bundleLibname = Utils.getPlatformDependentBundleName();

			targetLibrary = new File(System.getProperty("java.io.tmpdir"), bundleLibname);
			try {
				Utils.copyAndClose(LibXml.class.getResourceAsStream("/" + bundleLibname), targetLibrary);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}

		try {
			System.load(targetLibrary.getAbsolutePath());
		} catch( UnsatisfiedLinkError e ) {
			e.printStackTrace();
			System.err.println("==============================================");
			System.err.println(" We can't find libxml2 on your system         ");
			System.err.println("  centos $ sudo yum install libxml2-devel     ");
			System.err.println("  ubuntu $ sudo apt-get install libxml2-dev   ");
			System.err.println("  macosx $ sudo port install libxml2          ");
			System.err.println("==============================================");
			System.exit(1);
		}

		initInternalParser();
	}

	private LibXml() {

	}

	/**
	 * <p>Convenience method for registering JAXP implementations of libxml2-java as
	 * default DocumentBuilderFactory and SAXParserFactory.</p>
	 *
	 * <p>Invoking this method is equivalent of followings</p>
	 * <blockquote>
	 *   System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "rath.libxml.jaxp.DocumentBuilderFactoryImpl");
	 *   System.setProperty("javax.xml.parsers.SAXParserFactory", "rath.libxml.jaxp.SAXParserFactoryImpl");
	 * </blockquote>
	 */
	public static void setDefaultJAXPImplementation() {
		System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "rath.libxml.jaxp.DocumentBuilderFactoryImpl");
		System.setProperty("javax.xml.parsers.SAXParserFactory", "rath.libxml.jaxp.SAXParserFactoryImpl");
	}

	/**
	 * Create JAXP compatible DocumentBuilderFactory instance.
	 *
	 * @return created DocumentBuilderFactory instance
	 */
	public static DocumentBuilderFactory createDocumentBuilderFactory() {
		return new DocumentBuilderFactoryImpl();
	}

	/**
	 * Create JAXP compatible SAXParserFactory instance.
	 *
	 * @return created SAXParserFactory instance
	 */
	public static SAXParserFactory createSAXParserFactory() {
		return new SAXParserFactoryImpl();
	}

	/**
	 * Create JAXP compatible XPathFactory instance.
	 *
	 * @return created XPathFactory instance
	 */
	public static XPathFactory createXPathFactory() {
		return new XPathFactoryImpl();
	}

	/**
	 * Convenience method for Document.create(String)
	 * @return newly created document instance.
	 */
	public static Document createDocument() {
		return Document.create("1.0");
	}

	private static native void initInternalParser();

	/**
	 * Parse the content of the given file as an XML document and return a new DOM object.
	 * An IllegalArgumentException is thrown if the File is null.
	 *
	 * @param file The file containing the XML to parse.
	 * @return A new DOM document object.
	 * @throws IOException if any IO error occur.
	 */
	public static Document parseFile(File file) throws IOException {
		if( file==null )
			throw new IllegalArgumentException();
		if( !file.exists() )
			throw new FileNotFoundException();

		Document doc;
		doc = parseFileImpl(file.getAbsolutePath());
		return doc;
	}

	private static native Document parseFileImpl(String pathname);

	/**
	 * Parse the content of the given data as an XML document and return a new DOM object.
	 *
	 * @param data The string containing the XML to parse.
	 * @return A new DOM document object.
	 */
	public static Document parseString(String data) {
		if( data==null )
			throw new NullPointerException("Can't parse null data");

		Document doc;
		doc = parseStringImpl(data);
		return doc;
	}

	private static native Document parseStringImpl(String data);

	/**
	 * Parse the content in the given systemId as an XML document and return a new DOM object.
	 *
	 * @param systemId a URL containing XML document. <strong>file:sample.xml</strong> indicates
	 *                 sample.xml file in the current directory, and can also be combined with jar protocol.
	 * @return A new DOM document object.
	 * @throws IOException if any IO error occur.
	 */
	public static Document parseSystemId(String systemId) throws IOException {
		if( systemId==null )
			throw new NullPointerException("systemId cannot be null");

		Document doc;
		InputStream in = new URL(systemId).openStream();
		try {
			doc = parseSystemIdImpl(systemId, in);
		} finally {
			in.close();
		}
		return doc;
	}

	private static native Document parseSystemIdImpl(String systemId, InputStream in) throws IOException;

	/**
	 * Parse the content of the given InputStream as an XML document and return a new DOM object.
	 * An IllegalArgumentException is thrown if the InputStream is null.
	 *
	 * @param in InputStream containing the content to be parsed.
	 * @return A new DOM document object.
	 * @throws IOException If any IO error occur.
	 */
	public static Document parseInputStream(InputStream in) throws IOException {
		if( in==null )
			throw new IllegalArgumentException();
		Document doc;
		try {
			doc = parseSystemIdImpl(null, in);
		} finally {
			//in.close();
		}
		return doc;
	}

	/**
	 * Parse the content of the string as XML using the specified SAXHandler.
	 *
	 * @param data The string containing the XML to parse.
	 * @param handler The SAX handler to use.
	 * @param recovery Whether work in recovery mode or not, tries to read not well formed document.
	 */
	public static void parseSAX(String data, SAXHandler handler, boolean recovery) {
		parseSAX(data, handler, recovery, new SAXHandlerEngine());
	}

	/**
	 * Parse the content of the string as XML using the specified SAXHandler and engine.
	 *
	 * @param data The string containing the XML to parse.
	 * @param handler The SAX handler to use.
	 * @param recovery Whether work in recovery mode or not, tries to read not well formed document.
	 * @param engine The SAXHandlerEngine to use.
	 */
	public static void parseSAX(String data, SAXHandler handler, boolean recovery, SAXHandlerEngine engine) {
		if( data==null )
			throw new IllegalArgumentException("Can't parse null data");

		engine.setHandler(handler);
		parseSAXImpl(data, engine, recovery ? 1 : 0);
	}

	private static native void parseSAXImpl(String data, SAXHandlerEngine handler, int recovery);

	/**
	 * Parse the content of the file as XML document using specified SAXHandler.
	 *
	 * @param file The file containing the XML to parse.
	 * @param handler The SAX Handler to use.
	 * @param recovery Whether work in recovery mode or not, tries to read not well formed document.
	 */
	public static void parseSAX(File file, SAXHandler handler, boolean recovery) {
		parseSAX(file, handler, recovery, new SAXHandlerEngine());
	}

	/**
	 * Parse the content of the file as XML document using specified SAXHandler and engine.
	 *
	 * @param file The file containing the XML to parse.
	 * @param handler The SAX Handler to use.
	 * @param recovery Whether work in recovery mode or not, tries to read not well formed document.
	 * @param engine The SAXHandlerEngine to use.
	 */
	public static void parseSAX(File file, SAXHandler handler, boolean recovery, SAXHandlerEngine engine) {
		engine.setHandler(handler);
		parseSAXFileImpl(file.getAbsolutePath(), engine, recovery ? 1 : 0);
	}

	private static native void parseSAXFileImpl(String filepath, SAXHandlerEngine handler, int recovery);

	/**
	 * Parse the content located in specified systemId as XML document using specified SAXHandler.
	 *
	 * @param systemId The system identifier which indicates location of XML document as URI.
	 * @param handler The SAX Handler to use.
	 * @param recovery Whether work in recovery mode or not, tries to read not well formed document.
	 * @throws IOException If any IO error occur.
	 */
	public static void parseSAXSystemId(String systemId, SAXHandler handler, boolean recovery) throws IOException {
		parseSAXSystemId(systemId, handler, recovery, new SAXHandlerEngine());
	}

	/**
	 * Parse the content located in specified systemId as XML document using specified SAXHandler and engine.
	 *
	 * @param systemId The system identifier which indicates location of XML document as URI.
	 * @param handler The SAX Handler to use.
	 * @param recovery Whether work in recovery mode or not, tries to read not well formed document.
	 * @param engine The SAXHandlerEngine to use.
	 * @throws IOException If any IO error occur.
	 */
	public static void parseSAXSystemId(String systemId, SAXHandler handler, boolean recovery, SAXHandlerEngine engine) throws IOException {
		engine.setHandler(handler);
		// Resolve systemId's input stream
		InputStream in = new URL(systemId).openStream();
		try {
			parseSAXSystemIdImpl(systemId, in, engine, recovery ? 1 : 0);
		} finally {
			in.close();
		}
	}

	private static native void parseSAXSystemIdImpl(String systemId, InputStream in, SAXHandlerEngine engine, int recovery);

	/**
	 * Compile an XPath expression for later evaluation.
	 * If expression is null, a NullPointerException is thrown.
	 *
	 * @param expr The XPath expression.
	 * @return Compiled XPath expression.
	 */
	public static XPathExpression compileXPath(String expr) {
		if( expr==null )
			throw new NullPointerException("XPath expression cannot be null");

		XPathExpression ret = compileXPathImpl(expr);
		return ret;
	}

	private static native XPathExpression compileXPathImpl(String expr);
}
