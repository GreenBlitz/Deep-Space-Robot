package frc.robot.commands.spike;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Spike;

public class RetractSpike extends Command {

  public RetractSpike() {
    requires(Spike.getInstance());
  }

  @Override
  protected void initialize() {
    Spike.getInstance().setExtender(Value.kReverse);
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}