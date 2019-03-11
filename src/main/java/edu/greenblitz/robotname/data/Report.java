package edu.greenblitz.robotname.data;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * General robot data reporting class. Right now used only for counting solenoid usage and voltage at initChain.
 */
public class Report {

    private static final String PNEUMATICS = "Pneumatics";
    private static final String VOLTAGE = "Starting Voltage";
    private static final Logger logger = LogManager.getLogger(Report.class);

    private Map<String, Integer> m_pneumatics = new HashMap<>();
    private double m_startingVoltage = Double.NaN;

    public void reset() {
        resetStartingVoltage();
        resetPneumatics();
    }

    public void toShuffleboard() {
        initVoltageToShuffleboard();
        pneumaticsToShuffleboard();
    }

    public String getTotalReport() {
        var pneumatics = getPneumaticsReport();
        var voltage = getVoltageReport();
        var report = "Report:";
        return report + '\n' + voltage + '\n' + pneumatics;
    }

    public void setVoltageAtInit(double voltage) {
        m_startingVoltage = voltage;
    }

    public double getVoltageAtInit() {
        return m_startingVoltage;
    }

    public boolean isReportValid() {
        return !Double.isNaN(getStartingVoltage());
    }

    /**
     * @deprecated SmartDashboard is deprecated. BIG F.
     */
    @Deprecated
    public void toDashboard() {
        toShuffleboard();
    }

    public void updatePneumaticsUsed(String name) {
        if (m_pneumatics.containsKey(name)) {
            incPneumatics(name);
        } else {
            m_pneumatics.put(name, 1);
        }
    }

    private void resetPneumatics() {
        m_pneumatics = new HashMap<>();
    }

    private void resetStartingVoltage() {
        m_startingVoltage = Double.NaN;
    }

    private int accumulate() {
        // the map is necessary is for sum()
        return m_pneumatics.values().stream().mapToInt(n -> n).sum();
    }

    private void incPneumatics(String name) {
        m_pneumatics.put(name, m_pneumatics.get(name) + 1);
    }

    private int getPneumaticsUsed(String name) {
        return m_pneumatics.getOrDefault(name, 0);
    }

    private void setStartingVoltage(double voltage) {
        m_startingVoltage = voltage;
        logger.debug("voltage reported: {}", voltage);
    }

    private double getStartingVoltage() {
        return m_startingVoltage;
    }

    private void pneumaticsToShuffleboard() {
        for (var entry : m_pneumatics.entrySet()) {
            SmartDashboard.putNumber(PNEUMATICS + "::" + entry.getKey(), entry.getValue());
        }
        SmartDashboard.putNumber(PNEUMATICS + "::Total", accumulate());
    }

    private String getPneumaticsReport() {
        var sb = new StringBuilder();
        for (var key : m_pneumatics.keySet()) {
            sb.append('\t').append(key).append(": ").append(m_pneumatics.get(key)).append('\n');
        }
        sb.append('\t').append("total pneumatics usage: ").append(accumulate());
        return sb.toString();
    }

    private String getVoltageReport() {
        return String.format("\tvoltage at init: %f", getVoltageAtInit());
    }

    private void initVoltageToShuffleboard() {
        SmartDashboard.putNumber(VOLTAGE, getStartingVoltage());
    }
}
