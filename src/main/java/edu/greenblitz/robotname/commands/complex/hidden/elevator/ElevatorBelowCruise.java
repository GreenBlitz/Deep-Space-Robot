package edu.greenblitz.robotname.commands.complex.hidden.elevator;

import edu.greenblitz.robotname.commands.simple.elevator.MoveElevator;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Handles elevator moves from somewhere above lvl1 to somewhere above lvl1, i.e no interactions to handle
 */
public class ElevatorBelowCruise extends CommandGroup {
    public ElevatorBelowCruise(double height) {
        addSequential(new MoveElevator(height));
    }

    public ElevatorBelowCruise(Elevator.Level level) {
        this(level.getHeight());
    }
}


