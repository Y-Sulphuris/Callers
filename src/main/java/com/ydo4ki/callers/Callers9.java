package com.ydo4ki.callers;

/**
 * @author Sulphuris
 * @since 12.10.2024 18:44
 */
class Callers9 {
	static Class<?> getCallerClass(final int index) {
		return StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
				.walk(s -> s.map(StackWalker.StackFrame::getDeclaringClass).skip(index).findFirst()).orElse(null);
	}
}
