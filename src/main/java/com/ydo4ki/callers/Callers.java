package com.ydo4ki.callers;

/**
 * Utility class that provides access to getCallerClass methods from sun.reflect.Reflection
 * @author Sulphuris
 * @since 1.0
 */
public final class Callers {
	private Callers() throws IllegalAccessException
		{ throw new IllegalAccessException(); }

	/**
	 * Returns the class of the caller of the method calling this method,
	 * ignoring frames associated with java reflection.
	 *
	 * @since 1.0
	 * @return the class of the caller of the method calling this method, ignoring frames associated with java reflection.
	 * */
	public static Class getCallerClass() {
		return getCallerClass(3);
	}

	/**
	 * Returns the class [of the caller of the caller of the caller... * <b>index</b> times] of the method calling this method,
	 * ignoring frames associated with java reflection.
	 *
	 * @since 1.0
	 * @return the class [of the caller of the caller of the caller... * <b>index</b> times] of the method calling this method,
	 * ignoring frames associated with java reflection.
	 * */
	public static Class getCallerClass(final int index) {
		return CallersImpl.getCallerClass(index + 1);
	}
}

