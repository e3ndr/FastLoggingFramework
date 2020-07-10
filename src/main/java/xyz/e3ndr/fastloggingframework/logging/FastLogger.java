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
        return this.log(LogLevel.INFO, parseFormat(object, args));
    }

    public FastLogger warn(@Nullable Object object, @Nullable Object... args) {
        return this.log(LogLevel.WARNING, parseFormat(object, args));
    }

    public FastLogger severe(@Nullable Object object, @Nullable Object... args) {
        return this.log(LogLevel.SEVERE, parseFormat(object, args));
    }

    public FastLogger debug(@Nullable Object object, @Nullable Object... args) {
        return this.log(LogLevel.DEBUG, parseFormat(object, args));
    }

    public FastLogger exception(Throwable e) {
        if (LogLevel.SEVERE.canLog(this.currentLevel)) {
            LogHandler.exception(this.name, e);
        }

        return this;
    }

    public static String parseFormat(@Nullable Object object, @Nullable Object... args) {
        if (object == null) {
            return "null";
        } else {
            String format = object.toString();

            for (Object o : args) {
                format = format.replaceFirst("{}", (o == null) ? "null" : o.toString());
            }

            return format;
        }
    }

}
