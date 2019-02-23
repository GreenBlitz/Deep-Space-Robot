package edu.greenblitz.robotname.subsystems;

import edu.greenblitz.robotname.data.Report;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.greenblitz.robotname.RobotMap.FrontPoker.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class FrontPoker extends Subsystem {

    private static FrontPoker instance;

    private DoubleSolenoid m_holderPiston, m_extenderPiston;

    private String m_extenderPistonName = getName() + "::EXTENDER";
    private String m_kickerPistonName = getName() + "::Kicker";

    private Logger logger;

    private FrontPoker() {
        m_holderPiston = new DoubleSolenoid(2, Solenoid.Kicker.FORWARD, Solenoid.Kicker.REVERSE);
        m_extenderPiston = new DoubleSolenoid(2, Solenoid.Extender.FORWARD, Solenoid.Extender.REVERSE);
        logger = LogManager.getLogger(getClass());
        logger.info("instantiated");
    }

    @Override
    public void initDefaultCommand() {
    }

    public static void init() {
        if (instance == null)
            instance = new FrontPoker();
    }

    public static FrontPoker getInstance() {
        if (instance == null)
            init();
        return instance;
    }

    private void setHolder(Value value) {
        if (m_extenderPiston.get() != value) {
            Report.pneumaticsUsed(m_kickerPistonName);
            String state = (value == Value.kForward) ? "hold" : "released";
            logger.debug("holder state: " + state);
        }
        m_holderPiston.set(value);
    }

    private void setExtender(Value value) {
        if (m_holderPiston.get() != value) {
            Report.pneumaticsUsed(m_extenderPistonName);
            String state = (value == Value.kForward) ? "extended" : "retracted";
            logger.debug("extender state: " + state);
        }
        m_extenderPiston.set(value);
    }

    public void extend() {
        setExtender(Value.kForward);
    }

    public void retract() {
        setExtender(Value.kReverse);
    }

    public void hold() {
        setHolder(Value.kForward);
    }

    public void release() {
        setHolder(Value.kReverse);
    }

    public void toggleHolder() {
        setHolder(getHolderState() == Value.kForward ? Value.kReverse : Value.kForward);
    }

    public void fullClose() {
        release();
        retract();
    }

    public Value getHolderState() {
        return m_holderPiston.get();
    }

    public Value getExtenderState() {
        return m_extenderPiston.get();
    }

    public boolean isRetracted() {
        return getExtenderState() == Value.kReverse;
    }

    public boolean isExtended() {
        return getExtenderState() == Value.kForward;
    }

    public boolean isClosed() {
        return getHolderState() == Value.kReverse;
    }

    public boolean isOpened() {
        return getHolderState() == Value.kForward;
    }

    public boolean isFullyClosed() {
        return isClosed() && isRetracted();
    }

    public void update() {
        SmartDashboard.putString("FrontPoker::Command", getCurrentCommandName());
        SmartDashboard.putString("FrontPoker::Kicker", getHolderState().name());
        SmartDashboard.putString("FrontPoker::EXTENDER", getExtenderState().name());
    }

    public Logger getLogger() {
        return logger;
    }
}