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
 * Utility class that provides access to getCallerClass methods from sun.reflect.Reflection
 * @author Sulphuris
 * @since 1.0
 */
public final class Callers {
	private Callers() throws IllegalAccessException
		{ throw new IllegalAccessException(); }

	/**
	 * Returns the class of the caller of the method calling this method,
	 * ignoring frames associated with java reflection.
	 *
	 * @since 1.0
	 * @return the class of the caller of the method calling this method, ignoring frames associated with java reflection.
	 * */
	public static Class getCallerClass() {
		return getCallerClass(3);
	}

	/**
	 * Returns the class [of the caller of the caller of the caller... * <b>index</b> times] of the method calling this method,
	 * ignoring frames associated with java reflection.
	 *
	 * @since 1.0
	 * @return the class [of the caller of the caller of the caller... * <b>index</b> times] of the method calling this method,
	 * ignoring frames associated with java reflection.
	 * */
	public static Class getCallerClass(final int index) {
		return CallersImpl.getCallerClass(index + 1);
	}
}

