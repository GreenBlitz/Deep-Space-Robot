package edu.greenblitz.robotname.commands.complex.hidden.elevator;

import edu.greenblitz.robotname.commands.complex.hidden.roller.EnsureRollerExtended;
import edu.greenblitz.robotname.commands.simple.elevator.MoveElevator;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class ElevatorCrossingCruiseFromAboveWithBall extends CommandGroup {
    public ElevatorCrossingCruiseFromAboveWithBall(double height) {
        addSequential(new EnsureRollerExtended());
        addSequential(new MoveElevator(height));
    }
}
