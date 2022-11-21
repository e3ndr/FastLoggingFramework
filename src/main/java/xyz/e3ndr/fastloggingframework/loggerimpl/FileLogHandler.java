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

public class FileLogHandler extends StaticLogHandler {
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
    protected void log(@NotNull String name, @NotNull LogLevel level, @NotNull String raw) {
        super.log(name, level, raw);
        this.writer.print(LogColor.strip(createFrontPorch(name, level)));
        this.writer.print(LogColor.strip(raw));
        this.writer.println();
    }

}
