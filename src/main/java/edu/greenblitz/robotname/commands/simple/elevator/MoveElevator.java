package edu.greenblitz.robotname.commands.simple.elevator;

import edu.greenblitz.robotname.subsystems.Elevator;

public class MoveElevator extends MoveElevatorByExternalPID {
    public MoveElevator(double height, double lowerTol, double higherTol) {
        super(height, lowerTol, higherTol);
        setInterruptible(false);
        logger.debug("moving to " + height + " from " + Elevator.getInstance().getHeight());
    }

    public MoveElevator(Elevator.Level level) {
        this(level.heightByCurrentState(), 0.05, 0.05);
    }

    public MoveElevator(Elevator.Level level, double lower, double higher){
        this(level.heightByCurrentState(), lower, higher);
    }

}