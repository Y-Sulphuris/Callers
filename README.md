# Callers
![Java](https://img.shields.io/badge/Java-1.1%2B-ED8B00?logo=openjdk&logoColor=white)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

Backport of `StackWalker.getCallerClass()`/`sun.reflect.Reflection.getCallerClass()` to Java 1.1 - 1.8<br>
The library searches for the fastest implementation available on current JVM, and if nothing is available, falls back to retrieving stack information from Throwable.

API consists of two methods:<br>
* ```getCallerClass()``` - returns caller class for a method where getCallerClass() was called from;<br>
* ```getCallerClass(int index)``` - returns caller class for a method that is **index** stackframes above the current stackframe (for 1 it returns this class, 2 — same as getCallerClass(), 3 — caller of caller, etc.).<br>

Usage example:
```java
package com.mycompany;

import com.ydo4ki.callers.Callers;

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

## Installation

### Maven
```xml
<dependency>
    <groupId>com.ydo4ki</groupId>
    <artifactId>Callers</artifactId>
    <version>1.1.1</version>
</dependency>
```
### Gradle

```groovy
implementation("com.ydo4ki:Callers:1.1.1")
```

### No build system
```
Go to releases tab and download latest jar
```

## How to build
Please note that you are required to have JDK 1.4 on your computer to compile this.
1. Clone this repository
```bash
git clone https://github.com/Y-Sulphuris/Callers.git
cd Callers
```
2. Create file "local.properties" in the source root and add the path to your JDK 1.4 to it:
```properties
jdk4.home=path/to/j2sdk1.4.0
```
3. Run maven build (maven should run on Java 9 or higher):
```bash
mvn clean package
```

As for where to get JDK 1.4, I'll tactfully refrain from commenting on this.