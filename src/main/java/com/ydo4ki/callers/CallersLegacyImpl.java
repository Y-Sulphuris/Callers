package com.ydo4ki.callers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

// a complex system of high durability
// do not touch
// do not look
final class CallersLegacyImpl {

    static {
        // this should never be loaded in java 4+
        System.setProperty("com.ydo4ki.callers.impl", "ThrowableLegacy");
    }

    static Class getCallerClass(int depth) {
        String s = extractClassName(getStackTraceElement(new Throwable(), depth));
        Class cls;
        try {
            cls = Class.forName(s);
        } catch (ClassNotFoundException ex) {
            throw new AssertionError(ex);
        }
        return cls;
    }

    private static String extractClassName(String stackTraceElement) {
        String s = stackTraceElement.substring(4);
        int i = s.indexOf("(");
        s = s.substring(0, i);
        i = s.lastIndexOf(".");
        return s.substring(0, i);
    }

    private static String getStackTraceElement(Throwable t, int index) {
        List str = getTruncatedStack(t, index);
        return (String) str.get(str.size() - 1);
    }

    private static List getTruncatedStack(Throwable t, int maxLines) {
        EarlyStopWriter esw = new EarlyStopWriter(maxLines + 1);
        try {
            t.printStackTrace(new PrintWriter(esw, true));
        } catch (StopException e) {
        }
        esw.lines.remove(0);
        return esw.lines;
    }
}

// this is to prevent the entire stack being printed
class EarlyStopWriter extends Writer {
    private int linesLeft;
    List lines = new ArrayList();
    StringBuffer line = new StringBuffer();

    public EarlyStopWriter(int maxLines) {
        this.linesLeft = maxLines;
    }

    public void write(char[] cbuf, int off, int len) throws IOException {
        for (int i = 0; i < len; i++) {
            char c = cbuf[off + i];
            if (c == '\n') {
                String str = line.toString();
                if (str.startsWith("\tat jdk.") || str.startsWith("\tat sun.reflect.") || str.startsWith("\tat java.")) {
                    // skip internal lines
                } else {
                    lines.add(str);
                    line.setLength(0);
                    if (--linesLeft <= 0) {
                        throw new StopException();
                    }
                }
            } else {
                line.append(c);
            }
        }
    }

    public void flush() {
    }

    public void close() {
    }
}

class StopException extends RuntimeException {
    public synchronized Throwable fillInStackTrace() {
        return this; // would be funny if I forgot to add this
    }
}