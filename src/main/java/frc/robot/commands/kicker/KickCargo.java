package frc.robot.commands.kicker;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Kicker;

public class KickCargo extends Command {
  
  private static final long timeout = 400;
  
  public KickCargo() {
    super(timeout/1000.0);
    requires(Kicker.getInstance());
  }

  @Override
  protected void initialize() {
    Kicker.getInstance().setState(Value.kForward);
  }

  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }

  @Override
  protected void end() {
    Kicker.getInstance().setState(Value.kReverse);
  }
}