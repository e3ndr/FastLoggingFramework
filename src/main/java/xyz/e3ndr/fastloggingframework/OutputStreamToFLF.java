package xyz.e3ndr.fastloggingframework;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

@RequiredArgsConstructor
public class OutputStreamToFLF extends OutputStream {
    private static final String[] FILTER = {
            "xyz.e3ndr.fastloggingframework",
            "java.",
            "javax.",
            "sun."
    };

    private final Map<String, StringBuilder> buildersByStackName = new HashMap<>();

    private final LogLevel level;

    @Override
    public void write(int b) throws IOException {
        String stackName = LogUtil.getCallerFromStack(FILTER);

        if (b == '\n' || b == '\r') {
            StringBuilder builder = this.buildersByStackName.remove(stackName);
            if (builder == null) return;

            String buffered = builder.toString();
            if (buffered.isEmpty()) return;

            FastLoggingFramework
                .getLogHandler()
                .log(
                    LogUtil.stackNameToClassName(stackName),
                    this.level,
                    buffered
                );
            return;
        }

        StringBuilder builder = this.buildersByStackName.get(stackName);
        if (builder == null) {
            builder = new StringBuilder();
            this.buildersByStackName.put(stackName, builder);
        }

        builder.append((char) b);
    }

}
