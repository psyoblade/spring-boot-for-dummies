package me.suhyuk.spring.admin.common;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtils {
    public static String printStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
