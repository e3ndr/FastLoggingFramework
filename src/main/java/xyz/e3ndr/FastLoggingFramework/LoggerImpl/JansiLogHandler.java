package xyz.e3ndr.FastLoggingFramework.LoggerImpl;

import org.fusesource.jansi.AnsiConsole;
import org.jetbrains.annotations.NotNull;

import lombok.Getter;
import lombok.Setter;
import xyz.e3ndr.FastLoggingFramework.AbstractLogHandler;
import xyz.e3ndr.FastLoggingFramework.Logging.LogColor;
import xyz.e3ndr.FastLoggingFramework.Logging.LogLevel;

public class JansiLogHandler extends AbstractLogHandler {
    private static @Getter @Setter boolean usingJansiConsole = true;

    @Override
    protected void log0(@NotNull LogLevel level, @NotNull String formatted) {
        if (usingJansiConsole) {
            AnsiConsole.out.println(LogColor.translateToAnsi(formatted));
        } else {
            System.out.println(LogColor.translateToAnsi(formatted));
        }
    }

}
