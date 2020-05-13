package xyz.e3ndr.FastLoggingFramework;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import xyz.e3ndr.FastLoggingFramework.Logging.LogColor;
import xyz.e3ndr.FastLoggingFramework.Logging.LogLevel;

@Getter
@Setter
@Accessors(chain = true)
public abstract class AbstractLogHandler {
    private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    private static ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();

    private static @Getter @Setter boolean async = false;

    protected boolean showingColor = true;

    private static ExecutorService getPool() {
        return async ? cachedThreadPool : singleThreadPool;
    }

    public void log(@NonNull LogLevel level, @NonNull String name, @Nullable String message) {
        if (level == LogLevel.NONE) return;

        getPool().submit(() -> {
            String line = String.format("&7[%s&7] [%s&7]&7%s %s&r", level.toString(), name, level.getTextColor(), message);

            this.log0(level, this.showingColor ? LogColor.translateAlternateCodes(line) : LogColor.strip(line));
        });
    }

    public void exception(@NonNull String name, @NonNull Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        e.printStackTrace(pw);

        String out = sw.toString();

        pw.close();
        pw.flush();
        sw.flush();

        this.log(LogLevel.SEVERE, name, out.substring(0, out.length() - 2));
    }

    protected abstract void log0(@NotNull LogLevel level, @NotNull String formatted);

    public static void close() {
        cachedThreadPool.shutdown();
        singleThreadPool.shutdown();
    }

    public void dispose() {}

}
