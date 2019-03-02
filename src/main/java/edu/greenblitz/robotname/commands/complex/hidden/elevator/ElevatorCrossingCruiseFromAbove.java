package edu.greenblitz.robotname.commands.complex.hidden.elevator;

import edu.greenblitz.robotname.commands.complex.hidden.roller.EnsureRollerExtended;
import edu.greenblitz.robotname.commands.complex.hidden.roller.EnsureRollerRetracted;
import edu.greenblitz.robotname.commands.simple.elevator.MoveElevator;
import edu.greenblitz.utils.command.chain.CommandChain;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class ElevatorCrossingCruiseFromAbove extends AbstractElevatorHeightCommand {
    public ElevatorCrossingCruiseFromAbove(double height) {
        super(height);
    }

    @Override
    protected void initChain() {
        addSequential(new EnsureRollerExtended());
        addSequential(new MoveElevator(height));
        addSequential(new EnsureRollerRetracted());
    }
}
