package frc.robot.commands.spike;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Spike;

public class ExtendSpike extends Command {

  public ExtendSpike() {
    requires(Spike.getInstance());
  }

  @Override
  protected void initialize() {
    Spike.getInstance().setExtender(Value.kForward);
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}