package org.xmlsoft.util;

import org.xml.sax.InputSource;

import java.io.*;
import java.net.URL;

/**
 * 
 * User: rath
 * Date: 04/11/2013
 * Time: 11:50
 * 
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

	public static InputSource createInputSource(String xml) throws IOException {
		InputSource ret = new InputSource();
		ret.setByteStream(new ByteArrayInputStream(xml.getBytes("UTF-8")));
		ret.setPublicId(null);
		ret.setSystemId(null);
		return ret;
	}

	public static String loadInputSource(InputSource in) throws IOException {
		InputStream byteStream = in.getByteStream();
		if(byteStream!=null )
			return loadStream(byteStream, in.getEncoding());
		Reader charStream = in.getCharacterStream();
		if( charStream!=null )
			return loadReader(charStream);

		String systemId = in.getSystemId();
		if( systemId!=null ) {
			URL url = new URL(systemId);
			byteStream = url.openConnection().getInputStream();
			return loadStream(byteStream);
		}

		String msg = "publicId: " + in.getPublicId() + ", systemId: " + in.getSystemId() + ", encoding: " + in.getEncoding();
		throw new UnsupportedOperationException(msg);
	}

	public static String loadReader(Reader in) throws IOException {
		StringWriter sw = new StringWriter();
		char[] buf = new char[1024];
		while(true) {
			int readlen = in.read(buf);
			if( readlen==-1 )
				break;
			sw.write(buf, 0, readlen);
		}
		return sw.toString();
	}

	public static String loadStream(InputStream in) throws IOException {
		return loadStream(in, "UTF-8");
	}

	public static String loadStream(InputStream in, String encoding) throws IOException {
		if( encoding==null )
			encoding = "UTF-8";

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		while(true) {
			int readlen = in.read(buf);
			if( readlen==-1 )
				break;
			bos.write(buf, 0, readlen);
		}
		return bos.toString(encoding);
	}

	public static String loadFile(File f) throws IOException {
		FileInputStream fis = new FileInputStream(f);
		String str = null;
		try {
			str = loadStream(fis);
		} finally {
			fis.close();
		}
		return str;
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

	public static void writeFile(File f, String str) throws IOException {
		FileOutputStream fos = new FileOutputStream(f);
		try {
			fos.write(str.getBytes("UTF-8"));
			fos.flush();
		} finally {
			fos.close();
		}
	}
}
