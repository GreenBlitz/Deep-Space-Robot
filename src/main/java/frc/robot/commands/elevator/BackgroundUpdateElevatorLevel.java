package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Elevator;
import frc.utils.enums.ElevatorLevel;

public class BackgroundUpdateElevatorLevel extends Command {
  
  private static final double LEVEL_HEIGHT_RANGE = 0;

  @Override
  protected void execute() {
    for (ElevatorLevel level : ElevatorLevel.values()) {
      if (Math.abs(Elevator.getInstance().getHeight() - level.getHeight()) < LEVEL_HEIGHT_RANGE) {
        Elevator.getInstance().setLevel(level);
        return;
      }
    }
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}