package xyz.e3ndr.fastloggingframework;

import java.io.PrintStream;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.SneakyThrows;
import xyz.e3ndr.fastloggingframework.loggerimpl.BukkitLogHandler;
import xyz.e3ndr.fastloggingframework.loggerimpl.BungeeLogHandler;
import xyz.e3ndr.fastloggingframework.loggerimpl.JansiLogHandler;
import xyz.e3ndr.fastloggingframework.loggerimpl.NukkitLogHandler;
import xyz.e3ndr.fastloggingframework.logging.FastLogger;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

public class FastLoggingFramework {
    private static final @Getter String version = "1.1.0";

    private static @Getter @NonNull LogHandler logHandler = new JansiLogHandler();
    private static @Getter @Setter @NonNull LogLevel defautLevel = LogLevel.SEVERE;
    private static @Getter PrintStream defautSystemOut = System.out;

    private static FastLogger faLogger = new FastLogger("&d&oFLF");

    static {
        if (classExists("org.bukkit.Bukkit")) {
            new BukkitLogHandler();
        } else if (classExists("cn.nukkit.Server")) {
            new NukkitLogHandler();
        } else if (classExists("net.md_5.bungee.api.ProxyServer")) {
            new BungeeLogHandler();
        }

        faLogger.info("&aFastLoggingFramework v" + version);
    }

    public static void setLogHandler(LogHandler newHandler) {
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
        LogHandler.close();
    }

    @SneakyThrows
    public static Class<?> getCallingClass() {
        return Class.forName(Thread.currentThread().getStackTrace()[3].getClassName());
    }

    public static boolean classExists(String name) {
        try {
            Class.forName(name);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
