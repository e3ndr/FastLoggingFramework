package xyz.e3ndr.fastloggingframework.loggerimpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;

import org.jetbrains.annotations.NotNull;

import lombok.NonNull;
import xyz.e3ndr.fastloggingframework.FastLoggingFramework;
import xyz.e3ndr.fastloggingframework.logging.LogColor;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

public class FileLogHandler extends JansiLogHandler {
    private static final String HEADER = "-------------------- UTC %s --------------------";

    private PrintWriter writer;

    public FileLogHandler() throws IOException {
        this(new File("latest.log"));
    }

    public FileLogHandler(@NonNull File out) throws IOException {
        if (!out.exists()) out.createNewFile();

        this.writer = new PrintWriter(new FileOutputStream(out, true), true);
        this.writer.println(String.format(HEADER, Instant.now().toString()));

        FastLoggingFramework.setLogHandler(this);
    }

    @Override
    protected void log(@NotNull LogLevel level, @NotNull String formatted) {
        super.log(level, formatted);
        this.writer.println(LogColor.strip(formatted));
    }

    @Override
    public void finalize() {
        this.dispose();
    }

    @Override
    public void dispose() {
        this.writer.close();
    }

}
