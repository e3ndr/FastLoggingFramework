package xyz.e3ndr.fastloggingframework;

import xyz.e3ndr.fastloggingframework.logging.FastLogger;

public class Test {

    public static void main(String[] args) {
        FastLogger log = new FastLogger();

        for (int i = 0; i != 100; i++) {
            log.info(i);
        }

        FastLoggingFramework.close();
    }

}
