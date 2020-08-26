package xyz.e3ndr.fastloggingframework.logging;

import org.fusesource.jansi.Ansi;

import javafx.scene.paint.Color;
import lombok.Getter;

@SuppressWarnings("restriction")
@Getter
public enum LogColor {
    RESET("r", Color.web("#AAAAAA"), Ansi.Color.DEFAULT.fg(), Ansi.Attribute.INTENSITY_BOLD_OFF.value(), Ansi.Attribute.UNDERLINE_OFF.value(), Ansi.Attribute.ITALIC_OFF.value(), Ansi.Attribute.STRIKETHROUGH_OFF.value()),
    DEFAULT(RESET),

    BOLD("l", Ansi.Attribute.INTENSITY_BOLD.value()),
    UNDERLINE("n", Ansi.Attribute.UNDERLINE.value()),
    ITALIC("o", Ansi.Attribute.ITALIC.value()),
    STRIKETHROUGH("m", Ansi.Attribute.STRIKETHROUGH_ON.value()),

    RED("4", Color.web("#AA0000"), Ansi.Color.RED.fg()),
    LIGHT_RED("c", Color.web("#FF5555"), Ansi.Color.RED.fgBright()),

    YELLOW("6", Color.web("#FFAA00"), Ansi.Color.YELLOW.fg()),
    LIGHT_YELLOW("e", Color.web("#FFFF55"), Ansi.Color.YELLOW.fgBright()),

    GREEN("2", Color.web("#00AA00"), Ansi.Color.GREEN.fg()),
    LIGHT_GREEN("a", Color.web("#55FF55"), Ansi.Color.GREEN.fgBright()),

    AQUA("3", Color.web("#00AAAA"), Ansi.Color.CYAN.fg()),
    LIGHT_AQUA("b", Color.web("#55FFFF"), Ansi.Color.CYAN.fgBright()),

    BLUE("1", Color.web("#0000AA"), Ansi.Color.BLUE.fg()),
    LIGHT_BLUE("9", Color.web("#5555FF"), Ansi.Color.BLUE.fgBright()),

    MAGENTA("5", Color.web("#AA00AA"), Ansi.Color.MAGENTA.fg()),
    LIGHT_MAGENTA("d", Color.web("#FF55FF"), Ansi.Color.MAGENTA.fgBright()),

    WHITE("f", Color.web("#FFFFFF"), Ansi.Color.WHITE.fg()),

    GREY("7", Color.web("#AAAAAA"), Ansi.Color.DEFAULT.fg()),
    GRAY("7", Color.web("#AAAAAA"), Ansi.Color.DEFAULT.fg()),

    DARK_GREY("8", Color.web("#555555"), Ansi.Color.BLACK.fgBright()),
    DARK_GRAY("8", Color.web("#555555"), Ansi.Color.BLACK.fgBright()),

    BLACK("0", Color.web("#000000"), Ansi.Color.BLACK.fg());

    private static final @Getter String flag = "\u00A7";
    private String colorCode;
    private String ansiColor;
    private Color color;

    private LogColor(String colorCode, int... ansi) {
        this(colorCode, Color.rgb(0, 0, 0, 0), ansi);
    }

    private LogColor(LogColor copy) {
        this.color = copy.color;
        this.colorCode = copy.colorCode;
        this.ansiColor = copy.ansiColor;
    }

    private LogColor(String colorCode, Color color, int... ansi) {
        StringBuilder sb = new StringBuilder();
        for (int code : ansi) {
            sb.append("\033[0").append(code).append("m");
        }

        this.color = color;
        this.colorCode = colorCode;
        this.ansiColor = sb.toString();
    }

    @Override
    public String toString() {
        return flag + this.colorCode;
    }

    public String getHex() {
        return getHexForColor(this.color);
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

    private static String getHex(int number) {
        String hex = Integer.toHexString(number).toUpperCase();

        return (hex.length() == 1) ? ("0" + hex) : hex;
    }

    private static String getHexForColor(Color color) {
        String red = getHex((int) (color.getRed() * 255));
        String green = getHex((int) (color.getGreen() * 255));
        String blue = getHex((int) (color.getBlue() * 255));
        String hex = String.format("#%s%s%s", red, green, blue);

        return hex;
    }

}
