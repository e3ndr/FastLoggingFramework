package xyz.e3ndr.fastloggingframework.loggerimpl;

import org.jetbrains.annotations.NotNull;

import xyz.e3ndr.fastloggingframework.LogHandler;
import xyz.e3ndr.fastloggingframework.logging.LogColor;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

public class StaticLogHandler extends LogHandler {

    @Override
    protected void log(@NotNull LogLevel level, @NotNull String formatted) {
        System.out.println(LogColor.translateToAnsi(formatted));
    }

}
