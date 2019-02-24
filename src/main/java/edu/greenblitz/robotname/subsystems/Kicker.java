package edu.greenblitz.robotname.subsystems;

import edu.greenblitz.robotname.data.Report;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.greenblitz.robotname.RobotMap.Kicker.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Kicker extends Subsystem {

    private static Kicker instance;

    private DoubleSolenoid m_piston;
    private Logger logger;

    private Kicker() {
        logger = LogManager.getLogger(getClass());

        m_piston = new DoubleSolenoid(2, Solenoid.FORWARD, Solenoid.REVERSE);

        addChild(m_piston);

        logger.info("instantiated");
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addBooleanProperty("state", this::isOpen, null);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(null);
    }

    public static void init() {
        if (instance == null)
            instance = new Kicker();
    }

    public static Kicker getInstance() {
        if (instance == null)
            init();
        return instance;
    }

    private void setState(Value value) {
        if (m_piston.get() != value) {
            Report.pneumaticsUsed(getName());
            var state = (value == Value.kForward) ? "kicking" : "waiting";
            logger.debug("state: " + state);
        }
        m_piston.set(value);
    }

    public void kick() {
        setState(Value.kForward);
    }

    public void unkick() {
        setState(Value.kReverse);
    }

    public Value getState() {
        return m_piston.get();
    }

    public boolean isOpen() {
        return getState() == Value.kForward;
    }

    public boolean isClosed() {
        return getState() == Value.kReverse;
    }

    public Logger getLogger() {
        return logger;
    }
}