package xyz.e3ndr.fastloggingframework.logging;

import lombok.Getter;

@Getter
public enum LogColor {
    // @formatter: offs
    RESET("r", "#AAAAAA", "[039m[022m[024m[023m[029m"),
    DEFAULT(RESET),

    BOLD("l", "", "[01m"),
    UNDERLINE("n", "", "[04m"),
    ITALIC("o", "", "[03m"),
    STRIKETHROUGH("m", "", "[09m"),

    RED("4", "#AA0000", "[031m"),
    LIGHT_RED("c", "#FF5555", "[091m"),

    YELLOW("6", "#FFAA00", "[033m"),
    LIGHT_YELLOW("e", "#FFFF55", "[093m"),

    GREEN("2", "#00AA00", "[032m"),
    LIGHT_GREEN("a", "#55FF55", "[092m"),

    AQUA("3", "#00AAAA", "[036m"),
    LIGHT_AQUA("b", "#55FFFF", "[096m"),

    BLUE("1", "#0000AA", "[034m"),
    LIGHT_BLUE("9", "#5555FF", "[094m"),

    MAGENTA("5", "#AA00AA", "[035m"),
    LIGHT_MAGENTA("d", "#FF55FF", "[095m"),

    DARK_GREY("8", "#555555", "[090m"),
    DARK_GRAY("8", "#555555", "[090m"),
    GREY("7", "#AAAAAA", "[039m"),
    GRAY("7", "#AAAAAA", "[039m"),

    BLACK("0", "#000000", "[030m"),
    WHITE("f", "#FFFFFF", "[037m"),

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
            str = str.replace(color.toString(), color.ansiColor).replace(color.toString().toUpperCase(), color.ansiColor);
        }

        return str;
    }

    public static String translateAlternateCodes(String str) {
        for (LogColor color : values()) {
            str = str.replace('&' + color.colorCode, color.toString());
        }

        return str;
    }

    public static String strip(String str) {
        String result = str;

        for (LogColor color : values()) {
            result = result.replace('&' + color.colorCode, color.toString()).replace(color.toString(), "").replace(color.ansiColor, "");
        }

        return result;
    }

    public static LogColor getColor(String code) {
        for (LogColor color : values()) {
            if (color.toString().equalsIgnoreCase(code) || color.name().equalsIgnoreCase(code) || color.getColorCode().equalsIgnoreCase(code)) {
                return color;
            }
        }

        return null;
    }

}
