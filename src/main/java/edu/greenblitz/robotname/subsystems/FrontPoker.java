package edu.greenblitz.robotname.subsystems;

import edu.greenblitz.robotname.data.Report;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.greenblitz.robotname.RobotMap.FrontPoker.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class FrontPoker extends Subsystem {

    private static FrontPoker instance;

    private DoubleSolenoid m_holderPiston, m_extenderPiston;

    private String m_extenderPistonName = getName() + "::EXTENDER";
    private String m_kickerPistonName = getName() + "::Holder";

    private Logger logger;

    private FrontPoker() {
        logger = LogManager.getLogger(getClass());

        m_holderPiston = new DoubleSolenoid(Solenoid.PCM, Solenoid.Holder.FORWARD, Solenoid.Holder.REVERSE);
        m_extenderPiston = new DoubleSolenoid(Solenoid.PCM, Solenoid.Extender.FORWARD, Solenoid.Extender.REVERSE);

        addChild(m_holderPiston);
        addChild(m_extenderPiston);

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
        if (instance == null)
            init();
        return instance;
    }

    public void hold(boolean state) {
        var value = state ? Value.kReverse : Value.kForward;
        if (m_extenderPiston.get() != value) {
            Report.pneumaticsUsed(m_kickerPistonName);
            logger.debug("holder state: {}", state ? "hold" : "released");
        }
        m_holderPiston.set(value);
    }

    public void extend(boolean state) {
        var value = state ? Value.kForward : Value.kReverse;
        if (m_holderPiston.get() != value) {
            Report.pneumaticsUsed(m_extenderPistonName);
            logger.debug("extender state: {}", state ? "extended" : "retracted");
        }
        logger.debug("raw extender value: {}", value);
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
        return !isRetracted();
    }

    public boolean isHeld() {
        return getHolderState() != Value.kReverse;
    }

    public boolean isReleased() {
        return !isHeld();
    }

    public boolean isFullyClosed() {
        return isHeld() && isRetracted();
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addStringProperty("kicker", () -> getHolderState().name(), null);
        builder.addStringProperty("extender", () -> getExtenderState().name(), null);
    }

    public Logger getLogger() {
        return logger;
    }
}