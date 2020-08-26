package xyz.e3ndr.fastloggingframework;

import java.io.IOException;

import xyz.e3ndr.fastloggingframework.loggerimpl.FileLogHandler;
import xyz.e3ndr.fastloggingframework.logging.FastLogger;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

public class Test {

    public static void main(String[] args) throws IOException {
        FastLoggingFramework.setDefaultLevel(LogLevel.DEBUG);
        new FileLogHandler();

        for (int i = 0; i != 20; i++) {
            if ((i % 10) == 0) {
                FastLogger.logStatic(LogLevel.DEBUG, i);
            } else if ((i % 5) == 0) {
                FastLogger.logStatic(LogLevel.WARNING, i);
            } else {
                FastLogger.logStatic(LogLevel.INFO, i);
            }
        }

        FastLogger.logException(null);
        FastLogger.logException(new Throwable());
        FastLoggingFramework.close();
    }

}
