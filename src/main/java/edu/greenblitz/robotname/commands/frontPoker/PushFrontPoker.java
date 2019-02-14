package edu.greenblitz.robotname.commands.frontPoker;

import edu.greenblitz.utils.command.SubsystemCommand;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
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