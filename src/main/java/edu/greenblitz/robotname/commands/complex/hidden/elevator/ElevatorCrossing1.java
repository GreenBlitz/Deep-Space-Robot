package edu.greenblitz.robotname.commands.complex.hidden.elevator;

import edu.greenblitz.robotname.commands.complex.hidden.roller.EnsureRollerExtended;
import edu.greenblitz.robotname.commands.complex.hidden.roller.EnsureRollerRetracted;
import edu.greenblitz.robotname.commands.simple.elevator.MoveElevator;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Handles an elevator move from lvl0 to above lvl1
 */
public class ElevatorCrossing1 extends CommandGroup {
    public ElevatorCrossing1(double height) {
        addSequential(new EnsureRollerExtended());
        addSequential(new MoveElevator(height));
        addSequential(new EnsureRollerRetracted());
    }
}
