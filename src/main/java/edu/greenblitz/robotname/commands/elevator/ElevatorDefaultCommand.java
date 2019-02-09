package edu.greenblitz.robotname.commands.elevator;

import edu.greenblitz.robotname.subsystems.Elevator;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class ElevatorDefaultCommand extends CommandGroup {
  public ElevatorDefaultCommand() {
    addParallel(new BrakeElevator());
    addParallel(Elevator.getInstance().commandQueue);
  }
}