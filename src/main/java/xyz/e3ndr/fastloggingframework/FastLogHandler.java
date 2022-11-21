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
public abstract class FastLogHandler {
    private static List<Message> messageCache = Collections.synchronizedList(new ArrayList<>());

    private static boolean jvmHalting = false;

    static {
        Thread thread = new Thread(() -> {
            // When the JVM begins shutdown, we set jvmHalting to true.
            // This allows us to flush some final messages to console before the JVM
            // terminates.
            while (!jvmHalting) {
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
                        FastLoggingFramework.getLogHandler().log(message.name, message.level, line);
                    }
                }
            }
        });

        thread.setDaemon(true);
        thread.setName("FastLoggingFramework Logging Thread");
        thread.start();

        Runtime
            .getRuntime()
            .addShutdownHook(new Thread(() -> {
                try {
                    log(LogLevel.TRACE, "FastLoggingFramework", "JVM shutdown started, flushing remaining messages.");

                    jvmHalting = true;
                    wake(); // We have to wake the thread here, otherwise it'll deadlock.
                    thread.join();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }));
    }

    protected abstract void log(@NotNull String name, @NotNull LogLevel level, @NotNull String raw);

    public static String createFrontPorch(@NotNull String name, @NotNull LogLevel level) {
        String formattedLine = String.format(
            "&r&7[%s&7] [%s&r&7]&r%s ",
            level.getPrettyString(),
            name,
            level.getTextColor()
        );

        return FastLoggingFramework.isColorEnabled() ? LogColor.translateAlternateCodes(formattedLine) : LogColor.strip(formattedLine);
    }

    public static void log(@NonNull LogLevel level, @NonNull String name, @NonNull String message) {
        if (level == LogLevel.NONE) return;

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
