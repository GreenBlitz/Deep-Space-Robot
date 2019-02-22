package edu.greenblitz.robotname.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.greenblitz.robotname.OI;
import edu.greenblitz.robotname.RobotMap;
import edu.greenblitz.robotname.RobotMap.Elevator.Motor;
import edu.greenblitz.robotname.RobotMap.Elevator.Sensor;
import edu.greenblitz.robotname.RobotMap.Elevator.Solenoid;
import edu.greenblitz.robotname.commands.simple.elevator.BrakeElevator;
import edu.greenblitz.robotname.data.Report;
import edu.greenblitz.utils.encoder.IEncoder;
import edu.greenblitz.utils.encoder.TalonEncoder;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.logging.Logger;

import static edu.greenblitz.robotname.RobotMap.Elevator.Heights;

public class Elevator extends Subsystem {

    private static Logger logger = Logger.getLogger("elevator");

    public interface Level {
        double getHeight();

        String name();

        default boolean greaterThan(Level other) {
            return getHeight() >= getHeight();
        }

        enum Cargo implements Level {
            GROUND(Heights.Cargo.GROUND),
            COLLECT(Heights.Cargo.COLLECT),
            CRUISE(Heights.Cargo.CRUISE),
            SHIP(Heights.Cargo.SHIP),
            ROCKET_LOW(Heights.Cargo.ROCKET_LOW),
            ROCKET_MID(Heights.Cargo.ROCKET_MID),
            ROCKET_HIGH(Heights.Cargo.ROCKET_HIGH);

            private double m_height;

            Cargo(double height) {
                m_height = height;
            }

            @Override
            public double getHeight() {
                return m_height;
            }
        }

        enum Hatch implements Level {
            GROUND(Heights.Hatch.GROUND),
            CRUISE(Heights.Hatch.CRUISE),
            SHIP(Heights.Hatch.SHIP),
            COLLECT(Heights.Hatch.COLLECT),
            ROCKET_LOW(Heights.Hatch.ROCKET_LOW),
            ROCKET_MID(Heights.Hatch.ROCKET_MID),
            ROCKET_HIGH(Heights.Hatch.ROCKET_HIGH);

            private double m_height;

            Hatch(double height) {
                m_height = height;
            }

            @Override
            public double getHeight() {
                return m_height;
            }
        }
    }

    private static Elevator instance;

    private static final double LEVEL_HEIGHT_TOLERANCE = 0.05;

    private WPI_TalonSRX m_main, m_follower;
    private IEncoder m_encoder;
    private DoubleSolenoid m_braker;
    private DigitalInput m_infrared, m_limitSwitch;
    private Level m_level;

    private Elevator() {
        m_main = new WPI_TalonSRX(Motor.MAIN);
        m_follower = new WPI_TalonSRX(Motor.FOLLOWER);
        m_follower.follow(m_main);
        m_encoder = new TalonEncoder(Sensor.TICKS_PER_METER, m_main);
        m_braker = new DoubleSolenoid(Solenoid.FORWARD, Solenoid.REVERSE);
        m_infrared = new DigitalInput(RobotMap.Roller.Sensor.INFRARED);
        m_limitSwitch = new DigitalInput(RobotMap.Roller.Sensor.LIMIT_SWITCH);

        logger.info("instantiated");
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new BrakeElevator());
    }

    public static void init() {
        if (instance == null)
            instance = new Elevator();
    }

    public static Elevator getInstance() {
        return instance;
    }

    private void setLevel(Level level) {
        m_level = level;
    }

    public Level getLevel() {
        return m_level;
    }

    public boolean isFloorLevel() {
        return m_level.getHeight() == Level.Cargo.GROUND.getHeight() ||
                m_level.getHeight() == Level.Hatch.GROUND.getHeight();
    }

    public double getHeight() {
        return m_encoder.getNormalizedTicks();
    }

    private void setBrakeState(Value value) {
        if (m_braker.get() != value) {
            Report.pneumaticsUsed(getName());
            if (value == Value.kForward) {
                logger.fine("braking");
            } else {
                logger.fine("brake released");
            }
        }
        m_braker.set(value);
    }

    public void brake() {
        setBrakeState(Value.kForward);
    }

    public void releaseBrake() {
        setBrakeState(Value.kReverse);
    }

    public void setPower(double power) {
        m_main.set(power);
        logger.finest("power: " + power);
    }

    public void resetEncoder() {
        m_encoder.reset();

        logger.config("encoders reset");
    }

    public void reset() {
        resetEncoder();
    }

    public boolean isBallFullyIn() {
        return m_limitSwitch.get();
    }

    public boolean isBallIn() {
        return m_infrared.get();
    }

    private void updateLevel() {
        Level[] current = OI.getOIState() == OI.State.CARGO ? Level.Cargo.values() : Level.Hatch.values();
        for (Level level : current) {
            if (Math.abs(getHeight() - level.getHeight()) <= LEVEL_HEIGHT_TOLERANCE) {
                setLevel(level);
                logger.fine("level: " + getLevel());
                return;
            }
        }
    }

    public void update() {
        SmartDashboard.putString("Elevator::Command", getCurrentCommandName());
        SmartDashboard.putNumber("Elevator::Height", getHeight());
        SmartDashboard.putString("Elevator::Level", getLevel().name());
        updateLevel();
    }
}