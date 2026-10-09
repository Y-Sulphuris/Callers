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

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Vector;

// a complex system of high durability
// do not touch
// do not look
// this should never be loaded in java 4+
final class CallersLegacyImpl {
    private CallersLegacyImpl()
        { throw new IllegalStateException(); }

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
        Vector str = getTruncatedStack(t, index);
        return (String) str.get(str.size() - 1);
    }

    private static Vector getTruncatedStack(Throwable t, int maxLines) {
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
    Vector lines = new Vector();
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