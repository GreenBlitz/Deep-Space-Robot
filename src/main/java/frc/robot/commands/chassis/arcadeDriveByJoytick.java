package frc.robot.commands.chassis;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Chassis;
import frc.utils.SmartJoystick;

public class ArcadeDriveByJoytick extends Command {

  private SmartJoystick m_joystick;

  public ArcadeDriveByJoytick(SmartJoystick joystick) {
    requires(Chassis.getInstance());
    m_joystick = joystick;
  }

  @Override
  protected void execute() {
    Chassis.getInstance().arcadeDrive(SmartJoystick.Axis.LEFT_Y.getValue(m_joystick),
                                      SmartJoystick.Axis.RIGHT_X.getValue(m_joystick));
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Chassis.getInstance().stop();
  }
}