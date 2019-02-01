package frc.robot.commands.roller;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Roller;

public class AutoRollerByElevator extends Command {

  public AutoRollerByElevator() {
    requires(Roller.getInstance());
  }

  @Override
  protected void execute() {
    Roller.getInstance().setExtender(Elevator.getInstance().isInDangerZone() ? Value.kForward : Value.kReverse);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}