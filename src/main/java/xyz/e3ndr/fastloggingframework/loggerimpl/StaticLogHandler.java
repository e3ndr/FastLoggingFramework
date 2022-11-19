package xyz.e3ndr.fastloggingframework.loggerimpl;

import org.jetbrains.annotations.NotNull;

import xyz.e3ndr.fastloggingframework.FastLogHandler;
import xyz.e3ndr.fastloggingframework.logging.LogColor;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

public class StaticLogHandler extends FastLogHandler {

    @Override
    protected void log(@NotNull String name, @NotNull LogLevel level, @NotNull String raw) {
        System.out.println(LogColor.translateToAnsi(FastLogHandler.createFrontPorch(name, level, raw)));
    }

}
