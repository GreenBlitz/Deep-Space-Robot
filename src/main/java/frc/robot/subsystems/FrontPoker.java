package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap.FrontPoker.*;

public class FrontPoker extends Subsystem {

  private static FrontPoker instance;

  private DoubleSolenoid m_kickerPiston, m_extenderPiston;

  private FrontPoker() {
    m_kickerPiston = new DoubleSolenoid(Solenoid.Kicker.Forward, Solenoid.Kicker.Reverse);
    m_extenderPiston = new DoubleSolenoid(Solenoid.Extender.Forward, Solenoid.Extender.Reverse);
  }

  @Override
  public void initDefaultCommand() {
  }

  public static void init() {
    if (instance == null)
      instance = new FrontPoker();
  }

  public static FrontPoker getInstance() {
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
    SmartDashboard.putString("FrontPoker::Command", getCurrentCommandName());
    SmartDashboard.putString("FrontPoker::Kicker", getKickerState().name());
    SmartDashboard.putString("FrontPoker::Extender", getExtenderState().name());    
  }
}