package edu.greenblitz.robotname.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.robotname.OI;
import edu.greenblitz.robotname.RobotMap.Chassis.Motor;
import edu.greenblitz.robotname.RobotMap.Chassis.Sensor;
import edu.greenblitz.robotname.commands.chassis.ArcadeDriveByJoystick;
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
        var leftFront = new CANSparkMax(Motor.Left.FRONT, CANSparkMaxLowLevel.MotorType.kBrushless);
        var leftRear = new CANSparkMax(Motor.Left.REAR, CANSparkMaxLowLevel.MotorType.kBrushless);
        var rightFront = new CANSparkMax(Motor.Right.FRONT, CANSparkMaxLowLevel.MotorType.kBrushless);
        var rightRear = new CANSparkMax(Motor.Right.REAR, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_robotDrive = new RobotDrive(leftFront, leftRear, rightFront, rightRear);
        m_leftEncoder = new SparkEncoder(Sensor.Encoder.TICKS_PER_METER, (CANSparkMax) m_robotDrive.getMotor(Sensor.Encoder.LEFT));
        m_rightEncoder = new SparkEncoder(Sensor.Encoder.TICKS_PER_METER, (CANSparkMax) m_robotDrive.getMotor(Sensor.Encoder.RIGHT));
        m_navX = new AHRS(Sensor.NavX);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new ArcadeDriveByJoystick(OI.getInstance().getMainJoystick()));
    }

    public void arcadeDrive(double move, double rotate) {
        m_robotDrive.arcadeDrive(move, rotate);
    }

    public void tankDrive(double left, double right) {
        m_robotDrive.tankDrive(left, right);
    }

    public void stop() {
        m_robotDrive.stop();
    }

    public double getDistance() {
        return (m_leftEncoder.getNormalizedTicks() + m_rightEncoder.getNormalizedTicks()) / 2;
    }

    public double getSpeed() {
        return (m_leftEncoder.getNormalziedVelocity() + m_rightEncoder.getNormalziedVelocity()) / 2;
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

    public void resetNavx() {
        m_navX.reset();
    }

    public void resetEncoders() {
        m_rightEncoder.reset();
        m_leftEncoder.reset();
    }

    public void update() {
        SmartDashboard.putString("Chassis::Command", getCurrentCommandName());
        SmartDashboard.putNumber("Chassis::Speed", getSpeed());
        SmartDashboard.putNumber("Chassis::Distance", getDistance());
        SmartDashboard.putNumber("Chassis::Angle", getAngle());
    }
}