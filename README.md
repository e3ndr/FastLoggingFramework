# FastLoggingFramework
A fast logging framework  
  
Example  
```java
// If you ever want to mute an application using FLF, this will override any LogLevel set in a logger. Any loggers generated without a specified level will absorb this one.
FastLoggingFramework.setDefaultLevel(LogLevel.NONE);

FastLogger log = new FastLogger(); // Automatically gets the name of the calling class, you can of course pass a string and LogLevel

log.info("This is an info message!"); // Only if LogLevel >= INFO
log.warn("This is a warning!"); // Only if LogLevel >= WARNING
log.severe("This is a severe message!"); // Only if LogLevel >= SEVERE
log.debug("This is a debug message!"); // Only if LogLevel >= DEBUG
log.exception(new Exception()); // Only if LogLevel >= SEVERE
```  
  
Maven  
```xml
    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>

    <dependencies>
        <dependency>
            <groupId>com.github.e3ndr</groupId>
            <artifactId>FastLoggingFramework</artifactId>
            <version>1.1.0</version>
        </dependency>
    </dependencies>
```
