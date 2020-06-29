package xyz.e3ndr.fastloggingframework.loggerimpl;

import org.fusesource.jansi.AnsiConsole;
import org.jetbrains.annotations.NotNull;

import lombok.Getter;
import lombok.Setter;
import xyz.e3ndr.fastloggingframework.LogHandler;
import xyz.e3ndr.fastloggingframework.logging.LogColor;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

public class JansiLogHandler extends LogHandler {
    private static @Getter @Setter boolean usingJansiConsole = true;

    @Override
    protected void log(@NotNull LogLevel level, @NotNull String formatted) {
        if (usingJansiConsole) {
            AnsiConsole.out.println(LogColor.translateToAnsi(formatted));
        } else {
            System.out.println(LogColor.translateToAnsi(formatted));
        }
    }

}
