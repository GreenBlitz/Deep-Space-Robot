package edu.greenblitz.robotname.commands.simple.elevator;

import edu.greenblitz.robotname.subsystems.Elevator;

public class MoveElevator extends MoveElevatorByMagic {
    private static final long TIME_ON_TARGET = 50;

    public MoveElevator(double level) {
        super(level, Elevator.MAGIC_LOOP_IDX, TIME_ON_TARGET);
    }
}
