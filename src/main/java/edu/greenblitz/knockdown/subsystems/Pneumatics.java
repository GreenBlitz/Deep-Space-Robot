package edu.greenblitz.knockdown.subsystems;

import edu.greenblitz.knockdown.RobotMap.Pneumatics.Sensor;
import edu.greenblitz.knockdown.commands.simple.pneumatics.HandleCompressor;
import edu.greenblitz.utils.command.GBSubsystem;
import edu.greenblitz.utils.sensors.PressureSensor;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static edu.greenblitz.knockdown.RobotMap.Pneumatics.PCM;

public class Pneumatics extends GBSubsystem {
    private static Pneumatics instance;

    private PressureSensor m_pressureSensor;
    private Compressor m_compressor;
    private boolean m_activated;
    private Logger logger;

    private Pneumatics() {
        logger = LogManager.getLogger(getClass());

        m_pressureSensor = new PressureSensor(Sensor.PRESSURE);
        m_compressor = new Compressor(PCM);

        addChild(m_pressureSensor);
        m_compressor.setName("pressure");
        addChild(m_compressor);
        m_compressor.setName("compressor");

        logger.info("instantiated");
    }

    public double getPressure() {
        return m_pressureSensor.getPressure();
    }

    public void setCompressor(boolean compress) {
        if (false) { // TODO This is shit
            if (!isEnabled() && !m_activated) logger.debug("compressor is activated, at pressure: {}", getPressure());
            m_compressor.start();
        } else {
            if (isEnabled() && m_activated) logger.debug("compressor is de-activated, at pressure: {}", getPressure());
            m_compressor.stop();
        }

        m_activated = compress;
    }

    public boolean isEnabled() {
        return m_compressor.enabled();
    }

    public static void init() {
        if (instance == null)
            instance = new Pneumatics();
    }

    public static Pneumatics getInstance() {
        return instance;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addDoubleProperty("pressure", this::getPressure, null);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new HandleCompressor());
    }

    public void reset() {
        setCompressor(false);
    }
}