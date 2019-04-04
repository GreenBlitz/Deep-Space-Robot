package edu.greenblitz.knockdown.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.knockdown.OI;
import edu.greenblitz.knockdown.RobotMap;
import edu.greenblitz.knockdown.RobotMap.Chassis.Motor;
import edu.greenblitz.knockdown.RobotMap.Chassis.Sensor;
import edu.greenblitz.knockdown.commands.simple.chassis.driver.ArcadeDriveByJoystick;
import edu.greenblitz.knockdown.data.LocalizerRunner;
import edu.greenblitz.knockdown.data.vision.VisionMaster;
import edu.greenblitz.utils.command.GBSubsystem;
import edu.greenblitz.utils.encoder.IEncoder;
import edu.greenblitz.utils.encoder.SparkEncoder;
import edu.greenblitz.utils.sendables.SendableSparkMax;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.greenblitz.motion.app.Localizer;
import org.greenblitz.motion.base.Position;

public class Chassis extends GBSubsystem {
    private static final double DEADZONE = 0.03;
    private static final double GAMMA = 1;
    private static final double MULTIPLIER = 1;

    private static double deadzone(double power, double deadzone) {
        if (Math.abs(power) < deadzone) return 0;
        return (Math.abs(power) - deadzone) / (1 - deadzone) * Math.signum(power);
    }

    private static double deadzone(double power) {
        return deadzone(power, DEADZONE);
    }

    private static Chassis instance;

    private Logger logger;

    private CANSparkMax m_leftFollower1, m_leftFollower2, m_rightFollower1, m_rightFollower2;
    private SendableSparkMax m_leftLeader, m_rightLeader;
    private IEncoder m_leftEncoder, m_rightEncoder;
    private AHRS m_navX;
    private LocalizerRunner m_localizer;

    private Chassis() {
        logger = LogManager.getLogger(getClass());

        m_leftFollower1 = new CANSparkMax(Motor.Left.FOLLOWER1, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_leftLeader = new SendableSparkMax(Motor.Left.LEADER, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_leftFollower2 = new CANSparkMax(Motor.Left.FOLLOWER2, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_rightFollower1 = new CANSparkMax(Motor.Right.FOLLOWER1, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_rightLeader = new SendableSparkMax(Motor.Right.LEADER, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_rightFollower2 = new CANSparkMax(Motor.Right.FOLLOWER2, CANSparkMaxLowLevel.MotorType.kBrushless);

        m_leftLeader.setInverted(true);

        toBrake();
        currentLimit(40);

        m_leftFollower1.follow(m_leftLeader);
        m_leftFollower2.follow(m_leftLeader);

        m_rightFollower1.follow(m_rightLeader);
        m_rightFollower2.follow(m_rightLeader);

        m_leftEncoder = new SparkEncoder(Sensor.Encoder.TICKS_PER_METER_SPEED, m_leftLeader);
        m_rightEncoder = new SparkEncoder(Sensor.Encoder.TICKS_PER_METER_SPEED, m_rightLeader);
        m_navX = new AHRS(Sensor.NAVX);
        m_navX.enableLogging(false);

        addChild(m_leftLeader);
        m_leftLeader.setName("left");
        addChild(m_rightLeader);
        m_rightLeader.setName("right");
        addChild(m_navX);
        m_navX.setName("gyro");

        logger.info("instantiated");

    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new ArcadeDriveByJoystick(OI.getMainJoystick()));
    }

    public void arcadeDrive(double move, double rotate) {
        setLeftRightMotorOutput(move + rotate, move - rotate);
    }

    public void tankDrive(double left, double right) {
        setLeftRightMotorOutput(left, right);
    }

    public AHRS getNavx() {
        return m_navX;
    }

    public void stop() {
        m_leftLeader.stopMotor();
        m_rightLeader.stopMotor();
    }

    public double getDistance() {
        return (getLeftDistance() + getRightDistance()) / 2;
    }

    public double getVelocity() {
        return (getLeftVelocity() + getRightVelocity()) / 2;
    }

    public double getLeftDistance() {
        return m_leftEncoder.getNormalizedTicks();
    }

    public double getRightDistance() {
        return m_rightEncoder.getNormalizedTicks();
    }

    public double getLeftVelocity() {
        return m_leftEncoder.getNormalizedVelocity();
    }

    public double getRightVelocity() {
        return m_rightEncoder.getNormalizedVelocity();
    }

    public double getAngle() {
        return m_navX.getYaw();
    }

    public static void init() {
        if (instance == null) {
            instance = new Chassis();

            instance.m_localizer = new LocalizerRunner(RobotMap.Chassis.Data.WHEEL_BASE_RADIUS, instance.m_leftEncoder, instance.m_rightEncoder);
            instance.m_localizer.enableGyro();
            instance.m_localizer.start();
        }
    }

    public static Chassis getInstance() {
        return instance;
    }

    public void reset() {
        m_localizer.stop();
        resetNavx();
        resetEncoders();
        m_localizer.reset();
        m_localizer.start();
    }

    public void resetNavx() {
        m_navX.reset();
        logger.debug("gyro reset");
    }

    public void resetEncoders() {
        m_rightEncoder.reset();
        m_leftEncoder.reset();
        logger.debug("encoders reset");
    }

    public Position getLocation() {
        return m_localizer.getLocation();
    }

    public Logger getLogger() {
        return logger;
    }

    private void setLeftRightMotorOutput(double l, double r) {
        l = deadzone(l);
        r = deadzone(r);
        SmartDashboard.putNumber("raw left", l);
        SmartDashboard.putNumber("raw right", r);
        m_leftLeader.set(l);
        m_rightLeader.set(r);
    }

    public void setRampRate(double timeToTopSpeed) {
        m_leftLeader.setOpenLoopRampRate(timeToTopSpeed);
        m_rightLeader.setOpenLoopRampRate(timeToTopSpeed);
    }

    public void setLocation(Position location) {
        m_localizer.setGyroZero(location.getAngle());
        m_localizer.forceSetLocation(location, getLeftDistance(), getRightDistance());
    }

    private final int MAX_GYRO_DISCONNECTIONS = 10;
    private boolean gyroDied = false;
    private int gyroDisconnections = 0;

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Chassis::Distance", getDistance());

        SmartDashboard.putNumber("Chssis::Left Raw Ticks", m_leftEncoder.getRawTicks());
        SmartDashboard.putNumber("Chssis::Right Raw Ticks", m_rightEncoder.getRawTicks());

        SmartDashboard.putNumber("Chassis::Pitch", m_navX.getPitch());

        SmartDashboard.putNumber("Chassis::RPM", 60 * getVelocity() / 0.47);
        SmartDashboard.putNumber("Chassis::Left RPM", 60 * getLeftVelocity() / 0.47);
        SmartDashboard.putNumber("Chassis::Right RPM", 60 * getRightVelocity() / 0.47);

        SmartDashboard.putString("Chassis::Location", getLocation().toString());

    }

    public void update(){
        if (gyroDisconnections < MAX_GYRO_DISCONNECTIONS) {
            if (!gyroDied && !m_navX.isConnected()) {
                logger.warn("NAVX NOT CONNECTED");
                VisionMaster.getInstance().reportError(true, "NAVX not connected");
                gyroDied = true;
                m_localizer.disableGyro();
            } else if (gyroDied && m_navX.isConnected()) {
                gyroDisconnections++;
                if (gyroDisconnections < MAX_GYRO_DISCONNECTIONS) {
                    VisionMaster.getInstance().reportError(false, "NAVX not connected");
                    logger.warn("NAVX CONNECTED BACK");
                    m_localizer.enableGyro();
                    gyroDied = false;
                } else {
                    logger.warn("GYRO DISCONNECTED TOO MANY TIMES");
                    m_localizer.disableGyro();
                }
            }
        }
    }

    public void startLoclizer() {
        m_localizer.start();
    }

    public double getDownAcceleration() {
        return m_navX.getWorldLinearAccelY();
    }

    public void setTickPerMeter(Shifter.Gear gear) {
        double ticks = gear == Shifter.Gear.POWER ? Sensor.Encoder.TICKS_PER_METER_POWER : Sensor.Encoder.TICKS_PER_METER_SPEED;
        logger.debug("Switched to {}", gear);
        Localizer.getInstance().setSleep(20, 0, 0);
        m_rightEncoder.setNormalizeConst(ticks);
        m_leftEncoder.setNormalizeConst(ticks);
    }

    public Position getGyroPosition() {
        return new Position(m_navX.getDisplacementX(), m_navX.getDisplacementZ(), m_navX.getYaw());
    }

    public void setNeutralState(CANSparkMax.IdleMode state) {
        m_leftLeader.setIdleMode(state);
        m_leftFollower1.setIdleMode(state);
        m_leftFollower2.setIdleMode(state);

        m_rightLeader.setIdleMode(state);
        m_rightFollower1.setIdleMode(state);
        m_rightFollower2.setIdleMode(state);
    }

    public double getGyroZero() {
        return m_localizer.getGyroZero();
    }

    public void toCoast() {
        setNeutralState(CANSparkMax.IdleMode.kCoast);
    }

    public void toBrake() {
        setNeutralState(CANSparkMax.IdleMode.kBrake);
    }

    public void currentLimit(int a) {
        m_leftLeader.setSmartCurrentLimit(a);
        m_leftFollower2.setSmartCurrentLimit(a);
        m_leftFollower1.setSmartCurrentLimit(a);

        m_rightLeader.setSmartCurrentLimit(a);
        m_rightFollower2.setSmartCurrentLimit(a);
        m_rightFollower1.setSmartCurrentLimit(a);
    }

    private double gamma(double power) {
        return Math.pow(Math.abs(power), GAMMA) * MULTIPLIER * Math.signum(power);
    }
}