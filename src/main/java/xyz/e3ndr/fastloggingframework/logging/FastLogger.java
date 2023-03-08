package xyz.e3ndr.fastloggingframework.logging;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;
import lombok.NonNull;
import xyz.e3ndr.fastloggingframework.FastLogHandler;
import xyz.e3ndr.fastloggingframework.FastLoggingFramework;

public class FastLogger {
    private @Getter @Nullable LogLevel currentLevel;
    private @Getter String name;

    private @Getter @Nullable FastLogger parentLogger = null;

    public FastLogger() {
        this(StringUtil.getCallingClass());
    }

    public FastLogger(@NonNull Class<?> clazz) {
        this(clazz.getSimpleName());
    }

    public FastLogger(@NonNull String name) {
        this.name = name;
        this.currentLevel = FastLoggingFramework.getDefaultLevel();
    }

    /**
     * @param  newLevel the new level to use, can be null IF this is a child
     *                  instance.
     * 
     * @return          this instance, for chaining.
     */
    public FastLogger setCurrentLevel(LogLevel newLevel) {
        if (newLevel == null && this.parentLogger == null) {
            throw new IllegalArgumentException("newLevel cannot be null unless there is a parent logger present.");
        }
        this.currentLevel = newLevel;
        return this;
    }

    /* -------------- */
    /* Parent/Child   */
    /* -------------- */

    public boolean isChild() {
        return this.parentLogger != null;
    }

    /**
     * 
     * @param  childName the name of the child logger.
     * 
     * @return           a new child instance, with it's level automatically tied to
     *                   the parent.
     */
    public FastLogger createChild(@NonNull String childName) {
        FastLogger child = new FastLogger(String.format("%s / %s", this.name, childName));
        child.parentLogger = this;
        child.currentLevel = null;
        return child;
    }

    /* -------------- */
    /* Logging        */
    /* -------------- */

    public boolean canLog(@NonNull LogLevel toCheck) {
        // If we're a child and our level is null then we need to check
        // with the parent instead.
        if (this.isChild() && (this.currentLevel == null)) {
            return this.parentLogger.canLog(toCheck);
        }
        return toCheck.canLog(this.currentLevel);
    }

    public FastLogger log(@NonNull LogLevel level, @Nullable Object object, @Nullable Object... args) {
        if (this.canLog(level)) {
            FastLogHandler.log(level, this.name, object, args);
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
        if (LogLevel.SEVERE.canLog(FastLoggingFramework.getDefaultLevel())) {
            FastLogHandler.log(LogLevel.SEVERE, StringUtil.getCallingClass(), e);
        }
    }

    public static void logStatic(@Nullable Object object, @Nullable Object... args) {
        if (LogLevel.INFO.canLog(FastLoggingFramework.getDefaultLevel())) {
            FastLogHandler.log(LogLevel.INFO, StringUtil.getCallingClass(), object, args);
        }
    }

    public static void logStatic(@NonNull LogLevel level, @Nullable Object object, @Nullable Object... args) {
        if (level.canLog(FastLoggingFramework.getDefaultLevel())) {
            FastLogHandler.log(level, StringUtil.getCallingClass(), object, args);
        }
    }

}
