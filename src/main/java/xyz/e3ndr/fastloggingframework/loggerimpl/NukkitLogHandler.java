package xyz.e3ndr.fastloggingframework.loggerimpl;

import org.jetbrains.annotations.NotNull;

import cn.nukkit.Server;
import xyz.e3ndr.fastloggingframework.FastLoggingFramework;
import xyz.e3ndr.fastloggingframework.FastLogHandler;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

public class NukkitLogHandler extends FastLogHandler {

    public NukkitLogHandler() {
        if (Server.getInstance() != null) {
            FastLoggingFramework.setLogHandler(this);
        }
    }

    @Override
    protected void log(@NotNull String name, @NotNull LogLevel level, @NotNull String formatted) {
        Server.getInstance().getConsoleSender().sendMessage(formatted);
    }

}
