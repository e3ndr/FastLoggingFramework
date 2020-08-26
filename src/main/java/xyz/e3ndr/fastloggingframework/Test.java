package xyz.e3ndr.fastloggingframework;

import xyz.e3ndr.fastloggingframework.logging.FastLogger;

public class Test {

    public static void main(String[] args) {
        FastLogger.logException(null);
        FastLogger.logException(new Throwable());
        FastLoggingFramework.close();
    }

}
