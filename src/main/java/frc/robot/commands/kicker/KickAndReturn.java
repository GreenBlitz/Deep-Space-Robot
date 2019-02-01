package frc.robot.commands.kicker;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.robot.subsystems.Kicker;

public class KickAndReturn extends TimedCommand {
  
  private static final long timeout = 400;
  
  public KickAndReturn() {
    super(timeout/1000.0);
    requires(Kicker.getInstance());
  }

  @Override
  protected void initialize() {
    Kicker.getInstance().setState(Value.kForward);
  }

  @Override
  protected void end() {
    Kicker.getInstance().setState(Value.kReverse);
  }
}