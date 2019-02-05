package edu.greenblitz.robotname.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.greenblitz.robotname.RobotMap.RearPicker.*;

public class RearPicker extends Subsystem {

  private static RearPicker instance;

  private DoubleSolenoid m_piston;

  private RearPicker() {
    m_piston = new DoubleSolenoid(Solenoid.FORWARD, Solenoid.Backward);
  }

  @Override
  public void initDefaultCommand() {
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
  }

  public void update() {
    SmartDashboard.putString("RearPicker::Command", getCurrentCommandName());
  }
}