package xyz.e3ndr.fastloggingframework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder.Redirect;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import xyz.e3ndr.fastloggingframework.loggerimpl.QuietLogHandler;
import xyz.e3ndr.fastloggingframework.loggerimpl.StaticLogHandler;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

public class FastLoggingFramework {
    private static @Getter @Setter @NonNull LogLevel defaultLevel = LogLevel.valueOf(System.getProperty("fastloggingframework.defaultlevel", "INFO"));
    private static @Getter @NonNull FastLogHandler logHandler;

    private static @Getter @Setter boolean colorEnabled = System.getProperty("fastloggingframework.colorenabled", "true").equalsIgnoreCase("true");

    static {
        if (System.getProperty("os.name", "").contains("Windows")) {
            try {
                setupWindows();
            } catch (IOException | InterruptedException ignored) {}
        }

        if ("true".equalsIgnoreCase(System.getProperty("fastloggingframework.bequiet"))) {
            logHandler = new QuietLogHandler();
        } else {
            logHandler = new StaticLogHandler();
        }
    }

    public static void setLogHandler(@NonNull FastLogHandler newHandler) {
        logHandler = newHandler;
    }

    private static void setupWindows() throws IOException, InterruptedException {
        String script = new BufferedReader(
            new InputStreamReader(
                FastLoggingFramework.class.getResourceAsStream("/xyz/e3ndr/fastloggingframework/windows_setup.ps1")
            )
        )
            .lines()
            .collect(Collectors.joining("\n"));
        script = String.format("& {\n%s\n}", script);

        String encodedScript = Base64.getEncoder()
            .encodeToString(script.getBytes(StandardCharsets.UTF_16LE));

        ProcessBuilder builder = new ProcessBuilder()
            .command(
                "powershell.exe",
                "-NoProfile",
                "-NonInteractive",
                "-EncodedCommand", encodedScript
            )
            .inheritIO()
            .redirectError(Redirect.PIPE);

        /*int ret = */builder.start().waitFor();
//        if (ret != 0) {
//            throw new IOException(String.format("Error running windows setup script (code: %d)", ret));
//        }
    }

}
