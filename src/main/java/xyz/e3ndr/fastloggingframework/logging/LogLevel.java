package xyz.e3ndr.fastloggingframework.logging;

import lombok.Getter;

@Getter
public enum LogLevel {
    NONE("NONE  ", 0, LogColor.DEFAULT, LogColor.DEFAULT),
    INFO("INFO  ", 1, LogColor.LIGHT_BLUE, LogColor.DEFAULT),
    WARNING("WARN  ", 2, LogColor.LIGHT_YELLOW, LogColor.LIGHT_YELLOW),
    SEVERE("SEVERE", 3, LogColor.RED, LogColor.RED),
    DEBUG("DEBUG ", 4, LogColor.GREEN, LogColor.DEFAULT),
    TRACE("TRACE ", 5, LogColor.LIGHT_AQUA, LogColor.DEFAULT);

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
        return this.intValue <= currentLevel.intValue;
    }

}
