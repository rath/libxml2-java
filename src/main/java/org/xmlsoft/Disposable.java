package org.xmlsoft;

/**
 * A Disposable is a source of destination of data that can be disposed.
 * The dispose method is invoked to release underlying native resources that
 * the object is wired.
 *
 * @author Jang-Ho Hwang, rath@xrath.com
 */
public interface Disposable {
	/**
	 * Dispose any native system resources associated with it.
	 */
	public void dispose();
}
