package frc.robot.commands.intake;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.subsystems.Intake;

public class OpenAndCollectCargo extends Command {

  public OpenAndCollectCargo() {
    requires(Intake.getInstance());
  }

  @Override
  protected void initialize() {
    Intake.getInstance().setExtender(Value.kForward);
  }

  @Override
  protected void execute() {
    Intake.getInstance().setPower(1);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Scheduler.getInstance().add(new CloseAndCollectCargo());
  }
}