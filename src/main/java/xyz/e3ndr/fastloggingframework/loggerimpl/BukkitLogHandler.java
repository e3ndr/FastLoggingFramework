package xyz.e3ndr.fastloggingframework.loggerimpl;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import xyz.e3ndr.fastloggingframework.LogHandler;
import xyz.e3ndr.fastloggingframework.FastLoggingFramework;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

public class BukkitLogHandler extends LogHandler {

    public BukkitLogHandler() {
        if (Bukkit.getServer() != null) {
            FastLoggingFramework.setLogHandler(this);
        }
    }

    @Override
    protected void log(@NotNull LogLevel level, @NotNull String formatted) {
        Bukkit.getConsoleSender().sendMessage(formatted);
    }

}
