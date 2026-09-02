# java-utils

___
[![CI](https://github.com/amanzat/java-utils/actions/workflows/ci.yml/badge.svg)](https://github.com/amanzat/java-utils/actions/workflows/ci.yml)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](https://github.com/amanzat/java-utils/blob/dev/LICENSE)
[![Maven Central Version](https://img.shields.io/maven-central/v/io.github.amanzat/java-utils)](https://search.maven.org/artifact/io.github.amanzat/java-utils)
[![javadoc](https://javadoc.io/badge2/io.github.amanzat/java-utils/javadoc.svg)](https://javadoc.io/doc/io.github.amanzat/java-utils)
___
A simple, lightweight and well-tested utility library.
___
This library contains a collection of frequently used utilities.

Its primary use is in other libraries or lightweight applications that prefer not to add a dependency on other more
comprehensive and larger utility libraries.
___
## Requirements

**Java 21 or later.** Versions up to `0.0.2` were built for Java 17.

## Installation

```xml
<dependency>
    <groupId>io.github.amanzat</groupId>
    <artifactId>java-utils</artifactId>
    <version>0.0.4</version>
</dependency>
```

`slf4j-api` is declared as `provided`, so it is **not** pulled in transitively and has to be added
by the project using this library:

```xml
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>2.0.18</version>
</dependency>
```

`DateTimeUtils`, `FileUtils`, `NumberUtils` and `ThreadUtils` log, and `Slf4jUtils` takes a
`Logger` as a parameter, so those five classes throw `NoClassDefFoundError` without it. The other
six classes do not need it. A logging binding, such as `slf4j-simple` or `logback-classic`, is
only needed to actually see the log output.

## Usage

```java
StringUtils.truncateWithMarker("a very long label", 10);  // "a very l.."
StringUtils.concat('/', "usr", "local", "bin");           // "usr/local/bin"
DateTimeUtils.formatDurationHMS(3725000);                 // "01:02:05.000"
CollectionUtils.getFirst(List.of());                      // Optional.empty
CollectionUtils.chunkify(names.iterator(), 2);            // [a, b] [c, d] [e]
```

The full API is documented in the [javadoc](https://javadoc.io/doc/io.github.amanzat/java-utils).

## Java modules

The jar declares `Automatic-Module-Name: io.github.amanzat.util`, so a modular project uses:

```java
requires io.github.amanzat.util;
```

## Changes

See the [changelog](CHANGELOG.md) for what changed in each release, including what to expect when
upgrading from an earlier version.
