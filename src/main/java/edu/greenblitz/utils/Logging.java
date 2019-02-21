package edu.greenblitz.utils;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class Logging {

    public static final String LOG_PATTERN = "%1$tH:%1$tM:%1$tS %1$tL [%4$s] {%2$s}: %5$s%6$s%n";
    public static final String FORMATTER_PROPERTY = "java.util.logging.SimpleFormatter.format";

    public static void init() {
        System.setProperty(FORMATTER_PROPERTY, LOG_PATTERN);
    }
}
