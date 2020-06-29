package xyz.e3ndr.fastloggingframework.logging;

import org.fusesource.jansi.Ansi;

import lombok.Getter;

@Getter
public enum LogColor {
    RESET("r", Ansi.Color.DEFAULT.fg(), Ansi.Attribute.INTENSITY_BOLD_OFF.value(), Ansi.Attribute.UNDERLINE_OFF.value(), Ansi.Attribute.ITALIC_OFF.value(), Ansi.Attribute.STRIKETHROUGH_OFF.value()),
    DEFAULT(RESET),

    BOLD("l", Ansi.Attribute.INTENSITY_BOLD.value()),
    UNDERLINE("n", Ansi.Attribute.UNDERLINE.value()),
    ITALIC("o", Ansi.Attribute.ITALIC.value()),
    STRIKETHROUGH("m", Ansi.Attribute.STRIKETHROUGH_ON.value()),

    RED("4", Ansi.Color.RED.fg()),
    LIGHT_RED("c", Ansi.Color.RED.fgBright()),
    YELLOW("6", Ansi.Color.YELLOW.fg()),
    LIGHT_YELLOW("e", Ansi.Color.YELLOW.fgBright()),
    GREEN("2", Ansi.Color.GREEN.fg()),
    LIGHT_GREEN("a", Ansi.Color.GREEN.fgBright()),
    AQUA("3", Ansi.Color.CYAN.fg()),
    LIGHT_AQUA("b", Ansi.Color.CYAN.fgBright()),
    BLUE("1", Ansi.Color.BLUE.fg()),
    LIGHT_BLUE("9", Ansi.Color.BLUE.fgBright()),
    MAGENTA("5", Ansi.Color.MAGENTA.fg()),
    LIGHT_MAGENTA("d", Ansi.Color.MAGENTA.fgBright()),
    WHITE("f", Ansi.Color.WHITE.fg()),
    GREY("7", Ansi.Color.DEFAULT.fg()),
    GRAY("7", Ansi.Color.DEFAULT.fg()),
    DARK_GREY("8", Ansi.Color.BLACK.fgBright()),
    DARK_GRAY("8", Ansi.Color.BLACK.fgBright()),
    BLACK("0", Ansi.Color.BLACK.fg());

    private String colorCode;
    private String ansiColor;

    private LogColor(String colorCode, int... ansi) {
        StringBuilder sb = new StringBuilder();

        for (int code : ansi) {
            sb.append("\033[0").append(code).append("m");
        }

        this.colorCode = colorCode;
        this.ansiColor = sb.toString();
    }

    private LogColor(LogColor copy) {
        this.colorCode = copy.colorCode;
        this.ansiColor = copy.ansiColor;
    }

    @Override
    public String toString() {
        return '\u00A7' + this.colorCode;
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
            if (color.toString().equalsIgnoreCase(code) || color.name().equalsIgnoreCase(code)) {
                return color;
            }
        }

        return null;
    }

}
