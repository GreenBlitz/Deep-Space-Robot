package edu.greenblitz.robotname.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.robotname.RobotMap.Roller.Motor;
import edu.greenblitz.robotname.RobotMap.Roller.Solenoid;
import edu.greenblitz.robotname.data.Report;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Roller extends Subsystem {
    private static final double ROLL_IN = 0;
    private static final double ROLL_OUT = 0;

    private static Roller instance;

    private DoubleSolenoid m_piston;
    private SpeedController m_motor;
    private Logger logger;

    private Roller() {
        m_piston = new DoubleSolenoid(Solenoid.FORWARD, Solenoid.REVERSE);
        m_motor = new CANSparkMax(Motor.ROLLER, CANSparkMaxLowLevel.MotorType.kBrushless);
        logger = LogManager.getLogger();

        logger.info("instantiated");
    }

    private void setExtender(Value value) {
        if (m_piston.get() != value) {
            Report.pneumaticsUsed(getName());
            var state = value == Value.kForward ? "extended" : "retracted";
            logger.debug("state " + state);
        }
        m_piston.set(value);
    }

    public void extend() {
        setExtender(Value.kForward);
    }

    public void retract() {
        setExtender(Value.kReverse);
    }

    public Value getExtenderState() {
        return m_piston.get();
    }

    public boolean isExtended() { return getExtenderState() == Value.kForward; }

    public boolean isRetracted() { return getExtenderState() == Value.kReverse; }

    private void setPower(double power) {
        m_motor.set(power);
        logger.trace(power);
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

    public void update() {
        SmartDashboard.putString("ROLLER::Command", getCurrentCommandName());
        SmartDashboard.putString("ROLLER::EXTENDER", getExtenderState().name());
    }
}