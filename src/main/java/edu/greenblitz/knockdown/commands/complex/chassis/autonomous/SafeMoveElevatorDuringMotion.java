package edu.greenblitz.knockdown.commands.complex.chassis.autonomous;

import edu.greenblitz.knockdown.commands.complex.elevator.SafeMoveElevator;
import edu.greenblitz.knockdown.subsystems.Elevator;
import edu.greenblitz.utils.command.CommandChain;

public class SafeMoveElevatorDuringMotion extends CommandChain {
    public SafeMoveElevatorDuringMotion(Elevator.Level level, double x, double y) {
        addSequential(new WaitLocalizerPassedY(x, y));
        addSequential(new SafeMoveElevator(level));
    }
}
