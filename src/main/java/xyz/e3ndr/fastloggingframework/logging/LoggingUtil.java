package xyz.e3ndr.fastloggingframework.logging;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.jetbrains.annotations.Nullable;

import lombok.NonNull;
import lombok.SneakyThrows;

public class LoggingUtil {

    public static String parseFormat(@Nullable Object object, @Nullable Object... args) {
        if (object == null) {
            return "null";
        } else if (object instanceof Throwable) {
            return getExceptionStack((Throwable) object);
        } else if ((args == null) || (args.length == 0)) {
            return String.valueOf(object);
        } else {
            return String.format(String.valueOf(object), args);
        }
    }

    @SneakyThrows
    public static Class<?> getCallingClass() {
        return Class.forName(Thread.currentThread().getStackTrace()[3].getClassName());
    }

    public static boolean classExists(String name) {
        try {
            Class.forName(name);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getExceptionStack(@NonNull Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        e.printStackTrace(pw);

        String out = sw.toString();

        pw.flush();
        pw.close();
        sw.flush();

        return out.substring(0, out.length() - 2);
    }

}
