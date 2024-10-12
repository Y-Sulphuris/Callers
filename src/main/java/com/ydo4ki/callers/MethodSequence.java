package com.ydo4ki.callers;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

final class MethodSequence {
	private final MethodHandles.Lookup lookup;

	private final String name;
	private final MethodType methodType;

	private MethodHandle found = null;
	private Throwable lastThrowable = null;

	MethodSequence(MethodHandles.Lookup lookup, String name, MethodType methodType) {
		this.lookup = lookup;
		this.name = name;
		this.methodType = methodType;
	}

	public MethodSequence tryFindNext(String owner) {
		return tryFindNext(owner, name);
	}
	// signs
	public MethodSequence tryFindNext(String owner, String name) {
		if (found == null) try {
			Class<?> refc = Class.forName(owner);
			found = lookup.findStatic(refc, name, methodType);
		} catch (Exception e) {
			lastThrowable = e;
		}
		return this;
	}


	public MethodHandle fallback(String owner) {
		return fallback(owner, name);
	}
	public MethodHandle fallback(String owner, String name) {
		tryFindNext(owner, name);
		if (found == null) {
			if (lastThrowable == null) throw new NoSuchMethodError(name);
			if (lastThrowable instanceof RuntimeException) throw (RuntimeException)lastThrowable;
			if (lastThrowable instanceof Error) throw (Error)lastThrowable;
			throw new RuntimeException(lastThrowable);
		}
		return found;
	}
}
