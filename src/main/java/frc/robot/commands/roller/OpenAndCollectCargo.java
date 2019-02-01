package frc.robot.commands.roller;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.subsystems.Roller;

public class OpenAndCollectCargo extends Command {

  public OpenAndCollectCargo() {
    requires(Roller.getInstance());
  }

  @Override
  protected void initialize() {
    Roller.getInstance().setExtender(Value.kForward);
  }

  @Override
  protected void execute() {
    Roller.getInstance().setPower(1);
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