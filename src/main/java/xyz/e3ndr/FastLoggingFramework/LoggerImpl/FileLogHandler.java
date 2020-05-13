package xyz.e3ndr.FastLoggingFramework.LoggerImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.jetbrains.annotations.NotNull;

import lombok.NonNull;
import xyz.e3ndr.FastLoggingFramework.Logging.LogColor;
import xyz.e3ndr.FastLoggingFramework.Logging.LogLevel;

public class FileLogHandler extends JansiLogHandler {
    private PrintWriter writer;

    public FileLogHandler(@NonNull File out) throws IOException {
        if (!out.exists()) out.createNewFile();

        this.writer = new PrintWriter(new FileOutputStream(out), true);
    }

    @Override
    protected void log0(@NotNull LogLevel level, @NotNull String formatted) {
        super.log0(level, formatted);

        this.writer.println(LogColor.strip(formatted));
    }

    @Override
    public void dispose() {
        this.writer.flush();
        this.writer.close();
    }

}
