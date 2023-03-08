package xyz.e3ndr.fastloggingframework.logging;

import lombok.Getter;

@Getter
public enum LogColor {
    // @formatter: offs
    RESET("r", "#AAAAAA", "\u001b[039m\u001b[022m\u001b[024m\u001b[023m\u001b[029m"),
    DEFAULT(RESET),

    BOLD("l", "", "\u001b[01m"),
    UNDERLINE("n", "", "\u001b[04m"),
    ITALIC("o", "", "\u001b[03m"),
    STRIKETHROUGH("m", "", "\u001b[09m"),

    RED("4", "#AA0000", "\u001b[031m"),
    LIGHT_RED("c", "#FF5555", "\u001b[091m"),

    YELLOW("6", "#FFAA00", "\u001b[033m"),
    LIGHT_YELLOW("e", "#FFFF55", "\u001b[093m"),

    GREEN("2", "#00AA00", "\u001b[032m"),
    LIGHT_GREEN("a", "#55FF55", "\u001b[092m"),

    AQUA("3", "#00AAAA", "\u001b[036m"),
    LIGHT_AQUA("b", "#55FFFF", "\u001b[096m"),

    BLUE("1", "#0000AA", "\u001b[034m"),
    LIGHT_BLUE("9", "#5555FF", "\u001b[094m"),

    MAGENTA("5", "#AA00AA", "\u001b[035m"),
    LIGHT_MAGENTA("d", "#FF55FF", "\u001b[095m"),

    DARK_GREY("8", "#555555", "\u001b[090m"),
    DARK_GRAY("8", "#555555", "\u001b[090m"),
    GREY("7", "#AAAAAA", "\u001b[039m"),
    GRAY("7", "#AAAAAA", "\u001b[039m"),

    BLACK("0", "#000000", "\u001b[030m"),
    WHITE("f", "#FFFFFF", "\u001b[037m"),

    // @formatter: on
    ;

    private static final @Getter String flag = "\u00A7";
    private String colorCode;
    private String ansiColor;
    private String cssColor;

    private LogColor(String colorCode, String ansiColor) {
        this(colorCode, "", ansiColor);
    }

    private LogColor(LogColor copy) {
        this.cssColor = copy.cssColor;
        this.colorCode = copy.colorCode;
        this.ansiColor = copy.ansiColor;
    }

    private LogColor(String colorCode, String cssColor, String ansiColor) {
        this.cssColor = cssColor;
        this.colorCode = colorCode;
        this.ansiColor = ansiColor;
    }

    @Override
    public String toString() {
        return flag + this.colorCode;
    }

    public static String translateToAnsi(String str) {
        for (LogColor color : values()) {
            str = str
                .replace(color.toString(), color.ansiColor)
                .replace(color.toString().toUpperCase(), color.ansiColor);
        }
        return str;
    }

    public static String translateAlternateCodes(String str) {
        for (LogColor color : values()) {
            str = str
                .replace('&' + color.colorCode, color.toString());
        }
        return str;
    }

    public static String strip(String str) {
        for (LogColor color : values()) {
            str = str
                .replace('&' + color.colorCode, color.toString())
                .replace(color.toString(), "")
                .replace(color.ansiColor, "");
        }

        return str;
    }

    public static LogColor getColor(String code) {
        for (LogColor color : values()) {
            if (color.toString().equalsIgnoreCase(code) ||
                color.name().equalsIgnoreCase(code) ||
                color.getColorCode().equalsIgnoreCase(code)) {
                return color;
            }
        }
        return null;
    }

}
