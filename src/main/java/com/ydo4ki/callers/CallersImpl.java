package com.ydo4ki.callers;

import java.lang.reflect.Method;

/**
 * @author Sulphuris
 * @since 10.06.2026 09:09
 */
final class CallersImpl {
    private static final boolean useReflection = findIfReflectionPresent();
    private static final boolean stePresent = useReflection || findIfStePresent();

    private static boolean findIfStePresent() {
        try {
            Class.forName("java.lang.StackTraceElement");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private static boolean findIfReflectionPresent() {
        try {
            Method m = Class.forName("sun.reflect.Reflection").getMethod("getCallerClass", new Class[]{int.class});
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
        return sun.reflect.Reflection.getCallerClass(index); // some illegal stuff here
    }

    private static Class getCallerEx(int index) {
        try {
            String name;
            StackTraceElement[] fullStackTrace = new Throwable().getStackTrace(); // why was i doing it in a loop am i stupid ._.

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
