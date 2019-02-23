package edu.greenblitz.utils;

public class Logging {
    public static void init() {
        System.out.println("logging shit");
        System.setProperty("java.util.logging.config.file", "logging.properties");
    }
}
