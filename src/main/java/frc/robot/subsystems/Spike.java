package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap.Spike.*;

public class Spike extends Subsystem {

  private static Spike instance;

  private DoubleSolenoid m_kickerPiston, m_extenderPiston;

  private Spike() {
    m_kickerPiston = new DoubleSolenoid(Solenoid.Kicker.Forward, Solenoid.Kicker.Reverse);
    m_extenderPiston = new DoubleSolenoid(Solenoid.Extender.Forward, Solenoid.Extender.Reverse);
  }

  @Override
  public void initDefaultCommand() {
  }

  public static void init() {
    if (instance == null)
      instance = new Spike();
  }

  public static Spike getInstance() {
    if (instance == null)
      init();
    return instance;
  }

  public void setKicker(Value value) {
    m_kickerPiston.set(value);
  }

  public void setExtender(Value value) {
    m_extenderPiston.set(value);
  }

  public Value getKickerState() {
    return m_kickerPiston.get();
  }
  
  public Value getExtenderState() {
    return m_extenderPiston.get();
  }

  public void update() {
    SmartDashboard.putString("Spike::Command", getCurrentCommandName());
    SmartDashboard.putString("Spike::Kicker", getKickerState().name());
    SmartDashboard.putString("Spike::Extender", getExtenderState().name());    
  }
}