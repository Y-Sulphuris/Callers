package com.ydo4ki.callers;

public final class Callers {
	private Callers() throws IllegalAccessException
		{ throw new IllegalAccessException(); }

	public static Class getCallerClass() {
		return getCallerClass(3);
	}

	public static Class getCallerClass(final int index) {
		return CallersImpl.getCallerClass(index + 1);
	}
}

