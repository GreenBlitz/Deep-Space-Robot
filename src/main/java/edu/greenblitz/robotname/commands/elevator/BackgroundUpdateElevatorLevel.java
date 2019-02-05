package edu.greenblitz.robotname.commands.elevator;

import edu.greenblitz.robotname.RobotMap;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.wpi.first.wpilibj.command.Command;

public class BackgroundUpdateElevatorLevel extends Command {
  
  private static final double LEVEL_HEIGHT_RANGE = 0;

  @Override
  protected void execute() {
    for (RobotMap.Elevator.ElevatorLevel level : RobotMap.Elevator.ElevatorLevel.values()) {
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