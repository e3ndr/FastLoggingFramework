package xyz.e3ndr.fastloggingframework.logging;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.jetbrains.annotations.Nullable;

import lombok.NonNull;

public class LoggingUtil {

    public static String parseFormat(@Nullable Object object, @Nullable Object... args) {
        if ((args == null) || (args.length == 0)) {
            return String.valueOf(object);
        } else if (object instanceof Throwable) {
            return getExceptionStack((Throwable) object);
        } else {
            return String.format(String.valueOf(object), args);
        }
    }

    public static String getExceptionStack(@NonNull Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        e.printStackTrace(pw);

        String out = sw.toString();

        pw.close();
        pw.flush();
        sw.flush();

        return out.substring(0, out.length() - 2);
    }

}
