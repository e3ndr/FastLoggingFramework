# FastLoggingFramework
A fast logging framework  
  

In order, the loglevels are:
1) FATAL (Always logged)
2) SEVERE
3) WARNING
4) INFO
5) DEBUG
6) TRACE
  
So a log level of WARNING, will show the following:
 - FATAL (Always logged)
 - SEVERE
 - WARNING
  
  
## Example  
```java
// If you ever want to mute an application using FLF you can set the default level. 
// Any loggers generated without a specified level will absorb this one.
FastLoggingFramework.setDefaultLevel(LogLevel.NONE);

// Automatically gets the name of the calling class, you can of course pass a string and LogLevel
FastLogger log = new FastLogger();

// Enable logging for all levels.
log.setLevel(LogLevel.ALL);

log.fatal("Uh oh!");
log.info("This is an info message!");
log.warn("This is a warning!"); 
log.severe("This is a severe message!");
log.debug("This is a debug message!");
log.trace("This is a trace message!");
log.exception(new Exception());
```  
  
## Maven  
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
            <version>VERSION</version>
            <scope>compile</scope>
        </dependency>
    </dependencies>
```
