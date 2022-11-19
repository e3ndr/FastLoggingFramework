package xyz.e3ndr.fastloggingframework.loggerimpl;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import xyz.e3ndr.fastloggingframework.FastLogHandler;
import xyz.e3ndr.fastloggingframework.FastLoggingFramework;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

public class BukkitLogHandler extends FastLogHandler {

    public BukkitLogHandler() {
        if (Bukkit.getServer() != null) {
            FastLoggingFramework.setLogHandler(this);
        }
    }

    @Override
    protected void log(@NotNull String name, @NotNull LogLevel level, @NotNull String raw) {
        Bukkit.getConsoleSender().sendMessage(FastLogHandler.createFrontPorch(name, level, raw));
    }

}
