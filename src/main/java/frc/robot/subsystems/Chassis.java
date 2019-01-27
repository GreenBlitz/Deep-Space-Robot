package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap.Chassis.*;
import frc.utils.ctre.CANRobotDrive;
import frc.utils.ctre.SmartEncoder;

public class Chassis extends Subsystem {

  private static Chassis instance;

  private CANRobotDrive m_robotDrive;
  private SmartEncoder m_leftEncoder, m_rightEncoder;
  private AHRS m_navX;

  private Chassis() {
    m_robotDrive = new CANRobotDrive(Motor.Left.Front, 
                                     Motor.Left.Rear, 
                                     Motor.Right.Front, 
                                     Motor.Right.Rear);
    m_leftEncoder = new SmartEncoder(m_robotDrive.getTalon(Sensor.Encoder.Left), Sensor.Encoder.TicksPerMeter);
    m_leftEncoder = new SmartEncoder(m_robotDrive.getTalon(Sensor.Encoder.Right), Sensor.Encoder.TicksPerMeter);
    m_navX = new AHRS(Sensor.NavX);
  }

  @Override
  public void initDefaultCommand() {
  }

  public void arcadeDrive(double move, double rotate) {
    m_robotDrive.arcadeDrive(move, rotate);
  }

  public void tankDrive(double left, double right) {
    m_robotDrive.tankDrive(left, right);
  }

  public void stop() {
    tankDrive(0, 0);
  }

  public double getDistance() {
    return (m_leftEncoder.getDistance() + m_rightEncoder.getDistance()) / 2;
  }

  public double getSpeed() {
    return (m_leftEncoder.getSpeed() + m_rightEncoder.getSpeed()) / 2;
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
    SmartDashboard.putNumber("Chassis::Speed", getSpeed());
    SmartDashboard.putNumber("Chassis::Distance", getDistance());
    SmartDashboard.putNumber("Chassis::Angle", getAngle());
  }
}