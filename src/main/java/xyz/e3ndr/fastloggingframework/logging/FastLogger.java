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

    public FastLogger log(@NonNull LogLevel level, @NonNull String message) {
        if (level.canLog(this.currentLevel)) {
            LogHandler.log(level, this.name, message);
        }

        return this;
    }

    public FastLogger println() {
        return this.log(LogLevel.INFO, "");
    }

    public FastLogger info(@Nullable Object object, @Nullable Object... args) {
        if (LogLevel.INFO.canLog(this.currentLevel)) {
            LogHandler.log(LogLevel.INFO, this.name, LoggingUtil.parseFormat(object, args));
        }

        return this;
    }

    public FastLogger warn(@Nullable Object object, @Nullable Object... args) {
        if (LogLevel.WARNING.canLog(this.currentLevel)) {
            LogHandler.log(LogLevel.WARNING, this.name, LoggingUtil.parseFormat(object, args));
        }

        return this;
    }

    public FastLogger severe(@Nullable Object object, @Nullable Object... args) {
        if (LogLevel.SEVERE.canLog(this.currentLevel)) {
            LogHandler.log(LogLevel.SEVERE, this.name, LoggingUtil.parseFormat(object, args));
        }

        return this;
    }

    public FastLogger debug(@Nullable Object object, @Nullable Object... args) {
        if (LogLevel.DEBUG.canLog(this.currentLevel)) {
            LogHandler.log(LogLevel.DEBUG, this.name, LoggingUtil.parseFormat(object, args));
        }

        return this;
    }

    public FastLogger entering(@Nullable Object... args) {
        if (this.currentLevel == LogLevel.TRACE) {
            StringBuilder sb = new StringBuilder();

            for (Object object : args) {
                sb.append(", ");

                if (object instanceof Throwable) {
                    sb.append(LoggingUtil.getExceptionStack((Throwable) object));
                } else {
                    sb.append(object);
                }
            }

            LogHandler.log(LogLevel.TRACE, this.name, String.format("Entering %s with the following arguments: %s", LoggingUtil.getCaller(), sb.substring(2)));
        }

        return this;
    }

    public FastLogger exiting() {
        if (this.currentLevel == LogLevel.TRACE) {
            LogHandler.log(LogLevel.TRACE, this.name, String.format("Exiting %s.", LoggingUtil.getCaller()));
        }

        return this;
    }

    public FastLogger exception(@NonNull Throwable e) {
        return this.severe(e);
    }

    public static void logException(@Nullable Throwable e) {
        if (LogLevel.SEVERE.canLog(FastLoggingFramework.getDefaultLevel())) {
            LogHandler.log(LogLevel.SEVERE, LoggingUtil.getCallingClass().getSimpleName(), LoggingUtil.parseFormat(e));
        }
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
