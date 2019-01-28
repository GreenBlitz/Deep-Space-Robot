package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap.Intake.*;
import frc.robot.commands.intake.AutoIntakeByElevator;
import frc.utils.ctre.SmartTalon;

public class Intake extends Subsystem {

  private static Intake instance;

  private DoubleSolenoid m_piston;
  private SmartTalon m_motor;

  private Intake() {
    m_piston = new DoubleSolenoid(Solenoid.Forward, Solenoid.Reverse);
    m_motor = new SmartTalon(Motor.Roller);
  }

  public void setExtender(Value value) {
    m_piston.set(value);
  }

  public Value getExtenderState() {
    return m_piston.get();
  }

  public void setPower(double power) {
    m_motor.set(power);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new AutoIntakeByElevator());
  }

  public static void init() {
    if (instance == null)
      instance = new Intake();
  }

  public static Intake getInstance() {
    if (instance == null)
      init();
    return instance;
  }

  public void update() {
    SmartDashboard.putString("Intake::Command", getCurrentCommandName());
    SmartDashboard.putString("Intake::Extender", getExtenderState().name());
  }
}