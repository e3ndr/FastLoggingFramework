package xyz.e3ndr.FastLoggingFramework.LoggerImpl;

import org.jetbrains.annotations.NotNull;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import xyz.e3ndr.FastLoggingFramework.AbstractLogHandler;
import xyz.e3ndr.FastLoggingFramework.FastLoggingFramework;
import xyz.e3ndr.FastLoggingFramework.Logging.LogLevel;

public class BungeeLogHandler extends AbstractLogHandler {

    public BungeeLogHandler() {
        if (ProxyServer.getInstance() != null) {
            FastLoggingFramework.setLogHandler(this);
        }
    }

    @Override
    protected void log0(@NotNull LogLevel level, @NotNull String formatted) {
        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(formatted));
    }

}
