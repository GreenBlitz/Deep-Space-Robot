package edu.greenblitz.robotname.subsystems;

import edu.greenblitz.utils.sensors.PressureSensor;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.greenblitz.robotname.RobotMap.Pneumatics.*;
import edu.greenblitz.robotname.commands.simple.pneumatics.HandleCompressor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.logging.Logger;

public class Pneumatics extends Subsystem {

    private static Logger logger = Logger.getLogger("pneumatics");

    private static final double DEFAULT_DEADZONE = 10;
    private static final double DEFAULT_DUTY_CYCLE_PERCENT = 0.1;
    private static final double DEFAULT_MIN_PRESSURE_RELEASED = 40;
    private static final double DEFAULT_MAX_PRESSURE_RELEASED = 80;
    private static final double DEFAULT_CRITICAL_PRESSURE_HELD = 100;
    private static final double DEFAULT_FULL_DUTY_CYCLE = 60;

    private static final String SMD_DEADZONE = "Pneumatics::deadzone";
    private static final String SMD_DUTY_CYCLE_PERCENT = "Pneumatics::duty cycle %";
    private static final String SMD_MAX_PRESSURE_RELEASED = "Pneumatics::max pressure";
    private static final String SMD_MIN_PRESSURE_RELEASED = "Pneumatics::min pressure";
    private static final String SMD_CRITICAL_PRESSURE_HELD = "Pneumatics::critical pressure";
    private static final String SMD_FULL_DUTY_CYCLE = "Pneumatics::full duty cycle";

    private static Pneumatics instance;

    private PressureSensor m_pressureSensor;
    private Compressor m_compressor;
    private DigitalInput m_switch;

    private Pneumatics() {
        m_pressureSensor = new PressureSensor(Sensor.PRESSURE);
        m_compressor = new Compressor(PCM.COMPRESSOR);
        m_compressor.stop();
        m_switch = new DigitalInput(Sensor.SWITCH);

        logger.info("instantiated");
    }

    public double getPressure() {
        return m_pressureSensor.getPressure();
    }

    private void setCompressor(boolean isActive) {
        if (isActive) {
            logger.fine("compressor is activated, at pressure: " + getPressure());
            //m_compressor.start();
        } else {
            logger.fine("compressor is de-activated, at pressure: " + getPressure());
            m_compressor.stop();
        }
    }

    public void compress() {
        setCompressor(true);
    }

    public void stop() {
        setCompressor(false);
    }

    public boolean isEnabled() {
        return m_compressor.enabled();
    }

    public boolean isLimitOn() {
        return m_switch.get();
    }

    public static void init() {
        if (instance == null)
            instance = new Pneumatics();
    }

    public static Pneumatics getInstance() {
        if (instance == null)
            init();
        return instance;
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new HandleCompressor());
    }

    public double getDeadzone() {
        return SmartDashboard.getNumber(SMD_DEADZONE, DEFAULT_DEADZONE);
    }

    public double getDutyCyclePercent() {
        return SmartDashboard.getNumber(SMD_DUTY_CYCLE_PERCENT, DEFAULT_DUTY_CYCLE_PERCENT);
    }

    public double getMinPressureReleased() {
        return SmartDashboard.getNumber(SMD_MIN_PRESSURE_RELEASED, DEFAULT_MIN_PRESSURE_RELEASED);
    }

    public double getMaxPressureReleased() {
        return SmartDashboard.getNumber(SMD_MAX_PRESSURE_RELEASED, DEFAULT_MAX_PRESSURE_RELEASED);
    }

    public double getCriticalPressureHeld() {
        return SmartDashboard.getNumber(SMD_CRITICAL_PRESSURE_HELD, DEFAULT_CRITICAL_PRESSURE_HELD);
    }

    public double getFullDutyCycle() {
        return SmartDashboard.getNumber(SMD_FULL_DUTY_CYCLE, DEFAULT_FULL_DUTY_CYCLE);
    }

    public void update() {
        SmartDashboard.putNumber("Pneumatics::PRESSURE", m_pressureSensor.getPressure());
        SmartDashboard.putBoolean("Pneumatics::Enabled", isEnabled());
        SmartDashboard.putBoolean("Pneumatics::LIMIT_SWITCH Status", isLimitOn());
    }
}