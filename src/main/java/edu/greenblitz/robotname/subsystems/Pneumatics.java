package edu.greenblitz.robotname.subsystems;

import edu.greenblitz.utils.sensors.PressureSensor;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.greenblitz.robotname.RobotMap.Pneumatics.*;
import edu.greenblitz.robotname.commands.simple.pneumatics.ActivateCompressorBelow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.logging.Logger;

public class Pneumatics extends Subsystem {

    private static Logger logger = Logger.getLogger("pneumatics");

    private static Pneumatics instance;

    private PressureSensor m_pressureSensor;
    private Compressor m_compressor;
    private DigitalInput m_switch;

    private Pneumatics() {
        m_pressureSensor = new PressureSensor(Sensor.Pressure);
        m_compressor = new Compressor(PCM.Compressor);
        m_switch = new DigitalInput(Sensor.Switch);

        logger.info("instantiated");
    }

    public double getPressure() {
        return m_pressureSensor.getPressure();
    }

    public void setCompressor(boolean isActive) {
        if (isActive) {
            logger.fine("compressor is activated, at pressure: " + getPressure());
            m_compressor.start();
        } else {
            logger.fine("compressor is de-activated, at pressure: " + getPressure());
            m_compressor.stop();
        }
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
        setDefaultCommand(new ActivateCompressorBelow(80));
    }

    public void update() {
        SmartDashboard.putNumber("Pneumatics::Pressure", m_pressureSensor.getPressure());
        SmartDashboard.putBoolean("Pneumatics::Enabled", isEnabled());
        SmartDashboard.putBoolean("Pneumatics::LimitSwitch Status", isLimitOn());
    }
}