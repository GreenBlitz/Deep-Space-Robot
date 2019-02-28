package edu.greenblitz.robotname.subsystems;

import edu.greenblitz.robotname.Robot;
import edu.greenblitz.robotname.RobotMap.Roller.Solenoid;
import edu.greenblitz.utils.sendables.SendableDoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Roller extends Subsystem {
    private static final double ROLL_IN = 0;
    private static final double ROLL_OUT = 0;

    private static Roller instance;

    private SendableDoubleSolenoid m_piston;
    private SpeedController m_motor;
    private Logger logger;

    private Roller() {
        logger = LogManager.getLogger(getClass());

        m_piston = new SendableDoubleSolenoid(Solenoid.PCM, Solenoid.FORWARD, Solenoid.REVERSE);
//        m_motor = new CANSparkMax(Motor.ROLLER, CANSparkMaxLowLevel.MotorType.kBrushless);

        addChild(m_piston);
        m_piston.setName("ball centerer piston");
//        addChild(m_motor);

        logger.info("instantiated");
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addBooleanProperty("extender", this::isExtended, this::setExtender);
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

    private void setPower(double power) {
//        m_motor.set(power);
        throw new UnsupportedOperationException("roller motor isn't connected");
    }

    public void extend() {
        setExtender(true);
    }

    public void retract() {
        setExtender(false);
    }

    public void rollOut() {
        setPower(ROLL_OUT);
    }

    public void rollIn() {
        setPower(ROLL_IN);
    }

    public void stop() {
        setPower(0);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(null);
    }

    public static void init() {
        if (instance == null)
            instance = new Roller();
    }

    public static Roller getInstance() {
        return instance;
    }
}