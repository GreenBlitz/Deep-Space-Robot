package frc.robot.commands.intake;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;

public class AutoIntakeByElevator extends Command {

  public AutoIntakeByElevator() {
    requires(Intake.getInstance());
  }

  @Override
  protected void execute() {
    Intake.getInstance().setExtender(Elevator.getInstance().isInDangerZone() ? Value.kForward : Value.kReverse);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}