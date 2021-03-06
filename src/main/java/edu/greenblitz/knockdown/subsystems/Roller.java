package edu.greenblitz.knockdown.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.greenblitz.knockdown.OI;
import edu.greenblitz.knockdown.Robot;
import edu.greenblitz.knockdown.RobotMap;
import edu.greenblitz.knockdown.RobotMap.Roller.Solenoid;
import edu.greenblitz.knockdown.commands.simple.roller.RollWithTriggers;
import edu.greenblitz.utils.command.GBSubsystem;
import edu.greenblitz.utils.sendables.SendableDoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Roller extends GBSubsystem {
    public static final long ROLLER_STATE_TIMEOUT = 1000;
    private static final double ROLL_IN = -0.7;
    private static final double ROLL_OUT = 0.7;

    private static Roller instance;

    private SendableDoubleSolenoid m_piston;
    private WPI_TalonSRX m_motor;
    private Logger logger;

    private Roller() {
        logger = LogManager.getLogger(getClass());

        m_piston = new SendableDoubleSolenoid(Solenoid.PCM, Solenoid.FORWARD, Solenoid.REVERSE);
        m_motor = new WPI_TalonSRX(RobotMap.Roller.Motor.ROLLER);

        addChild(m_piston);
        m_piston.setName("roller extender");
        addChild(m_motor);
        m_motor.setName("roller motor");

        logger.info("instantiated");
    }

    public void setExtender(boolean state) {
        var value = state ? Value.kForward : Value.kReverse;
        if (m_piston.get() != value) {
            Robot.getInstance().getReport().updatePneumaticsUsed(getName());
            logger.debug("state: {}", state ? "extended" : "retracted");
        }
        m_piston.set(value);
    }

    public Value getExtenderState() {
        return m_piston.get();
    }

    public boolean isExtended() { return getExtenderState() == Value.kForward; }

    public boolean isRetracted() { return getExtenderState() == Value.kReverse; }

    private void setRawPower(double power) {
        m_motor.set(power);
    }

    public void extend() {
        setExtender(true);
    }

    public void retract() {
        setExtender(false);
    }

    public void rollOut() {
        setRawPower(ROLL_OUT);
    }

    public void rollIn() {
        setRawPower(ROLL_IN);
    }

    public void stopRolling() {
        setRawPower(0);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new RollWithTriggers(OI.getSideJoystick()));
    }

    public static void init() {
        if (instance == null)
            instance = new Roller();
    }

    public static Roller getInstance() {
        return instance;
    }

    public void reset() {
        retract();
        stopRolling();
    }
}