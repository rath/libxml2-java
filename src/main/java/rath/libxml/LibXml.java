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
		System.load(new File("./libxml2java.jnilib").getAbsolutePath());
	}

	private static native long parseFileImpl(String pathname);

	public static Document parseFile(File file) throws IOException {
		if( !file.exists() )
			throw new FileNotFoundException();

		long pDocument = parseFileImpl(file.getAbsolutePath());
		Document doc = new Document(pDocument);
		return doc;
	}
}
