package xyz.e3ndr.FastLoggingFramework.LoggerImpl;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import xyz.e3ndr.FastLoggingFramework.AbstractLogHandler;
import xyz.e3ndr.FastLoggingFramework.FastLoggingFramework;
import xyz.e3ndr.FastLoggingFramework.Logging.LogLevel;

public class BukkitLogHandler extends AbstractLogHandler {

    public BukkitLogHandler() {
        if (Bukkit.getServer() != null) {
            FastLoggingFramework.setLogHandler(this);
        }
    }

    @Override
    protected void log0(@NotNull LogLevel level, @NotNull String formatted) {
        Bukkit.getConsoleSender().sendMessage(formatted);
    }

}
