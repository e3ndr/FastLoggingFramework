package xyz.e3ndr.fastloggingframework.loggerimpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.jetbrains.annotations.NotNull;

import lombok.NonNull;
import xyz.e3ndr.fastloggingframework.logging.LogColor;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

public class FileLogHandler extends JansiLogHandler {
    private PrintWriter writer;

    public FileLogHandler(@NonNull File out) throws IOException {
        if (!out.exists()) out.createNewFile();

        this.writer = new PrintWriter(new FileOutputStream(out), true);
    }

    @Override
    protected void log(@NotNull LogLevel level, @NotNull String formatted) {
        super.log(level, formatted);

        this.writer.println(LogColor.strip(formatted));
    }

    @Override
    public void dispose() {
        this.writer.flush();
        this.writer.close();
    }

}
