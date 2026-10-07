# Callers
![Java](https://img.shields.io/badge/Java-1.1%2B-ED8B00?logo=openjdk&logoColor=white)
[![Maven Central](https://img.shields.io/maven-central/v/com.ydo4ki/Callers?label=Maven%20Central)](https://central.sonatype.com/artifact/com.ydo4ki/Callers)
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

For projects using Java 5 and newer it is also recommended that you use `@CallerSensitive` annotation from `com.ydo4ki.callers` for methods similar to the one in this example.

This annotation might be useful to prevent obfuscation/optimization tools from altering your stack flow in thehttps://central.sonatype.com/artifact/com.ydo4ki/Callers parts of code that depend on it.

## Installation

### Maven
```xml
<dependency>
    <groupId>com.ydo4ki</groupId>
    <artifactId>Callers</artifactId>
    <version>1.2</version>
</dependency>
```
### Gradle

```groovy
implementation("com.ydo4ki:Callers:1.2")
```

### No build system
```
Go to releases tab and download latest jar
```

## How to build
Please note that you are required to have JDKs 1.4, 1.8 and 9+ on your computer to compile this.
1. Clone this repository
```bash
git clone https://github.com/Y-Sulphuris/Callers.git
cd Callers
```
2. Create file "local.properties" in the repository root and add paths to your JDK 1.4 and 1.8 to it:
```properties
jdk4.home=path/to/j2sdk1.4.0
jdk8.home=path/to/jdk1.8.0
```
3. Run maven build (maven should run on Java 9 or higher):
```bash
mvn clean package
```

As for where to get JDK 1.4, I'll tactfully refrain from commenting on this.