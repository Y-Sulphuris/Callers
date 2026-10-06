# Callers
![Java](https://img.shields.io/badge/Java-1.4-ED8B00?logo=openjdk&logoColor=white)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)


Small library that gives access to getCallerClass() for Java 4 (faster than instantiation of new Throwable)<br>
API contains two methods:<br>
```getCallerClass()``` - returns caller class for function where getCallerClass() called from<br>
```getCallerClass(int index)``` - returns caller class for function that is **index** stackframes higher than the current function (for 1 it returns this class, 2 — same as getCallerClass(), 3 — caller of caller, etc)<br>

Usage example:
```java
package com.mycompany;

class MyVerySecretClass {
	private static final MyVerySecretClass instance = new MyVerySecretClass();
	private MyVerySecretClass() {}

	// ... some great functionality ...

	public static MyVerySecretClass getInstance() {
		if (!Callers.getCallerClass().getName().startsWith("com.mycompany"))
			throw new SecurityException("No MyVerySecretClass instances for your package!");
		return instance;
	}
}
```
