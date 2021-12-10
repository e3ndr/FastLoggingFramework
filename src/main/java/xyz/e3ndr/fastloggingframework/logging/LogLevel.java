package xyz.e3ndr.fastloggingframework.logging;

import lombok.Getter;

@Getter
public enum LogLevel {
    // @formatter:off
    FATAL  ("FATAL ", -1, LogColor.LIGHT_RED,    LogColor.LIGHT_RED   ), // Fatal is always logged.
    SEVERE ("SEVERE",  1, LogColor.RED,          LogColor.RED         ),
    WARNING("WARN  ",  2, LogColor.LIGHT_YELLOW, LogColor.LIGHT_YELLOW),
    INFO   ("INFO  ",  3, LogColor.LIGHT_BLUE,   LogColor.DEFAULT     ),
    DEBUG  ("DEBUG ",  4, LogColor.GREEN,        LogColor.GREEN       ),
    TRACE  ("TRACE ",  5, LogColor.LIGHT_GREEN,  LogColor.GREEN       ),
    
    NONE(0,                  INFO),
    ALL (Integer.MAX_VALUE,  INFO);
    // @formatter:on

    private String prettyString;
    private LogColor textColor;
    private int priority;

    private LogLevel(String prettyString, int intValue, LogColor color, LogColor textColor) {
        this.prettyString = color + prettyString;
        this.textColor = textColor;
        this.priority = intValue;
    }

    private LogLevel(int intValue, LogLevel copy) {
        this.prettyString = copy.prettyString;
        this.textColor = copy.textColor;
        this.priority = intValue;
    }

    @Override
    public String toString() {
        return this.prettyString;
    }

    public boolean canLog(LogLevel currentLevel) {
        if (this.priority == 0) {
            return false;
        } else {
            return this.priority <= currentLevel.priority;
        }
    }

}
