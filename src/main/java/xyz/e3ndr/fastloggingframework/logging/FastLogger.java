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
        this(LoggingUtil.getCallingClass().getSimpleName(), FastLoggingFramework.getDefaultLevel());
    }

    public FastLogger(@NonNull Class<?> clazz) {
        this(clazz, FastLoggingFramework.getDefaultLevel());
    }

    public FastLogger(@NonNull String name) {
        this(name, FastLoggingFramework.getDefaultLevel());
    }

    public FastLogger(@NonNull LogLevel level) {
        this(LoggingUtil.getCallingClass().getSimpleName(), level);
    }

    public FastLogger(@NotNull Class<?> clazz, @NonNull LogLevel level) {
        this(clazz.getSimpleName(), level);
    }

    public FastLogger(@NonNull String name, @NonNull LogLevel level) {
        this.currentLevel = level;
        this.name = name;
    }

    /* -------------- */
    /* Logging        */
    /* -------------- */

    public FastLogger log(@NonNull LogLevel level, @Nullable Object object, @Nullable Object... args) {
        if (level.canLog(this.currentLevel)) {
            LogHandler.log(level, this.name, LoggingUtil.parseFormat(object, args));
        }

        return this;
    }

    // Fatal

    public FastLogger fatal(@Nullable Object object, @Nullable Object... args) {
        this.log(LogLevel.FATAL, object, args);
        return this;
    }

    // Info

    public FastLogger println() {
        return this.log(LogLevel.INFO, "");
    }

    public FastLogger info(@Nullable Object object, @Nullable Object... args) {
        this.log(LogLevel.INFO, object, args);
        return this;
    }

    // Warning

    public FastLogger warn(@Nullable Object object, @Nullable Object... args) {
        this.log(LogLevel.WARNING, object, args);
        return this;
    }

    // Severe

    public FastLogger severe(@Nullable Object object, @Nullable Object... args) {
        this.log(LogLevel.SEVERE, object, args);
        return this;
    }

    public FastLogger exception(@NonNull Throwable e) {
        return this.severe(e);
    }

    // Debug Levels

    public FastLogger debug(@Nullable Object object, @Nullable Object... args) {
        this.log(LogLevel.DEBUG, object, args);
        return this;
    }

    public FastLogger trace(@Nullable Object object, @Nullable Object... args) {
        this.log(LogLevel.TRACE, object, args);
        return this;
    }

    /* -------------- */
    /* Static Logging */
    /* -------------- */

    public static void logException(@Nullable Throwable e) {
        logStatic(LogLevel.SEVERE, e);
    }

    public static void logStatic(@Nullable Object object, @Nullable Object... args) {
        if (LogLevel.INFO.canLog(FastLoggingFramework.getDefaultLevel())) {
            LogHandler.log(LogLevel.INFO, LoggingUtil.getCallingClass().getSimpleName(), LoggingUtil.parseFormat(object, args));
        }
    }

    public static void logStatic(@NonNull LogLevel level, @Nullable Object object, @Nullable Object... args) {
        if (level.canLog(FastLoggingFramework.getDefaultLevel())) {
            LogHandler.log(level, LoggingUtil.getCallingClass().getSimpleName(), LoggingUtil.parseFormat(object, args));
        }
    }

}
