package xyz.e3ndr.FastLoggingFramework.LoggerImpl;

import org.jetbrains.annotations.NotNull;

import cn.nukkit.Server;
import xyz.e3ndr.FastLoggingFramework.AbstractLogHandler;
import xyz.e3ndr.FastLoggingFramework.FastLoggingFramework;
import xyz.e3ndr.FastLoggingFramework.Logging.LogLevel;

public class NukkitLogHandler extends AbstractLogHandler {

    public NukkitLogHandler() {
        if (Server.getInstance() != null) {
            FastLoggingFramework.setLogHandler(this);
        }
    }

    @Override
    protected void log0(@NotNull LogLevel level, @NotNull String formatted) {
        Server.getInstance().getConsoleSender().sendMessage(formatted);
    }

}
