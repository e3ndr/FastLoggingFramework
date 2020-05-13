package xyz.e3ndr.FastLoggingFramework.LoggerImpl;

import org.jetbrains.annotations.NotNull;

import xyz.e3ndr.FastLoggingFramework.AbstractLogHandler;
import xyz.e3ndr.FastLoggingFramework.Logging.LogColor;
import xyz.e3ndr.FastLoggingFramework.Logging.LogLevel;

public class StaticLogHandler extends AbstractLogHandler {

    @Override
    protected void log0(@NotNull LogLevel level, @NotNull String formatted) {
        System.out.println(LogColor.translateToAnsi(formatted));
    }

}
