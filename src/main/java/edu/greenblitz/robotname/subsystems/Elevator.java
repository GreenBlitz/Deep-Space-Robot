package edu.greenblitz.robotname.subsystems;

import edu.greenblitz.robotname.RobotMap;
import edu.greenblitz.robotname.RobotMap.Elevator.ElevatorLevel;
import edu.greenblitz.robotname.RobotMap.Elevator.Motor;
import edu.greenblitz.robotname.RobotMap.Elevator.Sensor;
import edu.greenblitz.robotname.RobotMap.Elevator.Solenoid;
import edu.greenblitz.robotname.commands.simple.elevator.BrakeElevator;
import edu.greenblitz.robotname.data.Report;
import edu.greenblitz.utils.Tuple;
import edu.greenblitz.utils.ctre.SmartTalon;
import edu.greenblitz.utils.encoder.IEncoder;
import edu.greenblitz.utils.encoder.TalonEncoder;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.List;
import java.util.logging.Logger;

public class Elevator extends Subsystem {

    private static Logger logger = Logger.getLogger("elevator");

    private static final double LEVEL_HEIGHT_RANGE = 0;
    private static final double SAFE_TO_LOWER_DOWN = 0.05,
            SAFE_TO_LOWER_UP = 0.4,
            SAFETY_RANGE = 0.05;

    private static final List<Tuple<Double, Double>> DANGER_ZONES = List.of(
            new Tuple<>(SAFE_TO_LOWER_DOWN - SAFETY_RANGE, SAFE_TO_LOWER_UP + SAFETY_RANGE)
    );

    private static Elevator instance;

    private ElevatorLevel m_level = ElevatorLevel.GROUND;

    private SmartTalon m_main, m_follower;
    private IEncoder m_encoder;
    private DoubleSolenoid m_braker;
    private DigitalInput m_infrared, m_limitSwitch;

    private Elevator() {
        m_main = new SmartTalon(Motor.Main);
        m_follower = new SmartTalon(Motor.Follower);
        m_follower.follow(m_main);
        m_encoder = new TalonEncoder(Sensor.TicksPerMeter, m_main);
        m_braker = new DoubleSolenoid(Solenoid.Forward, Solenoid.Reverse);
        m_infrared = new DigitalInput(RobotMap.Roller.Sensor.Infrared);
        m_limitSwitch = new DigitalInput(RobotMap.Roller.Sensor.LimitSwitch);

        logger.info("instantiated");
    }

    public boolean isInDangerZone() {
        for (Tuple<Double, Double> dangerZone : DANGER_ZONES) {
            if (getHeight() > dangerZone.first() && getHeight() < dangerZone.second())
                return true;
        }
        return false;
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
        if (instance == null)
            init();
        return instance;
    }

    private void setLevel(ElevatorLevel level) {
        m_level = level;
    }

    public ElevatorLevel getLevel() {
        return m_level;
    }

    public boolean isFloorLevel() { return m_level == ElevatorLevel.GROUND; }

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

    public boolean isBallFullyIn() {
        return m_limitSwitch.get();
    }

    public boolean isBallIn() {
        return m_infrared.get();
    }

    private void updateLevel() {
        for (ElevatorLevel level : ElevatorLevel.values()) {
            if (Math.abs(Elevator.getInstance().getHeight() - level.getHeight()) <= LEVEL_HEIGHT_RANGE) {
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