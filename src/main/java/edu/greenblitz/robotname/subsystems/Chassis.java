package edu.greenblitz.robotname.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.robotname.OI;
import edu.greenblitz.robotname.RobotMap;
import edu.greenblitz.robotname.RobotMap.Chassis.Motor;
import edu.greenblitz.robotname.RobotMap.Chassis.Sensor;
import edu.greenblitz.robotname.commands.simple.chassis.driver.TankDriveByJoytick;
import edu.greenblitz.robotname.data.LocalizerRunner;
import edu.greenblitz.utils.encoder.IEncoder;
import edu.greenblitz.utils.encoder.SparkEncoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.greenblitz.motion.base.Position;


public class Chassis extends Subsystem {
    private static Chassis instance;

    private Logger logger;

    private CANSparkMax m_leftFront, m_leftLeader, m_leftRear, m_rightFront, m_rightLeader, m_rightRear;
    private IEncoder m_leftEncoder, m_rightEncoder;
    private AHRS m_navX;
    private LocalizerRunner m_localizer;

    private Chassis() {
        logger = LogManager.getLogger();

        m_leftFront = new CANSparkMax(Motor.Left.TOP, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_leftLeader = new CANSparkMax(Motor.Left.BOTTOM, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_leftRear = new CANSparkMax(Motor.Left.BACK, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_rightFront = new CANSparkMax(Motor.Right.TOP, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_rightLeader = new CANSparkMax(Motor.Right.BOTTOM, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_rightRear = new CANSparkMax(Motor.Right.BACK, CANSparkMaxLowLevel.MotorType.kBrushless);

        m_leftFront.follow(m_leftLeader);
        m_leftRear.follow(m_leftLeader);

        m_leftLeader.setInverted(true);

        m_rightFront.follow(m_rightLeader);
        m_rightRear.follow(m_rightLeader);

        m_leftEncoder = new SparkEncoder(Sensor.Encoder.TICKS_PER_METER, m_leftLeader);
        m_rightEncoder = new SparkEncoder(Sensor.Encoder.TICKS_PER_METER, m_rightLeader);
        m_navX = new AHRS(Sensor.NAVX);

        m_localizer = new LocalizerRunner(RobotMap.Chassis.Data.WHEEL_BASE_RADIUS, m_leftEncoder, m_rightEncoder);

        logger.info("instantiated");
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new TankDriveByJoytick(OI.getMainJoystick()));
    }

    public void arcadeDrive(double move, double rotate) {
        setLeftRightMotorOutput(move + rotate, move - rotate);
    }

    public void tankDrive(double left, double right) {
        setLeftRightMotorOutput(left, right);
    }

    public void stop() {
        tankDrive(0, 0);
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
        if (instance == null) instance = new Chassis();
    }

    public static Chassis getInstance() {
        return instance;
    }

    public void reset() {
        resetNavx();
        resetEncoders();
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

    public void update() {
        SmartDashboard.putString("Chassis::Command", getCurrentCommandName());
        SmartDashboard.putNumber("Chassis::LeftSpeed", m_leftEncoder.getNormalizedVelocity());
        SmartDashboard.putNumber("Chassis::RightSpeed", m_rightEncoder.getNormalizedVelocity());
        SmartDashboard.putNumber("Chassis::Speed", getVelocity());
        SmartDashboard.putNumber("Chassis::Distance", getDistance());
        SmartDashboard.putNumber("Chassis::Angle", getAngle());
        SmartDashboard.putNumberArray("Chassis::Location", getLocation().get());
    }

    public Position getLocation() {
        return m_localizer.getLocation();
    }

    public Logger getLogger() {
        return logger;
    }

    private void setLeftRightMotorOutput(double l, double r) {
        m_leftLeader.set(l);
        m_rightLeader.set(r);
    }
}