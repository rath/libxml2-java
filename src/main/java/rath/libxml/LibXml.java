package rath.libxml;

import rath.libxml.impl.SAXHandlerEngine;
import rath.libxml.util.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 
 * User: rath
 * Date: 02/11/2013
 * Time: 23:50
 * 
 */
public class LibXml {
	static {
		loadNativeLibrary();
	}

	private static synchronized void loadNativeLibrary() {
		String fullname = System.mapLibraryName("hello");
		String suffix = fullname.substring(fullname.lastIndexOf('.'));
		if (suffix.equalsIgnoreCase(".jnilib")) suffix = ".dylib";
		String libname = "libxml2j" + suffix;

		File targetLibrary = null;

		File localXcodeLibrary = new File("/Users/rath/Library/Developer/Xcode/DerivedData/libxml2-java-fkrlovtvxnmqhfhhoxmlslcqmtjx/Build/Products/Debug/" + libname);
		if( localXcodeLibrary.exists() && localXcodeLibrary.lastModified()-(1000L*60L*30L) < System.currentTimeMillis()) {
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


	private static native void initInternalParser();

	public static Document parseFile(File file) throws IOException {
		if( !file.exists() )
			throw new FileNotFoundException();

		Document doc;
		doc = parseFileImpl(file.getAbsolutePath());
		return doc;
	}

	private static native Document parseFileImpl(String pathname);

	public static Document parseString(String data) {
		if( data==null )
			throw new NullPointerException("Can't parse null data");

		Document doc;
		doc = parseStringImpl(data);
		return doc;
	}

	private static native Document parseStringImpl(String data);

	public static void parseSAX(String xml, SAXHandler handler, int recovery) {
		parseSAX(xml, handler, recovery, null);
	}

	public static void parseSAX(String xml, SAXHandler handler, int recovery, SAXHandlerEngine engine) {
		if( xml==null )
			throw new NullPointerException("Can't parse null data");

		if( engine==null )
			engine = new SAXHandlerEngine();
		engine.setHandler(handler);
		parseSAXImpl(xml, engine, recovery);
	}

	private static native void parseSAXImpl(String data, SAXHandlerEngine handler, int recovery);
}
