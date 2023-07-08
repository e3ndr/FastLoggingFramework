package xyz.e3ndr.fastloggingframework.loggerimpl;

import java.io.PrintStream;

import org.jetbrains.annotations.NotNull;

import xyz.e3ndr.fastloggingframework.FastLogHandler;
import xyz.e3ndr.fastloggingframework.FastLoggingFramework;
import xyz.e3ndr.fastloggingframework.logging.LogColor;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

public class StaticLogHandler extends FastLogHandler {

    /**
     * Normally just System.out, you can replace this with any {@link PrintStream}
     * that implements print(String) and println(String) and println().
     */
    public static PrintStream targetOut = System.out;

    @Override
    protected void log(@NotNull String name, @NotNull LogLevel level, @NotNull String raw) {
        if (FastLoggingFramework.isColorEnabled()) {
            targetOut.print(LogColor.translateToAnsi(createFrontPorch(name, level)));
            targetOut.print(LogColor.translateToAnsi(raw));
            targetOut.println(LogColor.DEFAULT.getAnsiColor());
        } else {
            targetOut.print(LogColor.strip(createFrontPorch(name, level)));
            targetOut.print(LogColor.strip(raw));
            targetOut.println();
        }
    }

}
