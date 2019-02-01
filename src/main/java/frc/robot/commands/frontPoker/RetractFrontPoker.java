package frc.robot.commands.frontPoker;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.FrontPoker;

public class RetractFrontPoker extends Command {

  public RetractFrontPoker() {
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