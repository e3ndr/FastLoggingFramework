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
import xyz.e3ndr.fastloggingframework.logging.StringUtil;

public class FastLoggingFramework {
    private static @Getter @Setter @NonNull LogLevel defaultLevel = LogLevel.INFO;
    private static @Getter @NonNull FastLogHandler logHandler = new StaticLogHandler();

    private static @Getter @Setter boolean colorEnabled = true;

    static {
        try {
            if (System.getProperty("os.name", "").toLowerCase().contains("windows")) {
                WindowsAnsi.setup();
            }
        } catch (Exception ignored) {
            // For some reason windows-ansi will throw on success in an IDE, so we catch.
        }

        if (StringUtil.classExists("org.bukkit.Bukkit")) {
            new BukkitLogHandler();
        } else if (StringUtil.classExists("cn.nukkit.Server")) {
            new NukkitLogHandler();
        } else if (StringUtil.classExists("net.md_5.bungee.api.ProxyServer")) {
            new BungeeLogHandler();
        }
    }

    public static void setLogHandler(@NonNull FastLogHandler newHandler) {
        logHandler = newHandler;
    }

}
