package edu.greenblitz.knockdown.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.greenblitz.knockdown.OI;
import edu.greenblitz.knockdown.Robot;
import edu.greenblitz.knockdown.RobotMap.Elevator.Motor;
import edu.greenblitz.knockdown.RobotMap.Elevator.Sensor;
import edu.greenblitz.knockdown.RobotMap.Elevator.Solenoid;
import edu.greenblitz.knockdown.commands.simple.elevator.BrakeElevator;
import edu.greenblitz.utils.command.GBSubsystem;
import edu.greenblitz.utils.encoder.IEncoder;
import edu.greenblitz.utils.encoder.TalonEncoder;
import edu.greenblitz.utils.sendables.SendableDoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class Elevator extends GBSubsystem {
    private static final double CARGO_OFFSET = 0.165;
    private static final double HATCH_OFFSET = 0.48;

    public enum Level {
        GROUND(CARGO_OFFSET, HATCH_OFFSET),    // 0, 0
        ROCKET_LOW(0.7, HATCH_OFFSET),  // 0.7, 0
        CARGO_SHIP(1.05, HATCH_OFFSET), // 1.05, 0
        ROCKET_MID(1.41, 1.29),  // 1.41, 1.19
        ROCKET_HIGH(2.12, 2); // 2.12, 1.90

        public final double cargo;
        public final double hatch;

        Level(double cargo, double hatch) {
            this.cargo = cargo - CARGO_OFFSET;
            this.hatch = hatch - HATCH_OFFSET;
        }

        public double heightByState(OI.State state) {
            return state == OI.State.CARGO ? cargo : hatch;
        }

        public double heightByCurrentState() {
            return heightByState(OI.getState());
        }
    }

    private static final double MAX_VEL = 1.4547785108388314,
            MAX_ACC = 14.03314483191957,
            GRAVITY_FF = 0.1;

    private static Elevator instance;

    public static final double LEVEL_HEIGHT_TOLERANCE = 0.05;

    public static final double LOWER_TOLERANCE = 0.04;
    public static final double HIGHER_TOLERANCE = 1;

    public static final int MAGIC_LOOP_IDX = 0;
    public static final int POSITION_LOOP_IDX = 1;

    public static final int MAIN_PID_IDX = 0;
    public static final int AUXILIARY_PID_IDX = 1;

    private WPI_TalonSRX m_leader;
    private TalonSRX m_follower;
    private IEncoder m_encoder;
    private SendableDoubleSolenoid m_brake;
    private Level m_level = Level.GROUND;
    private Logger logger;

    private DoubleSolenoid.Value m_brakeValue = Value.kForward;

    private Elevator() {
        logger = LogManager.getLogger(getClass());

        m_leader = new WPI_TalonSRX(Motor.LEADER);
        m_follower = new TalonSRX(Motor.FOLLOWER);
        m_follower.follow(m_leader);

        m_leader.setSensorPhase(true);

        m_encoder = new TalonEncoder(Sensor.TICKS_PER_METER, m_leader);

        m_brake = new SendableDoubleSolenoid(Solenoid.PCM, Solenoid.FORWARD, Solenoid.REVERSE);

        addChild(m_leader);
        m_leader.setName("motor");
        addChild(m_brake);
        m_brake.setName("brake");

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

    public static double getCriticalHeight(){
        return Level.ROCKET_LOW.cargo - 0.15;
    }

    public static boolean isBelowCritical(double h){
        return h < getCriticalHeight();
    }

    public static Elevator getInstance() {
        return instance;
    }

    private void setLevel(Level level) {
        if (level != m_level) {
            m_level = level;
            logger.debug("current level: {}", level);
        }
    }

    public Level getLevel() {
        return m_level;
    }

    public boolean isFloorLevel() {
        return getHeight() < LEVEL_HEIGHT_TOLERANCE;
    }

    public double getVelocity() {
        return m_encoder.getNormalizedVelocity();
    }

    public double getHeight() {
        return m_encoder.getNormalizedTicks();
    }

    public void brake(boolean state) {
        var value = state ? Value.kForward : Value.kReverse;
        if (m_brake.get() != value) {
            Robot.getInstance().getReport().updatePneumaticsUsed(getName());
            if (state) {
                logger.debug("braking");
            } else {
                logger.debug("brake released");
            }
        }
        m_brakeValue = value;
    }

    public void setRawPower(double power) {
        m_leader.set(power);
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
        m_leader.set(
                ControlMode.Position, Sensor.TICKS_PER_METER * level,
                DemandType.ArbitraryFeedForward, GRAVITY_FF);
    }

    public void setSmartPosition(double level) {
        m_leader.set(
                ControlMode.MotionMagic, Sensor.TICKS_PER_METER * level,
                DemandType.ArbitraryFeedForward, GRAVITY_FF);
    }

    public void resetEncoder() {
        m_encoder.reset();

        logger.info("encoders reset");
    }

    public void reset() {
        resetEncoder();
    }

    public void setCompensatedPower(double power, double ff) {
        m_leader.set(ControlMode.PercentOutput, power, DemandType.ArbitraryFeedForward, ff);
    }

    private Optional<Level> updateLevel() {
        var state = OI.getState();
        for (var lvl : Level.values()) {
            if (Math.abs(lvl.heightByState(state) - getHeight()) <= LEVEL_HEIGHT_TOLERANCE) {
                return Optional.of(lvl);
            }
        }
        return Optional.empty();
    }

    public boolean isBraking() {
        return m_brake.get() == Value.kForward;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addDoubleProperty("power", m_leader::get, this::setRawPower);
        builder.addBooleanProperty("brake", this::isBraking, this::brake);
        builder.addDoubleProperty("Encoder", this::getHeight, null);
        builder.addBooleanProperty("ground limit switch", this::isFloorLevel, null);
        builder.addDoubleProperty("height", this::getHeight, null);
    }

    public void periodic() {
        SmartDashboard.putNumber("Elevator::power", m_leader.get());
        SmartDashboard.putNumber("Elevator::Height", getHeight());
        updateLevel().ifPresent(this::setLevel);
        if (m_brakeValue != m_brake.get())
            m_brake.set(m_brakeValue);
    }
}