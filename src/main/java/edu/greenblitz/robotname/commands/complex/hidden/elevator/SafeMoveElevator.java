package edu.greenblitz.robotname.commands.complex.hidden.elevator;

import edu.greenblitz.robotname.commands.complex.hidden.roller.EnsureRollerExtended;
import edu.greenblitz.robotname.commands.simple.elevator.MoveElevator;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.sm.ElevatorState;

public class SafeMoveElevator extends AbstractElevatorHeightCommand {
    public SafeMoveElevator(double height) {
        super(height);
    }

    @Override
    protected void initChain() {
        if(ElevatorState.closestTo(Elevator.getInstance().getLevel()) == ElevatorState.GROUND)
                addSequential(new EnsureRollerExtended());
        addSequential(new MoveElevator(height));
    }
}
