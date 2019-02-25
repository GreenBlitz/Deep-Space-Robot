package edu.greenblitz.robotname.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
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
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static edu.greenblitz.robotname.RobotMap.Elevator.Heights;

public class Elevator extends Subsystem {

    public interface Level {
        double getHeight();

        String name();

        OI.State state();

        default boolean greaterThan(Level other) {
            return getHeight() >= other.getHeight();
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

            @Override
            public OI.State state() {
                return OI.State.CARGO;
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

            @Override
            public OI.State state() {
                return OI.State.HATCH;
            }
        }
    }

    private static Elevator instance;

    private static final double LEVEL_HEIGHT_TOLERANCE = 0.05;

    public static final int MAGIC_LOOP_IDX = 0;
    public static final int POSITION_LOOP_IDX = 1;

    public static final int MAIN_PID_IDX = 0;
    public static final int AUXILIARY_PID_IDX = 1;

    private static final double TICKS_PER_METER = 0;

    private WPI_TalonSRX m_leader, m_follower;
    private IEncoder m_encoder;
    private DoubleSolenoid m_brake;
    private DigitalInput m_infrared, m_limitSwitch;
    private Level m_level;
    private Logger logger;

    private Elevator() {
        logger = LogManager.getLogger(getClass());

        m_leader = new WPI_TalonSRX(Motor.MAIN);
        m_follower = new WPI_TalonSRX(Motor.FOLLOWER);
        m_follower.follow(m_leader);
        m_encoder = new TalonEncoder(Sensor.TICKS_PER_METER, m_leader);
        m_brake = new DoubleSolenoid(Solenoid.FORWARD, Solenoid.REVERSE);
        m_infrared = new DigitalInput(RobotMap.Roller.Sensor.INFRARED);
        m_limitSwitch = new DigitalInput(RobotMap.Roller.Sensor.LIMIT_SWITCH);

        addChild(m_leader);
        addChild(m_brake);
        addChild(m_infrared);
        addChild(m_limitSwitch);

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
        logger.debug("current level: " + level);
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

    public void brake(boolean state) {
        var value = state ? Value.kForward : Value.kReverse;
        if (m_brake.get() != value) {
            Report.pneumaticsUsed(getName());
            if (state) {
                logger.debug("braking");
            } else {
                logger.debug("brake released");
            }
        }
        m_brake.set(value);
    }

    public void setRawPower(double power) {
        m_leader.set(power);
        logger.trace(power);
    }

    public void stop() {
        m_leader.stopMotor();
    }

    public void setMainLoopIdx(int loopIdx) {
        logger.debug("set main loop index to {}", loopIdx);
        m_leader.selectProfileSlot(loopIdx, MAIN_PID_IDX);
    }

    public void setAuxiliaryLoopIdx(int loopIdx) {
        logger.debug("set auxiliary loop index to {}", loopIdx);
        m_leader.selectProfileSlot(loopIdx, AUXILIARY_PID_IDX);
    }

    public void setPosition(double level) {
        m_leader.set(ControlMode.Position, TICKS_PER_METER * level);
    }

    public void setSmartPosition(double level) {
        m_leader.set(ControlMode.MotionMagic, TICKS_PER_METER * level);
    }

    public void resetEncoder() {
        m_encoder.reset();

        logger.debug("encoders reset");
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
                return;
            }
        }
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addDoubleProperty("height", this::getHeight, null);
        builder.addStringProperty("level", () -> getLevel().name(), null);
    }

    public void update() {
        updateLevel();
    }
}