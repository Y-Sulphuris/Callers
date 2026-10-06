package com.ydo4ki.callers;

import java.util.Iterator;

/**
 * @author Sulphuris
 * @since 10.06.2026 09:09
 */
final class CallersImpl {
    static {
        System.setProperty("com.ydo4ki.callers.impl", "StackWalker");
    }

    private static final StackWalker WALKER =
            StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    static Class<?> getCallerClass(final int index) {
        return WALKER.walk(s -> {
			// virtually same as doing
            // s
            //     .skip(index)
            //     .findFirst()
            //     .map(StackWalker.StackFrame::getDeclaringClass)
            //     .orElse(null);
			// but streams slow things down a bit
			Iterator<StackWalker.StackFrame> sf = s.iterator();
			for (int i = 0; i < index; i++)
				sf.next();

			return sf.next().getDeclaringClass();
		});
    }
}
