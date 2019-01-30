package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.RobotMap.Climber.*;
import frc.utils.ctre.SmartTalon;

public class Climber extends Subsystem {

  private static Climber instance;

  private SmartTalon m_extender, m_wheels;

  private Climber() {
    m_extender = new SmartTalon(Motor.Extender);
    m_wheels = new SmartTalon(Motor.Wheels);
  }

  public void setExtender(double power) {
    m_extender.set(power);
  }

  public void setWheels(double power) {
    m_wheels.set(power);
  }

  @Override
  public void initDefaultCommand() {
    OI.getInstance().getMainJoystick().A.whenPressed(null);
  }

  public static void init() {
    if (instance == null)
      instance = new Climber();
  }

  public static Climber getInstance() {
    if (instance == null)
      init();
    return instance;
  }

  public void update() {
    SmartDashboard.putString("Climber::Command", getCurrentCommandName());
  }
}