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
    private static boolean running = true;
    private static Thread thread;

    protected static boolean showingColor = true;

    static {
        thread = new Thread() {
            @Override
            public void run() {
                while (running) {
                    synchronized (messageCache) {
                        try {
                            messageCache.wait(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    int threadCount = 0;

                    for (Thread thread : Thread.getAllStackTraces().keySet()) {
                        if (!thread.isDaemon()) {
                            String name = thread.getName();

                            if (!name.equals("FastLoggingFramework Logging Thread") && !name.equals("DestroyJavaVM")) {
                                threadCount++;
                            }
                        }
                    }

                    while (!messageCache.isEmpty()) {
                        Message message = messageCache.remove(0);

                        FastLoggingFramework.getLogHandler().log(message.level, message.line);
                    }

                    if (threadCount == 0) {
                        System.out.println("FastLoggingFramework has detected that it is the only running non daemon thread, it will now close inorder for the Java VM to exit.");
                        running = false;
                    }
                }
            }
        };

        // thread.setDaemon(true);
        thread.setName("FastLoggingFramework Logging Thread");
        thread.start();
    }

    protected abstract void log(@NotNull LogLevel level, @NotNull String formatted);

    public void dispose() {}

    public static void log(@NonNull LogLevel level, @NonNull String name, @NonNull String message) {
        if (level == LogLevel.NONE) return;

        String line = String.format("&7[%s&7] [%s&7]&7%s %s&r", level.toString(), name, level.getTextColor(), message);

        line = showingColor ? LogColor.translateAlternateCodes(line) : LogColor.strip(line);

        messageCache.add(new Message(level, line));
        wake();
    }

    public static void close() {
        running = false;
        wake();
        try {
            thread.join();
        } catch (InterruptedException e) {}
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
