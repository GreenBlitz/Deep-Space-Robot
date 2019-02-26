package edu.greenblitz.robotname.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.robotname.OI;
import edu.greenblitz.robotname.RobotMap;
import edu.greenblitz.robotname.RobotMap.Chassis.Motor;
import edu.greenblitz.robotname.RobotMap.Chassis.Sensor;
import edu.greenblitz.robotname.commands.simple.chassis.driver.ArcadeDriveByJoystick;
import edu.greenblitz.robotname.commands.simple.chassis.driver.TankDriveByJoytick;
import edu.greenblitz.robotname.data.LocalizerRunner;
import edu.greenblitz.utils.SendableSparkMax;
import edu.greenblitz.utils.encoder.IEncoder;
import edu.greenblitz.utils.encoder.SparkEncoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.greenblitz.motion.app.Localizer;
import org.greenblitz.motion.base.Position;


public class Chassis extends Subsystem {
    private static Chassis instance;

    private Logger logger;

    private CANSparkMax m_leftFront, m_leftRear, m_rightFront, m_rightRear;
    private SendableSparkMax m_leftLeader, m_rightLeader;
    private IEncoder m_leftEncoder, m_rightEncoder;
    private AHRS m_navX;
    private LocalizerRunner m_localizer;

    private Chassis() {
        logger = LogManager.getLogger(getClass());

        m_leftFront = new CANSparkMax(Motor.Left.FOLLOWER1, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_leftLeader = new SendableSparkMax(Motor.Left.LEADER, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_leftRear = new CANSparkMax(Motor.Left.FOLLOWER2, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_rightFront = new CANSparkMax(Motor.Right.FOLLOWER1, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_rightLeader = new SendableSparkMax(Motor.Right.LEADER, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_rightRear = new CANSparkMax(Motor.Right.FOLLOWER2, CANSparkMaxLowLevel.MotorType.kBrushless);

        m_leftLeader.setInverted(true);

        m_leftFront.follow(m_leftLeader);
        m_leftRear.follow(m_leftLeader);

        m_rightFront.follow(m_rightLeader);
        m_rightRear.follow(m_rightLeader);

        m_leftEncoder = new SparkEncoder(Sensor.Encoder.TICKS_PER_METER_SPEED, m_leftLeader);
        m_rightEncoder = new SparkEncoder(Sensor.Encoder.TICKS_PER_METER_SPEED, m_rightLeader);
        //m_navX = new AHRS(Sensor.NAVX);

        m_localizer = new LocalizerRunner(RobotMap.Chassis.Data.WHEEL_BASE_RADIUS, m_leftEncoder, m_rightEncoder);
        m_localizer.disableGyro();

        addChild(m_leftLeader);
        m_leftLeader.setName("left");
        addChild(m_rightLeader);
        m_rightLeader.setName("right");
        //addChild(m_navX);

        logger.info("instantiated");
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addDoubleProperty("left velocity", this::getLeftVelocity, null);
        builder.addDoubleProperty("right velocity", this::getRightVelocity, null);
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
        //return m_navX.getYaw();
        return 0;
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
        m_localizer.reset();
    }

    public void resetNavx() {
        //m_navX.reset();
        //logger.debug("gyro reset");
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
        m_leftLeader.set(l);
        m_rightLeader.set(r);
        System.out.println(l + " " + r);
    }

    public void setRampingRate(double timeToTopSpeed){
        m_leftLeader.setOpenLoopRampRate(timeToTopSpeed);
        m_rightLeader.setOpenLoopRampRate(timeToTopSpeed);
    }

    public void startLoclizer(){
        m_localizer.start();
    }
}