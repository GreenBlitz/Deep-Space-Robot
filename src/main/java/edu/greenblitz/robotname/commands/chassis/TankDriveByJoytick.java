package edu.greenblitz.robotname.commands.chassis;

import edu.wpi.first.wpilibj.command.Command;
import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.robotname.utils.SmartJoystick;

public class TankDriveByJoytick extends Command {

  private SmartJoystick m_joystick;

  public TankDriveByJoytick(SmartJoystick joystick) {
    requires(Chassis.getInstance());
    m_joystick = joystick;
  }

  @Override
  protected void execute() {
    Chassis.getInstance().tankDrive(SmartJoystick.Axis.LEFT_Y.getValue(m_joystick),
                                    SmartJoystick.Axis.RIGHT_Y.getValue(m_joystick));
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