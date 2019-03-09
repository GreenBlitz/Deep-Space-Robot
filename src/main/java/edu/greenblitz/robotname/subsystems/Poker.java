package edu.greenblitz.robotname.subsystems;

import edu.greenblitz.robotname.Robot;
import edu.greenblitz.robotname.RobotMap.FrontPoker.Solenoid;
import edu.greenblitz.utils.command.GBSubsystem;
import edu.greenblitz.utils.sendables.SendableDoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;

public class Poker extends GBSubsystem {

    public static final long POKER_EXTEND_TIMEOUT = 1000;

    private static Poker instance;

    private SendableDoubleSolenoid m_holderPiston, m_extenderPiston;

    private String m_extenderPistonName = getName() + "::Extender";
    private String m_holderPistonName = getName() + "::Holder";

    private Logger logger;

    private Poker() {
        logger = LogManager.getLogger(getClass());

        m_holderPiston = new SendableDoubleSolenoid(Solenoid.Holder.PCM, Solenoid.Holder.FORWARD, Solenoid.Holder.REVERSE);
        m_extenderPiston = new SendableDoubleSolenoid(Solenoid.Extender.PCM, Solenoid.Extender.FORWARD, Solenoid.Extender.REVERSE);

//        addChild(m_holderPiston);
//        m_holderPiston.setName("holder");
//
//        addChild(m_extenderPiston);
//        m_extenderPiston.setName("extender");
//
        logger.info("instantiated");
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(null);
    }

    public static void init() {
        if (instance == null)
            instance = new Poker();
    }

    public static Poker getInstance() {
        return instance;
    }

    public void hold(boolean state) {
        var value = state ? Value.kReverse : Value.kForward;
        if (m_extenderPiston.get() != value) {
            Robot.getInstance().getReport().updatePneumaticsUsed(m_holderPistonName);
            logger.debug("holder state: {}", state ? "hold" : "released");
        }
        m_holderPiston.set(value);
    }

    public void extend(boolean state) {
        var value = valueFromState(state);
        if (getExtenderState() != value) {
            Robot.getInstance().getReport().updatePneumaticsUsed(m_extenderPistonName);
        }
        m_extenderPiston.set(value);
    }

    private Value valueFromState(boolean state) {
        return state ? kForward : kReverse;
    }

    private boolean stateFromValue(Value value) {
        switch (value) {
            case kForward: return true;
            case kReverse: return false;
            default: return false;
        }
    }

    public void extend() {
        extend(true);
    }

    public void retract() {
        extend(false);
    }

    public void hold() { hold(true); }

    public void release() { hold(false); }

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
        return !isExtended();
    }

    public boolean isExtended() {
        return stateFromValue(getExtenderState());
    }

    public boolean isHeld() {
        return getHolderState() == Value.kReverse;
    }

    public boolean isReleased() {
        return !isHeld();
    }

    public boolean isFullyClosed() {
        return isHeld() && isRetracted();
    }

    private String stringifyExtenderState(Value state) {
        switch (state) {
            case kForward: return "extended";
            case kReverse: return "retracted";
            case kOff:     return "unknown";
            default: logger.warn("Invalid extender state: '{}', returning '{}'", state, "unknown"); return "unknown";
        }
    }

    public Logger getLogger() {
        return logger;
    }

    public void reset() {
        retract();
        hold();
    }

    public void update() {
        SmartDashboard.putBoolean("Poker::IsHeld", isHeld());
    }
}