package edu.greenblitz.robotname.commands.roller;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.robotname.subsystems.Roller;

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