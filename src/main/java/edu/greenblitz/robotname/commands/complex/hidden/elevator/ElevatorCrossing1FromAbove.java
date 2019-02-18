package edu.greenblitz.robotname.commands.complex.hidden.elevator;

import edu.greenblitz.robotname.Robot;
import edu.greenblitz.utils.command.dynamic.BinaryChoiceCommand;

public class ElevatorCrossing1FromAbove extends BinaryChoiceCommand {
    public ElevatorCrossing1FromAbove(double height) {
        super(
                new ElevatorCrossing1FromAboveWithoutBall(height),
                new ElevatorCrossing1FromAboveWithBall(height));
    }

    @Override
    protected Boolean state() {
        return Robot.getInstance().getState().hasBall();
    }
}
