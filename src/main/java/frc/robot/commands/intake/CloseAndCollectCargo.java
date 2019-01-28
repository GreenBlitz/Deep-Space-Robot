package frc.robot.commands.intake;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Intake;

public class CloseAndCollectCargo extends Command {
  private static final long timeout = 1000;

  CloseAndCollectCargo() {
    super(timeout/1000.0);
    requires(Intake.getInstance());
  }

  @Override
  protected void initialize() {
    Intake.getInstance().setExtender(Value.kReverse);
  }

  @Override
  protected void execute() {
    Intake.getInstance().setPower(1);
  }

  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }
}