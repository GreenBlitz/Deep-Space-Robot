package edu.greenblitz.robotname.subsystems;

import edu.greenblitz.robotname.data.Report;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.greenblitz.robotname.RobotMap.FrontPoker.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static edu.greenblitz.robotname.RobotMap.FrontPoker.Solenoid.PCM;


public class FrontPoker extends Subsystem {

    private static FrontPoker instance;

    private DoubleSolenoid m_holderPiston, m_extenderPiston;

    private String m_extenderPistonName = getName() + "::Extender";
    private String m_holderPistonName = getName() + "::Holder";

    private Logger logger;

    private FrontPoker() {
        logger = LogManager.getLogger(getClass());

        m_holderPiston = new DoubleSolenoid(PCM, Solenoid.Holder.FORWARD, Solenoid.Holder.REVERSE);
        m_extenderPiston = new DoubleSolenoid(PCM, Solenoid.Extender.FORWARD, Solenoid.Extender.REVERSE);

        addChild(m_holderPiston);
        m_holderPiston.setName("holder");

        addChild(m_extenderPiston);
        m_holderPiston.setName("extender");

        logger.info("instantiated");
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(null);
    }

    public static void init() {
        if (instance == null)
            instance = new FrontPoker();
    }

    public static FrontPoker getInstance() {
        return instance;
    }

    public void hold(boolean state) {
        var value = state ? Value.kForward : Value.kReverse;
        if (m_holderPiston.get() != value) {
            Report.pneumaticsUsed(m_holderPistonName);
            logger.debug("holder state: {}", (value == Value.kForward) ? "hold" : "released");
        }
        m_holderPiston.set(value);
    }

    public void extend(boolean state) {
        var value = state ? Value.kForward : Value.kReverse;
        if (m_extenderPiston.get() != value) {
            Report.pneumaticsUsed(m_extenderPistonName);
            logger.debug("extender state: {}", (value == Value.kForward) ? "extended" : "retracted");
        }
        m_extenderPiston.set(value);
    }

    public void fullClose() {
        hold(false);
        extend(false);
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

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addStringProperty("holder state name", () -> getHolderState().name(), null);
//        builder.addStringProperty("extender", () -> getExtenderState().name(), null);
    }

    public Logger getLogger() {
        return logger;
    }
}