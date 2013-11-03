package rath.libxml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: rath
 * Date: 02/11/2013
 * Time: 23:50
 * To change this template use File | Settings | File Templates.
 */
public class LibXml {
	static {
		initNativeLibrary();
	}

	private static void initNativeLibrary() {
		// TODO: Bundle native library into jar archive.
		System.load(new File("./libxml2java.jnilib").getAbsolutePath());
	}

	public static Document parseFile(File file) throws IOException {
		if( !file.exists() )
			throw new FileNotFoundException();

		Document doc = parseFileImpl(file.getAbsolutePath());
		return doc;
	}

	private static native Document parseFileImpl(String pathname);

	public static Document parseString(String data) {
		if( data==null )
			throw new NullPointerException("Can't parse null data");
		Document doc = parseStringImpl(data);
		return doc;
	}

	private static native Document parseStringImpl(String data);
}
