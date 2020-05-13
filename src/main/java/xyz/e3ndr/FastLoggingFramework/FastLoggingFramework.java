package xyz.e3ndr.FastLoggingFramework;

import java.io.PrintStream;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.SneakyThrows;
import xyz.e3ndr.FastLoggingFramework.LoggerImpl.JansiLogHandler;
import xyz.e3ndr.FastLoggingFramework.Logging.FastLogger;
import xyz.e3ndr.FastLoggingFramework.Logging.LogLevel;

public class FastLoggingFramework {
    private static final @Getter String version = "1.0.0";

    private static @Getter @NonNull AbstractLogHandler logHandler = new JansiLogHandler();
    private static @Getter @Setter @NonNull LogLevel defautLevel = LogLevel.SEVERE;
    private static @Getter PrintStream defautSystemOut = System.out;

    private static FastLogger faLogger = new FastLogger("&d&oFLF");
    private static boolean alertedAsync = false;

    static {
        faLogger.info("&aFastLoggingFramework v" + version);
        faLogger.info("&aUsing default JansiLogHandler\n");

    }

    public static void setLogHandler(AbstractLogHandler newHandler) {
        if (!newHandler.getClass().isAssignableFrom(logHandler.getClass())) {
            logHandler.dispose();
            logHandler = newHandler;
        }
    }

    public static void showFaDebug(boolean debug) {
        faLogger.setCurrentLevel(debug ? LogLevel.DEBUG : LogLevel.SEVERE);
    }

    public static void close() {
        logHandler.dispose();
        AbstractLogHandler.close();
    }

    @SneakyThrows
    public static Class<?> getCallingClass() {
        return Class.forName(Thread.currentThread().getStackTrace()[3].getClassName());
    }

    public static void setAsync(boolean async) {
        if (async && !alertedAsync) {
            faLogger.warn("Asynchronous logging is still in beta! Use at own risk.");
            alertedAsync = true;
        }

        faLogger.debug(getCallingClass().getCanonicalName() + " switched logging mode.");

        AbstractLogHandler.setAsync(async);
    }

}
