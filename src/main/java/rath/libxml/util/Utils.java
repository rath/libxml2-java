package rath.libxml.util;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: rath
 * Date: 04/11/2013
 * Time: 11:50
 * To change this template use File | Settings | File Templates.
 */
public class Utils {
	public static void copy(File in, File out) throws IOException {
		FileInputStream fis = new FileInputStream(in);
		copyAndClose(fis, out);
		fis.close();
	}

	public static void copyAndClose(InputStream in, File outFile) throws IOException {
		byte[] buf = new byte[8192];
		FileOutputStream out = new FileOutputStream(outFile);
		while(true) {
			int readlen = in.read(buf);
			if( readlen==-1 )
				break;
			out.write(buf, 0, readlen);
		}
		out.flush();
		out.close();
		in.close();
	}

	public static String loadFile(File f) throws IOException {
		FileInputStream fis = new FileInputStream(f);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		while(true) {
			int readlen = fis.read(buf);
			if( readlen==-1 )
				break;
			bos.write(buf, 0, readlen);
		}
		fis.close();
		return bos.toString("UTF-8");
	}

	public static String getPlatformDependentBundleName() {
		// libxml2j-linux-64.so
		// libxml2j-darwin-64.dylib
		String fullname = System.mapLibraryName("hello");
		String suffix = fullname.substring(fullname.lastIndexOf('.'));

		String arch = System.getProperty("sun.arch.data.model");
		String osname = System.getProperty("os.name").toLowerCase();
		if( osname.contains("mac") ) {
			osname = "darwin";
		} else
		if( osname.contains("linux") ) {
			osname = "linux";
		} else
			throw new UnsupportedOperationException("Not supported OS: " + System.getProperty("os.name"));

		return "libxml2j-" + osname + "-" + arch + suffix;
	}

}