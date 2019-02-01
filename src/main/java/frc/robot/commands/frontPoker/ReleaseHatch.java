package frc.robot.commands.frontPoker;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.robot.subsystems.FrontPoker;

public class ReleaseHatch extends TimedCommand {

  private static final long timeout = 1000;

  public ReleaseHatch() {
    super(timeout/1000.0);
    requires(FrontPoker.getInstance());
  }

  @Override
  protected void initialize() {
    if (FrontPoker.getInstance().getExtenderState() == Value.kForward)
      FrontPoker.getInstance().setExtender(Value.kForward);
  }

  @Override
  protected void end() {
    FrontPoker.getInstance().setKicker(Value.kForward);    
  }
}