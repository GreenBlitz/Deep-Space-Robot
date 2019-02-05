package edu.greenblitz.robotname.commands.frontPoker;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.greenblitz.robotname.subsystems.FrontPoker;

public class ReleaseHatch extends Command {

  public ReleaseHatch() {
    requires(FrontPoker.getInstance());
  }

  @Override
  protected void initialize() {
    FrontPoker.getInstance().setKicker(Value.kForward);
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}