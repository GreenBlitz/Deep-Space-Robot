package edu.greenblitz.robotname.commands.complex.hidden.elevator;

import edu.greenblitz.robotname.Robot;
import edu.greenblitz.utils.command.dynamic.BinaryChoiceCommand;

public class ElevatorCrossingCruiseFromAbove extends BinaryChoiceCommand {
    public ElevatorCrossingCruiseFromAbove(double height) {
        super(
                new ElevatorCrossingCruiseFromAboveWithoutBall(height),
                new ElevatorCrossingCruiseFromAboveWithBall(height));
    }

    @Override
    protected Boolean state() {
        return Robot.getInstance().getCurrentState().hasBall();
    }
}
