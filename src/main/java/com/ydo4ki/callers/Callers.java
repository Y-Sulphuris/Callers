package com.ydo4ki.callers;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class Callers {
	private static final MethodHandle getCallerClass =
			new MethodSequence(MethodHandles.lookup(), "getCallerClass", MethodType.methodType(Class.class, int.class))
					.tryFindNext("sun.reflect.Reflection")
					.tryFindNext("com.ydo4ki.callers.Callers9")
					.fallback("com.ydo4ki.callers.Callers","getCallerEx");


	public static Class<?> getCallerClass() {
		return getCallerClass(3);
	}

	public static Class<?> getCallerClass(final int index) {
		try {
			return (Class<?>) getCallerClass.invokeExact(index+1);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	private static Class<?> getCallerEx(int index) {
		try {
			String name;
			do {
				name = getFullStackTrace()[++index].getClassName();
			} while (name.startsWith("jdk.") || name.startsWith("sun.reflect.") || name.startsWith("java."));
			return Class.forName(name);
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		} catch (ClassNotFoundException e) {
			throw new AssertionError(e);
		}
	}

	public static StackTraceElement[] getFullStackTrace() {
		return new Throwable().getStackTrace();
	}
}

