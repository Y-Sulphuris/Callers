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

/**
 * Implementation of getCallerClass for Java 8 and older, where StackWalker isn't available,
 * so we search for the fastest way to get the caller class in the current environment.
 *
 * @author Sulphuris
 * @since 1.0 (10.06.2026 09:09)
 */
final class CallersImpl {
    private CallersImpl()
        { throw new IllegalStateException(); }

    private static final boolean useReflection = findIfReflectionPresent();
    private static final boolean stePresent = useReflection || findIfStePresent();

    private static boolean findIfStePresent() {
        try {
            Class.forName("java.lang.StackTraceElement");
            return true;
        } catch (ClassNotFoundException e) {
            System.setProperty("com.ydo4ki.callers.impl", "ThrowableLegacy");
            return false;
        }
    }

    private static boolean findIfReflectionPresent() {
        try {
            java.lang.reflect.Method m = Class.forName("sun.reflect.Reflection").getMethod("getCallerClass", new Class[]{int.class});
            //noinspection JavaReflectionInvocation
            Object ret = m.invoke(null, new Integer[]{new Integer(2)});
            Class callerImplClass = Class.forName("com.ydo4ki.callers.CallersImpl"); // to avoid creation of synthetic field by java 4
            if (ret != callerImplClass)
                throw new IllegalStateException();

            ret = getCallerSun(2);
            if (ret != callerImplClass)
                throw new IllegalStateException();

            System.setProperty("com.ydo4ki.callers.impl", "sun.reflect");
            return true;
        } catch (Exception e) {
            System.setProperty("com.ydo4ki.callers.impl", "Throwable");
            return false;
        }
    }

    static Class getCallerClass(final int index) {
        if (useReflection) {
            return getCallerSun(index + 1);
        } else {
            if (!stePresent) return getCallerLegacy(index + 2);
            return getCallerEx(index + 1);
        }
    }

    /** @noinspection deprecation */
    private static Class getCallerSun(int index) {
        return sun.reflect.Reflection.getCallerClass(index + 1); // some illegal stuff here
    }

    private static Class getCallerEx(int index) {
        try {
            String name;
            java.lang.StackTraceElement[] fullStackTrace = new Throwable().getStackTrace();

            do {
                name = fullStackTrace[++index].getClassName(); // some very slow but safe stuff
            } while (name.startsWith("jdk.") || name.startsWith("sun.reflect.") || name.startsWith("java."));

            return Class.forName(name);
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        } catch (ClassNotFoundException e) {
            throw new AssertionError(e);
        }
    }

    private static Class getCallerLegacy(int index) {
        return CallersLegacyImpl.getCallerClass(index);
    }
}
