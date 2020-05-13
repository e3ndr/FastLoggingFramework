package xyz.e3ndr.FastLoggingFramework.Logging;

import java.util.Arrays;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import xyz.e3ndr.FastLoggingFramework.FastLoggingFramework;

@Accessors(chain = true)
public class FastLogger {
    private @Getter @Setter LogLevel currentLevel;
    private @Getter String name;

    public FastLogger() {
        this(FastLoggingFramework.getCallingClass().getSimpleName(), FastLoggingFramework.getDefautLevel());
    }

    public FastLogger(@NonNull Class<?> clazz) {
        this(clazz, FastLoggingFramework.getDefautLevel());
    }

    public FastLogger(@NonNull String name) {
        this(name, FastLoggingFramework.getDefautLevel());
    }

    public FastLogger(@NonNull LogLevel level) {
        this(FastLoggingFramework.getCallingClass().getSimpleName(), level);
    }

    public FastLogger(@NotNull Class<?> clazz, @NonNull LogLevel level) {
        this(clazz.getSimpleName(), level);
    }

    public FastLogger(@NonNull String name, @NonNull LogLevel level) {
        this.currentLevel = level;
        this.name = name;
    }

    public FastLogger log(@NonNull LogLevel level, @Nullable String message) {
        if (level.canLog(this.currentLevel)) {
            FastLoggingFramework.getLogHandler().log(level, this.name, message);
        }

        return this;
    }

    public FastLogger logArray(@NonNull LogLevel level, @Nullable Object[] array) {
        if (level.canLog(this.currentLevel)) {
            FastLoggingFramework.getLogHandler().log(level, this.name, Arrays.deepToString(array));
        }

        return this;
    }

    public FastLogger logArray(@NonNull LogLevel level, @Nullable int[] array) {
        if (level.canLog(this.currentLevel)) {
            FastLoggingFramework.getLogHandler().log(level, this.name, Arrays.toString(array));
        }

        return this;
    }

    public FastLogger logArray(@NonNull LogLevel level, @Nullable long[] array) {
        if (level.canLog(this.currentLevel)) {
            FastLoggingFramework.getLogHandler().log(level, this.name, Arrays.toString(array));
        }

        return this;
    }

    public FastLogger logArray(@NonNull LogLevel level, @Nullable double[] array) {
        if (level.canLog(this.currentLevel)) {
            FastLoggingFramework.getLogHandler().log(level, this.name, Arrays.toString(array));
        }

        return this;
    }

    public FastLogger logArray(@NonNull LogLevel level, @Nullable float[] array) {
        if (level.canLog(this.currentLevel)) {
            FastLoggingFramework.getLogHandler().log(level, this.name, Arrays.toString(array));
        }

        return this;
    }

    public FastLogger logArray(@NonNull LogLevel level, @Nullable short[] array) {
        if (level.canLog(this.currentLevel)) {
            FastLoggingFramework.getLogHandler().log(level, this.name, Arrays.toString(array));
        }

        return this;
    }

    public FastLogger logArray(@NonNull LogLevel level, @Nullable byte[] array) {
        if (level.canLog(this.currentLevel)) {
            FastLoggingFramework.getLogHandler().log(level, this.name, Arrays.toString(array));
        }

        return this;
    }

    public FastLogger logArray(@NonNull LogLevel level, @Nullable char[] array) {
        if (level.canLog(this.currentLevel)) {
            FastLoggingFramework.getLogHandler().log(level, this.name, Arrays.toString(array));
        }

        return this;
    }

    public FastLogger logArray(@NonNull LogLevel level, @Nullable boolean[] array) {
        if (level.canLog(this.currentLevel)) {
            FastLoggingFramework.getLogHandler().log(level, this.name, Arrays.toString(array));
        }

        return this;
    }

    public FastLogger println() {
        return this.log(LogLevel.INFO, "");
    }

    public FastLogger println(@Nullable String message) {
        return this.log(LogLevel.INFO, message);
    }

    public FastLogger info(@Nullable String message) {
        return this.log(LogLevel.INFO, message);
    }

    public FastLogger warn(@Nullable String message) {
        return this.log(LogLevel.WARNING, message);
    }

    public FastLogger warning(@Nullable String message) {
        return this.log(LogLevel.WARNING, message);
    }

    public FastLogger severe(@Nullable String message) {
        return this.log(LogLevel.SEVERE, message);
    }

    public FastLogger debug(@Nullable String message) {
        return this.log(LogLevel.DEBUG, message);
    }

    public FastLogger exception(Throwable e) {
        if (LogLevel.SEVERE.canLog(this.currentLevel)) {
            FastLoggingFramework.getLogHandler().exception(this.name, e);
        }

        return this;
    }

}
