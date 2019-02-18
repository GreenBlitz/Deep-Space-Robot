package edu.greenblitz.robotname.data;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.HashMap;
import java.util.Map;

/**
 * General robot data reporting class. Right now used only for counting solenoid usage and voltage at init.
 */
public class Report {

    private static final String PNEUMATICS = "Pneumatics";
    private static final String VOLTAGE    = "Starting Voltage";

    private static Report instance;

    public static synchronized void init() {
        if (instance == null) instance = new Report();
    }

    public static Report getInstance() {
        return instance;
    }

    public static void pneumaticsUsed(String name) {
        getInstance().reportPneumaticsUsed(name);
    }

    public static int getTotalPneumaticsUsage() {
        return instance.accumulate();
    }

    public static int getPneumaticsUsage(String name) {
        return instance.getPneumaticsUsed(name);
    }

    public static double getVoltageAtInit() {
        return instance.getStartingVoltage();
    }

    public static void voltageAtInit(double voltage) {
        instance.setStartingVoltage(voltage);
    }

    public static void reset() {
        instance.resetStartingVoltage();
        instance.resetPneumatics();
    }

    public static void toShuffleboard() {
        instance.initVoltageToShuffleboard();
        instance.pneumaticsToShuffleboard();
    }

    public static String getTotalReport() {
        var pneumatics = instance.getPneumaticsReport();
        var voltage = instance.getVoltageReport();
        var report = "Report:";
        return report + '\n' + voltage + '\n' + pneumatics;
    }

    @Deprecated
    public static void toDashboard() {
        toShuffleboard();
    }


    private Map<String, Integer> m_pneumatics = new HashMap<>();
    private double m_startingVoltage = Double.NaN;

    private Report() { }

    private void reportPneumaticsUsed(String name) {
        if (m_pneumatics.containsKey(name)) incPneumatics(name);
        else m_pneumatics.put(name, 0);
    }

    private void resetPneumatics() {
        m_pneumatics = new HashMap<>();
    }

    private void resetStartingVoltage() {
        m_startingVoltage = Double.NaN;
    }

    private int accumulate() {
        return m_pneumatics.values().stream().mapToInt(n -> n).sum();
    }

    private void incPneumatics(String name) {
        m_pneumatics.put(name, instance.m_pneumatics.get(name) + 1);
    }

    private int getPneumaticsUsed(String name) {
        return m_pneumatics.getOrDefault(name, 0);
    }

    private void setStartingVoltage(double voltage) {
        m_startingVoltage = voltage;
    }

    private double getStartingVoltage() {
        return m_startingVoltage;
    }

    private void pneumaticsToShuffleboard() {
        for (var key : m_pneumatics.keySet()) {
            SmartDashboard.putNumber(PNEUMATICS + "::" + key, getPneumaticsUsed(key));
        }
        SmartDashboard.putNumber(PNEUMATICS + "::Total", getTotalPneumaticsUsage());
    }

    private String getPneumaticsReport() {
        var sb = new StringBuilder();
        for (var key : m_pneumatics.keySet()) {
            sb.append('\t').append(key).append(": ").append(m_pneumatics.get(key)).append('\n');
        }
        sb.append('\t').append("total: ").append(getTotalPneumaticsUsage());
        return sb.toString();
    }

    private String getVoltageReport() {
        return String.format("\tvoltage at report init: %f", getVoltageAtInit());
    }

    private void initVoltageToShuffleboard() {
        SmartDashboard.putNumber(VOLTAGE, getStartingVoltage());
    }
}
