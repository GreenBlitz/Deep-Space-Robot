package edu.greenblitz.robotname.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.robotname.OI;
import edu.greenblitz.robotname.RobotMap.Chassis.Motor;
import edu.greenblitz.robotname.RobotMap.Chassis.Sensor;
import edu.greenblitz.robotname.commands.simple.chassis.ArcadeDriveByJoystick;
import edu.greenblitz.utils.drive.RobotDrive;
import edu.greenblitz.utils.encoder.IEncoder;
import edu.greenblitz.utils.encoder.SparkEncoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Chassis extends Subsystem {

    private static Chassis instance;

    private RobotDrive m_robotDrive;
    private IEncoder m_leftEncoder, m_rightEncoder;
    private AHRS m_navX;

    private Chassis() {
        var leftFront = new CANSparkMax(Motor.Left.Front, CANSparkMaxLowLevel.MotorType.kBrushless);
        var leftMiddle = new CANSparkMax(Motor.Left.Middle, CANSparkMaxLowLevel.MotorType.kBrushless);
        var leftRear = new CANSparkMax(Motor.Left.Rear, CANSparkMaxLowLevel.MotorType.kBrushless);
        var rightFront = new CANSparkMax(Motor.Right.Front, CANSparkMaxLowLevel.MotorType.kBrushless);
        var rightMiddle = new CANSparkMax(Motor.Right.Middle, CANSparkMaxLowLevel.MotorType.kBrushless);
        var rightRear = new CANSparkMax(Motor.Right.Rear, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_robotDrive = new RobotDrive(leftFront, leftMiddle, leftRear, rightFront, rightMiddle, rightRear);
        m_leftEncoder = new SparkEncoder(Sensor.Encoder.TicksPerMeter, m_robotDrive.getMotor(Sensor.Encoder.Left));
        m_rightEncoder = new SparkEncoder(Sensor.Encoder.TicksPerMeter, m_robotDrive.getMotor(Sensor.Encoder.Right));
        m_navX = new AHRS(Sensor.NavX);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new ArcadeDriveByJoystick(OI.getMainJoystick()));
    }

    public void arcadeDrive(double move, double rotate) {
        m_robotDrive.arcadeDrive(move, rotate);
    }

    public void tankDrive(double left, double right) {
        m_robotDrive.tankDrive(left, right);
    }

    public void stop() {
        m_robotDrive.tankDrive(0, 0);
    }

    public double getDistance() {
        return (getLeftDistance() + getRightDistance()) / 2;
    }

    public double getSpeed() {
        return (getLeftVelocity() + getRightVelocity()) / 2;
    }

    public double getLeftDistance() {
        return m_leftEncoder.getNormalizedTicks();
    }

    public double getRightDistance() {
        return m_rightEncoder.getNormalizedTicks();
    }

    public double getLeftVelocity() {
        return m_leftEncoder.getNormalziedVelocity();
    }

    public double getRightVelocity() {
        return m_rightEncoder.getNormalziedVelocity();
    }

    public double getAngle() {
        return m_navX.getYaw();
    }

    public static void init() {
        if (instance == null)
            instance = new Chassis();
    }

    public static Chassis getInstance() {
        if (instance == null)
            init();
        return instance;
    }

    public void reset() {
        resetNavx();
        resetEncoders();
    }

    public void resetNavx() {
        m_navX.reset();
    }

    public void resetEncoders() {
        m_rightEncoder.reset();
        m_leftEncoder.reset();
    }

    public void update() {
        SmartDashboard.putString("Chassis::Command", getCurrentCommandName());
        SmartDashboard.putNumber("Chassis::LeftSpeed", m_leftEncoder.getNormalziedVelocity());
        SmartDashboard.putNumber("Chassis::RightSpeed", m_rightEncoder.getNormalziedVelocity());
        SmartDashboard.putNumber("Chassis::Speed", getSpeed());
        SmartDashboard.putNumber("Chassis::Distance", getDistance());
        SmartDashboard.putNumber("Chassis::Angle", getAngle());
    }
}