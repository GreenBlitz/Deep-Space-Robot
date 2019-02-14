package edu.greenblitz.robotname.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.greenblitz.robotname.RobotMap.Roller.Motor;
import edu.greenblitz.robotname.RobotMap.Roller.Sensor;
import edu.greenblitz.robotname.RobotMap.Roller.Solenoid;
import edu.greenblitz.robotname.commands.roller.HandleByElevator;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Roller extends Subsystem {

  private static Roller instance;

  private DoubleSolenoid m_piston;
  private CANSparkMax m_motor;
  private DigitalInput m_infrared, m_limitSwitch;

  private int m_pistonChanges = 0;

  private Roller() {
    m_piston = new DoubleSolenoid(3, Solenoid.Forward, Solenoid.Reverse);
    m_motor = new CANSparkMax(Motor.Roller, MotorType.kBrushless);
    m_infrared = new DigitalInput(Sensor.Infrared);
    m_limitSwitch = new DigitalInput(Sensor.LimitSwitch);
  }

  public void setExtender(Value value) {
    m_piston.set(value);
    m_pistonChanges += m_piston.get() != value ? 1 : 0;
  }

  public Value getExtenderState() {
    return m_piston.get();
  }

  public void setPower(double power) {
    m_motor.set(power);
  }

  public boolean isBallFullyIn() {
    return m_limitSwitch.get();
  }

  public boolean isBallIn() {
    return m_infrared.get();
  }

  public int getPistonChanges() {
    return m_pistonChanges;
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new HandleByElevator());
  }

  public static void init() {
    if (instance == null)
      instance = new Roller();
  }

  public static Roller getInstance() {
    if (instance == null)
      init();
    return instance;
  }

  public void update() {
    SmartDashboard.putString("ROLLER::Command", getCurrentCommandName());
    SmartDashboard.putString("ROLLER::EXTENDER", getExtenderState().name());
    SmartDashboard.putBoolean("Roller::IsBallIn", m_infrared.get());
    SmartDashboard.putBoolean("Roller::IsBallFullyIn", m_limitSwitch.get());
    SmartDashboard.putNumber("Roller::SolenoidChanges", m_pistonChanges);
  }
}