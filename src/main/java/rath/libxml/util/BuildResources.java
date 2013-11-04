package rath.libxml.util;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: rath
 * Date: 04/11/2013
 * Time: 12:36
 * To change this template use File | Settings | File Templates.
 */
public class BuildResources {
	public static void main(String[] args) throws IOException, InterruptedException {
		Runtime.getRuntime().exec("make all").waitFor();

		String s = Utils.loadFile(new File("Makefile"));
		Matcher matcher = Pattern.compile("TARGET=(.*)").matcher(s);
		matcher.find();
		String sourceFile = matcher.group(1);
		matcher = Pattern.compile("SRC=(.*)").matcher(s);
		matcher.find();
		String sourceDir = matcher.group(1);

		File resourceDir = new File("resources");
		resourceDir.mkdirs();
		File resourceFile = new File(resourceDir, Utils.getPlatformDependentBundleName());
		Utils.copy(new File(sourceDir, sourceFile), resourceFile);

		Runtime.getRuntime().exec("strip " + resourceFile.getAbsolutePath());
	}
}
