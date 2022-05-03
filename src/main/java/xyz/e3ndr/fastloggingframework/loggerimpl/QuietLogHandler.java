package xyz.e3ndr.fastloggingframework.loggerimpl;

import org.jetbrains.annotations.NotNull;

import xyz.e3ndr.fastloggingframework.FastLogHandler;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

public class QuietLogHandler extends FastLogHandler {

    @Override
    protected void log(@NotNull String name, @NotNull LogLevel level, @NotNull String formatted) {}

}
