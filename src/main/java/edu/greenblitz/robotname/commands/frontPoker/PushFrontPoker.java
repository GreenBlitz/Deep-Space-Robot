package edu.greenblitz.robotname.commands.frontPoker;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.greenblitz.robotname.subsystems.FrontPoker;

public class PushFrontPoker extends Command {

  public PushFrontPoker() {
    requires(FrontPoker.getInstance());
  }

  @Override
  protected void initialize() {
    FrontPoker.getInstance().setExtender(Value.kForward);
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}