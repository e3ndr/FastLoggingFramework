package xyz.e3ndr.fastloggingframework;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import xyz.e3ndr.fastloggingframework.logging.LogColor;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

@Getter
@Setter
@Accessors(chain = true)
public abstract class LogHandler {
    private static boolean running = true;
    private static List<Message> messageCache = Collections.synchronizedList(new ArrayList<>());

    protected static boolean showingColor = true;

    static {
        (new Thread() {
            @Override
            public void run() {
                while (running) {
                    synchronized (messageCache) {
                        try {
                            messageCache.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    while (messageCache.size() != 0) {
                        Message message = messageCache.get(0);
                        FastLoggingFramework.getLogHandler().log(message.level, message.line);
                        messageCache.remove(0);
                    }
                }
            }
        }).start();
    }

    protected abstract void log(@NotNull LogLevel level, @NotNull String formatted);

    public void dispose() {}

    public static void exception(@NonNull String name, @NonNull Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        e.printStackTrace(pw);

        String out = sw.toString();

        pw.close();
        pw.flush();
        sw.flush();

        log(LogLevel.SEVERE, name, out.substring(0, out.length() - 2));
    }

    public static void log(@NonNull LogLevel level, @NonNull String name, @Nullable Object message) {
        if (level == LogLevel.NONE) return;

        String line = String.format("&7[%s&7] [%s&7]&7%s %s&r", level.toString(), name, level.getTextColor(), message.toString());

        line = showingColor ? LogColor.translateAlternateCodes(line) : LogColor.strip(line);

        messageCache.add(new Message(level, line));
        wake();
    }

    public static void close() {
        running = false;
    }

    @AllArgsConstructor
    private static class Message {
        private LogLevel level;
        private String line;
    }

    private static void wake() {
        synchronized (messageCache) {
            messageCache.notifyAll();
        }
    }

}
