package xyz.e3ndr.fastloggingframework.loggerimpl;

import org.jetbrains.annotations.NotNull;

import xyz.e3ndr.consoleutil.ConsoleUtil;
import xyz.e3ndr.fastloggingframework.LogHandler;
import xyz.e3ndr.fastloggingframework.logging.LogColor;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

public class AnsiLogHandler extends LogHandler {

    @Override
    protected void log(@NotNull LogLevel level, @NotNull String formatted) {
        ConsoleUtil.out.println(LogColor.translateToAnsi(formatted));
    }

}
