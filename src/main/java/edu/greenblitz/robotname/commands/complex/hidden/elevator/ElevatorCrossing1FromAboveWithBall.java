package edu.greenblitz.robotname.commands.complex.hidden.elevator;

import edu.greenblitz.robotname.RobotMap;
import edu.greenblitz.robotname.commands.complex.hidden.roller.EnsureRollerExtended;
import edu.greenblitz.robotname.commands.simple.elevator.MoveElevator;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class ElevatorCrossing1FromAboveWithBall extends CommandGroup {
    public ElevatorCrossing1FromAboveWithBall(double height) {
        addSequential(new EnsureRollerExtended());
        addSequential(new MoveElevator(height));
    }
}
