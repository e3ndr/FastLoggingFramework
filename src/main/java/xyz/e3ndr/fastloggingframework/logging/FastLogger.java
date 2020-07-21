package xyz.e3ndr.fastloggingframework.logging;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import xyz.e3ndr.fastloggingframework.FastLoggingFramework;
import xyz.e3ndr.fastloggingframework.LogHandler;

@Accessors(chain = true)
public class FastLogger {
    private @Getter @Setter LogLevel currentLevel;
    private @Getter String name;

    public FastLogger() {
        this(FastLoggingFramework.getCallingClass().getSimpleName(), FastLoggingFramework.getDefaultLevel());
    }

    public FastLogger(@NonNull Class<?> clazz) {
        this(clazz, FastLoggingFramework.getDefaultLevel());
    }

    public FastLogger(@NonNull String name) {
        this(name, FastLoggingFramework.getDefaultLevel());
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

    public FastLogger log(@NonNull LogLevel level, @Nullable Object message) {
        if (level.canLog(this.currentLevel)) {
            LogHandler.log(level, this.name, message);
        }

        return this;
    }

    public FastLogger println() {
        return this.log(LogLevel.INFO, "");
    }

    public FastLogger println(@Nullable Object message) {
        return this.log(LogLevel.INFO, message);
    }

    public FastLogger info(@Nullable Object object, @Nullable Object... args) {
        return this.log(LogLevel.INFO, LoggingUtil.parseFormat(object, args));
    }

    public FastLogger warn(@Nullable Object object, @Nullable Object... args) {
        return this.log(LogLevel.WARNING, LoggingUtil.parseFormat(object, args));
    }

    public FastLogger severe(@Nullable Object object, @Nullable Object... args) {
        return this.log(LogLevel.SEVERE, LoggingUtil.parseFormat(object, args));
    }

    public FastLogger debug(@Nullable Object object, @Nullable Object... args) {
        return this.log(LogLevel.DEBUG, LoggingUtil.parseFormat(object, args));
    }

    public FastLogger exception(@NonNull Throwable e) {
        return this.severe(e);
    }

    public static void logException(@NonNull Throwable e) {
        if (LogLevel.SEVERE.canLog(FastLoggingFramework.getDefaultLevel())) {
            LogHandler.log(LogLevel.SEVERE, FastLoggingFramework.getCallingClass().getSimpleName(), LoggingUtil.parseFormat(e));
        }
    }

    public static void logStatic(@Nullable Object object, @Nullable Object... args) {
        if (LogLevel.INFO.canLog(FastLoggingFramework.getDefaultLevel())) {
            LogHandler.log(LogLevel.INFO, FastLoggingFramework.getCallingClass().getSimpleName(), LoggingUtil.parseFormat(object, args));
        }
    }

    public static void logStatic(@NonNull LogLevel level, @Nullable Object object, @Nullable Object... args) {
        if (level.canLog(FastLoggingFramework.getDefaultLevel())) {
            LogHandler.log(level, FastLoggingFramework.getCallingClass().getSimpleName(), LoggingUtil.parseFormat(object, args));
        }
    }

}
