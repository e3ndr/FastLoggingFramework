package xyz.e3ndr.fastloggingframework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder.Redirect;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.stream.Collectors;

class WindowsUtil {
    private static final boolean IS_WINDOWS = System.getProperty("os.name", "").contains("Windows");

    private static boolean hasAlreadyTriedColoredConsole = false;

    static void tryEnableColoredConsole() throws IOException, InterruptedException {
        if (hasAlreadyTriedColoredConsole) return;

        hasAlreadyTriedColoredConsole = true;

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
