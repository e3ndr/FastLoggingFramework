package xyz.e3ndr.fastloggingframework.loggerimpl;

import org.jetbrains.annotations.NotNull;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import xyz.e3ndr.fastloggingframework.LogHandler;
import xyz.e3ndr.fastloggingframework.FastLoggingFramework;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

public class BungeeLogHandler extends LogHandler {

    public BungeeLogHandler() {
        if (ProxyServer.getInstance() != null) {
            FastLoggingFramework.setLogHandler(this);
        }
    }

    @Override
    protected void log(@NotNull LogLevel level, @NotNull String formatted) {
        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(formatted));
    }

}
