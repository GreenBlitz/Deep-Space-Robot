package edu.greenblitz.robotname.commands.simple.chassis.driver;

import edu.wpi.first.wpilibj.command.Command;
import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.utils.hid.SmartJoystick;

public class ArcadeDriveByJoystick extends Command {

  public static final double SPEED_MULT = 1;
  public static final double TURN_MULT = 0.3;
  public static final double RAMPING_RATE_JOYSTIK = 1;

  private SmartJoystick m_joystick;

  public ArcadeDriveByJoystick(SmartJoystick joystick) {
    requires(Chassis.getInstance());
    m_joystick = joystick;
  }

  @Override
  protected void initialize() {
    Chassis.getInstance().setRampingRate(RAMPING_RATE_JOYSTIK);
  }

  @Override
  protected void execute() {
    Chassis.getInstance().arcadeDrive(SmartJoystick.Axis.LEFT_Y.getValue(m_joystick) * SPEED_MULT,
                                      SmartJoystick.Axis.RIGHT_X.getValue(m_joystick) * TURN_MULT);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Chassis.getInstance().setRampingRate(0);
    Chassis.getInstance().stop();
  }
}