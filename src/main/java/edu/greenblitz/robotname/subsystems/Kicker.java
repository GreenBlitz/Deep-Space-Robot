package edu.greenblitz.robotname.subsystems;

import edu.greenblitz.robotname.data.Report;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.greenblitz.robotname.RobotMap.Kicker.*;

import java.util.logging.Logger;

public class Kicker extends Subsystem {

    private static Logger logger = Logger.getLogger("kicker");

    private static Kicker instance;

    private DoubleSolenoid m_piston;

    private Kicker() {
        m_piston = new DoubleSolenoid(2, Solenoid.FORWARD, Solenoid.REVERSE);

        logger.info("instantiated");
    }

    @Override
    public void initDefaultCommand() {
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
            logger.fine("state: " + state);
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

    public void update() {
        SmartDashboard.putString("Kicker::Command", getCurrentCommandName());
        SmartDashboard.putString("Kicker::State", m_piston.get().name());
    }
}