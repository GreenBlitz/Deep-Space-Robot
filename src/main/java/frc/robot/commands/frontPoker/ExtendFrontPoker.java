package frc.robot.commands.frontPoker;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.FrontPoker;

public class ExtendFrontPoker extends Command {

  public ExtendFrontPoker() {
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