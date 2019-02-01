package frc.robot.commands.roller;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Roller;

public class CloseAndCollectCargo extends Command {
  private static final long timeout = 1000;

  CloseAndCollectCargo() {
    super(timeout/1000.0);
    requires(Roller.getInstance());
  }

  @Override
  protected void initialize() {
    Roller.getInstance().setExtender(Value.kReverse);
  }

  @Override
  protected void execute() {
    Roller.getInstance().setPower(1);
  }

  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }
}