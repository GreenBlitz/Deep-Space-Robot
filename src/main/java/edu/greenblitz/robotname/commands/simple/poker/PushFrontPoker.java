package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.utils.command.SubsystemCommand;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.greenblitz.robotname.subsystems.FrontPoker;

public class PushFrontPoker extends SubsystemCommand<FrontPoker> {

  public PushFrontPoker() {
    super(FrontPoker.getInstance());
  }

  @Override
  protected void initialize() {
    system.setExtender(Value.kForward);
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}