package xyz.e3ndr.fastloggingframework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jetbrains.annotations.NotNull;

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
    private static List<Message> messageCache = Collections.synchronizedList(new ArrayList<>());

    protected static boolean showingColor = true;

    private static Thread lockThread = new Thread();

    @SuppressWarnings("deprecation")
    private static synchronized void lock() {
        if (!lockThread.isAlive()) {
            Thread oldThread = lockThread;

            lockThread = new Thread(() -> {
                try {
                    Thread.sleep(Long.MAX_VALUE);
                } catch (Exception ignored) {}
            });

            // This ensures we never have a time gap.
            lockThread.start();
            oldThread.stop();
        }
    }

    static {
        Thread thread = new Thread() {
            @SuppressWarnings("deprecation")
            @Override
            public void run() {
                while (true) {
                    synchronized (messageCache) {
                        try {
                            messageCache.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    while (!messageCache.isEmpty()) {
                        Message message = messageCache.remove(0);

                        for (String line : message.lines) {
                            String formattedLine = String.format(
                                "&7[%s&7] [%s&r&7]%s %s&r",
                                message.level.toString(),
                                message.name,
                                message.level.getTextColor(),
                                line
                            );

                            formattedLine = showingColor ? LogColor.translateAlternateCodes(formattedLine) : LogColor.strip(formattedLine);

                            FastLoggingFramework.getLogHandler().log(message.level, formattedLine);
                        }
                    }

                    lockThread.stop();
                }
            }
        };

        thread.setDaemon(true);
        thread.setName("FastLoggingFramework Logging Thread");
        thread.start();
    }

    protected abstract void log(@NotNull LogLevel level, @NotNull String formatted);

    public static void log(@NonNull LogLevel level, @NonNull String name, @NonNull String message) {
        if (level == LogLevel.NONE) return;

        // We use the lock thread to prevent the JVM from terminating while we're trying
        // to log the last messages.
        lock();
        messageCache.add(new Message(level, name, message.split("\n")));
        wake();
    }

    @AllArgsConstructor
    private static class Message {
        private LogLevel level;
        private String name;
        private String[] lines;

    }

    private static void wake() {
        synchronized (messageCache) {
            messageCache.notifyAll();
        }
    }

}
