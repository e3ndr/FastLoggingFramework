package xyz.e3ndr.fastloggingframework.loggerimpl;

import org.jetbrains.annotations.NotNull;

import xyz.e3ndr.fastloggingframework.FastLogHandler;
import xyz.e3ndr.fastloggingframework.FastLoggingFramework;
import xyz.e3ndr.fastloggingframework.logging.LogColor;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

public class StaticLogHandler extends FastLogHandler {

    @Override
    protected void log(@NotNull String name, @NotNull LogLevel level, @NotNull String raw) {
        if (FastLoggingFramework.isColorEnabled()) {
            System.out.print(LogColor.translateToAnsi(createFrontPorch(name, level)));
            System.out.print(LogColor.translateToAnsi(raw));
            System.out.println(LogColor.DEFAULT.getAnsiColor());
        } else {
            System.out.print(LogColor.strip(createFrontPorch(name, level)));
            System.out.print(LogColor.strip(raw));
            System.out.println();
        }
    }

}
