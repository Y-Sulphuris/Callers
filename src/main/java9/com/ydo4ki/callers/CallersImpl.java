/*
 * MIT License
 *
 * Copyright (c) 2024 Sulphuris
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.ydo4ki.callers;

import java.util.Iterator;

/**
 * Implementation of getCallerClass for Java 9 and newer, where StackWalker is available.
 *
 * @author Sulphuris
 * @since 1.0 (10.06.2026 09:09)
 */
final class CallersImpl {
    private CallersImpl()
        { throw new IllegalStateException(); }

    static {
        System.setProperty("com.ydo4ki.callers.impl", "StackWalker");
    }

    private static final StackWalker WALKER =
            StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    static Class<?> getCallerClass(final int index) {
        if (index <= 0)
            return null;

        return WALKER.walk(s -> {
			// virtually same as doing
            // s
            //     .skip(index)
            //     .findFirst()
            //     .map(StackWalker.StackFrame::getDeclaringClass)
            //     .orElse(null);
			// but streams slow things down a bit
			Iterator<StackWalker.StackFrame> sf = s.iterator();
			for (int i = 0; i < index; i++) {
                if (!sf.hasNext())
                    return null;
                sf.next();
            }

            if (!sf.hasNext())
                return null;
			return sf.next().getDeclaringClass();
		});
    }
}
