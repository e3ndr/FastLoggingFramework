package xyz.e3ndr.fastloggingframework.logging;

import lombok.Getter;

@Getter
public enum LogLevel {
    // @formatter:off
    FATAL(  "FATAL ", -1, LogColor.LIGHT_RED,    LogColor.LIGHT_RED   ), // Fatal is always logged.
    NONE(   "NONE  ", 0, LogColor.DEFAULT,      LogColor.DEFAULT     ),
    SEVERE( "SEVERE", 1, LogColor.RED,          LogColor.RED         ),
    WARNING("WARN  ", 2, LogColor.LIGHT_YELLOW, LogColor.LIGHT_YELLOW),
    INFO(   "INFO  ", 3, LogColor.LIGHT_BLUE,   LogColor.DEFAULT     ),
    DEBUG(  "DEBUG ", 4, LogColor.GREEN,        LogColor.DEFAULT     ),
    TRACE(  "TRACE ", 5, LogColor.LIGHT_AQUA,   LogColor.DEFAULT     );
    // @formatter:on

    private String prettyString;
    private int intValue;
    private LogColor textColor;

    private LogLevel(String prettyString, int intValue, LogColor color, LogColor textColor) {
        this.prettyString = color + prettyString;
        this.textColor = textColor;
        this.intValue = intValue;
    }

    @Override
    public String toString() {
        return this.prettyString;
    }

    public boolean canLog(LogLevel currentLevel) {
        if (this.intValue == 0) {
            return false;
        } else {
            return this.intValue <= currentLevel.intValue;
        }
    }

}
