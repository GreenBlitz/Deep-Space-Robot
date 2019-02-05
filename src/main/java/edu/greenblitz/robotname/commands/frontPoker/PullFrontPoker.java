package edu.greenblitz.robotname.commands.frontPoker;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.greenblitz.robotname.subsystems.FrontPoker;

public class PullFrontPoker extends Command {

  public PullFrontPoker() {
    requires(FrontPoker.getInstance());
  }

  @Override
  protected void initialize() {
    FrontPoker.getInstance().setExtender(Value.kReverse);
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}