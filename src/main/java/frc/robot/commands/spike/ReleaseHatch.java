package frc.robot.commands.spike;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.robot.subsystems.Spike;

public class ReleaseHatch extends TimedCommand {

  private static final long timeout = 1000;

  public ReleaseHatch() {
    super(timeout/1000.0);
    requires(Spike.getInstance());
  }

  @Override
  protected void initialize() {
    if (Spike.getInstance().getExtenderState() == Value.kForward)
      Spike.getInstance().setExtender(Value.kForward);
  }

  @Override
  protected void end() {
    Spike.getInstance().setKicker(Value.kForward);    
  }
}