package xyz.e3ndr.fastloggingframework;

import io.github.alexarchambault.windowsansi.WindowsAnsi;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import xyz.e3ndr.fastloggingframework.loggerimpl.BukkitLogHandler;
import xyz.e3ndr.fastloggingframework.loggerimpl.BungeeLogHandler;
import xyz.e3ndr.fastloggingframework.loggerimpl.NukkitLogHandler;
import xyz.e3ndr.fastloggingframework.loggerimpl.StaticLogHandler;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;
import xyz.e3ndr.fastloggingframework.logging.LoggingUtil;

public class FastLoggingFramework {
    private static @Getter @Setter @NonNull LogLevel defaultLevel = LogLevel.INFO;
    private static @Getter @NonNull LogHandler logHandler = new StaticLogHandler();

    static {
        try {
            if (System.getProperty("os.name", "").toLowerCase().contains("windows")) {
                WindowsAnsi.setup();
            }
        } catch (Exception ignored) {
            // For some reason windows-ansi will throw on success in an IDE, so we catch.
        }

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
