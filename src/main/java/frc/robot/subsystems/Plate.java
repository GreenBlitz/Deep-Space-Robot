package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap.Plate.*;

public class Plate extends Subsystem {

  private static Plate instance;

  private DoubleSolenoid m_piston;

  private Plate() {
    m_piston = new DoubleSolenoid(Solenoid.Forward, Solenoid.Backward);
  }

  @Override
  public void initDefaultCommand() {
  }

  public static void init() {
    if (instance == null)
      instance = new Plate();
  }

  public static Plate getInstance() {
    if (instance == null)
      init();
    return instance;
  }

  public void setState(Value value) {
    m_piston.set(value);
  }

  public void update() {
    SmartDashboard.putString("Plate::Command", getCurrentCommandName());
  }
}