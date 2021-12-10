package xyz.e3ndr.fastloggingframework;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import xyz.e3ndr.fastloggingframework.loggerimpl.AnsiLogHandler;
import xyz.e3ndr.fastloggingframework.loggerimpl.BukkitLogHandler;
import xyz.e3ndr.fastloggingframework.loggerimpl.BungeeLogHandler;
import xyz.e3ndr.fastloggingframework.loggerimpl.NukkitLogHandler;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;
import xyz.e3ndr.fastloggingframework.logging.LoggingUtil;

public class FastLoggingFramework {
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

    public static void setLogHandler(@NonNull LogHandler newHandler) {
        logHandler = newHandler;
    }

}
