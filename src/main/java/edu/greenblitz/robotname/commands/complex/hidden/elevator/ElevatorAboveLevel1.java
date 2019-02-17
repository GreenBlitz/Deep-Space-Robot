package edu.greenblitz.robotname.commands.complex.hidden.elevator;

import edu.greenblitz.robotname.RobotMap;
import edu.greenblitz.robotname.commands.simple.elevator.MoveElevator;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Handles elevator moves from somewhere above lvl1 to somewhere above lvl1, i.e no interactions to handle
 */
public class ElevatorAboveLevel1 extends CommandGroup {
    public ElevatorAboveLevel1(double height) {
        addSequential(new MoveElevator(height));
    }

    public ElevatorAboveLevel1(RobotMap.Elevator.ElevatorLevel level) {
        this(level.getHeight());
    }
}
