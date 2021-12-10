package xyz.e3ndr.fastloggingframework;

import java.io.PrintStream;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import xyz.e3ndr.fastloggingframework.loggerimpl.BukkitLogHandler;
import xyz.e3ndr.fastloggingframework.loggerimpl.BungeeLogHandler;
import xyz.e3ndr.fastloggingframework.loggerimpl.JansiLogHandler;
import xyz.e3ndr.fastloggingframework.loggerimpl.NukkitLogHandler;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;
import xyz.e3ndr.fastloggingframework.logging.LoggingUtil;

public class FastLoggingFramework {
    private static final @Getter PrintStream defautSystemOut = System.out;
    private static final @Getter String version = "1.6.0";

    private static @Getter @Setter @NonNull LogLevel defaultLevel = LogLevel.SEVERE;
    private static @Getter @NonNull LogHandler logHandler = new AnsiLogHandler();

    static {
        if (LoggingUtil.classExists("org.bukkit.Bukkit")) {
            new BukkitLogHandler();
        } else if (LoggingUtil.classExists("cn.nukkit.Server")) {
            new NukkitLogHandler();
        } else if (LoggingUtil.classExists("net.md_5.bungee.api.ProxyServer")) {
            new BungeeLogHandler();
        }
    }

    public static void setLogHandler(LogHandler newHandler) {
        logHandler.dispose();
        logHandler = newHandler;
    }

    public static void close() {
        LogHandler.close();
        logHandler.dispose();
    }

}
