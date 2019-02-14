package edu.greenblitz.robotname.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.greenblitz.robotname.RobotMap.RearPicker.*;
import edu.greenblitz.robotname.commands.rearPicker.PickByBumbers;

public class RearPicker extends Subsystem {

  private static RearPicker instance;

  private DoubleSolenoid m_piston;
  private CANSparkMax m_motor;
  private DigitalInput m_lowSwitch, m_highSwitch;

  private int m_pistonChanges = 0;

  private RearPicker() {
    m_piston = new DoubleSolenoid(3, Solenoid.Forward, Solenoid.Reverse);
    m_motor = new CANSparkMax(Motor.Picker, MotorType.kBrushless);
    m_lowSwitch = new DigitalInput(Sensor.LowSwitch);
    m_highSwitch = new DigitalInput(Sensor.HighSwitch);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new PickByBumbers());
  }

  public static void init() {
    if (instance == null)
      instance = new RearPicker();
  }

  public static RearPicker getInstance() {
    if (instance == null)
      init();
    return instance;
  }

  public void setState(Value value) {
    m_piston.set(value);
    m_pistonChanges += m_piston.get() != value ? 1 : 0;
  }

  public void setPower(double power) {
    m_motor.set(power);
  }

  public boolean isLowered() {
    return m_lowSwitch.get();
  }

  public boolean isRaised() {
    return m_highSwitch.get();
  }

  public int getPistonChanges() {
    return m_pistonChanges;
  }

  public void update() {
    SmartDashboard.putString("RearPicker::Command", getCurrentCommandName());
    SmartDashboard.putNumber("RearPicker::SolenoidChanges", m_pistonChanges);
  }
}