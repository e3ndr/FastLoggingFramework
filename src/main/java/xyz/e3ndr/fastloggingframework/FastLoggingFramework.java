package xyz.e3ndr.fastloggingframework;

import java.io.IOException;
import java.io.PrintStream;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import xyz.e3ndr.fastloggingframework.loggerimpl.QuietLogHandler;
import xyz.e3ndr.fastloggingframework.loggerimpl.StaticLogHandler;
import xyz.e3ndr.fastloggingframework.logging.FastLogger;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

public class FastLoggingFramework {
    private static @Getter @NonNull FastLogHandler logHandler;

    private static @Getter @Setter @NonNull LogLevel defaultLevel = LogLevel.INFO;
    private static @Getter boolean colorEnabled = false; // This needs to be FALSE by default for Windows reasons.

    static {
        try {
            // This defaults to INFO^
            setDefaultLevel(
                LogLevel.valueOf(
                    System.getProperty("fastloggingframework.defaultlevel")
                )
            );
        } catch (IllegalArgumentException | NullPointerException ignored) {}

        if ("true".equalsIgnoreCase(System.getProperty("fastloggingframework.bequiet"))) {
            logHandler = new QuietLogHandler(); // Do not setup static log handler. Do not wrap system.
        } else {
            logHandler = new StaticLogHandler();

            if ("true".equalsIgnoreCase(System.getProperty("fastloggingframework.wrapsystem", "false"))) {
                System.setOut(new PrintStream(new OutputStreamToFLF(LogLevel.INFO)));
                System.setErr(new PrintStream(new OutputStreamToFLF(LogLevel.SEVERE)));
            }
        }
    }

    public static void setColorEnabled(boolean enabled) {
        colorEnabled = enabled;
        if (enabled) {
            try {
                WindowsUtil.tryEnableColoredConsole();
            } catch (IOException | InterruptedException e) {
                FastLogger.logStatic(LogLevel.DEBUG, "An exception occurred whilst enabling colored output on Windows:\n%s", e);
            }
        }
    }

    public static void setLogHandler(@NonNull FastLogHandler newHandler) {
        logHandler = newHandler;
    }

}
