package edu.greenblitz.knockdown.commands.complex.roller;

import edu.greenblitz.knockdown.commands.simple.roller.RollerBaseCommand;
import edu.greenblitz.knockdown.subsystems.Elevator;

public class SmartExtendAndRollIn extends RollerBaseCommand {

    public SmartExtendAndRollIn(long ms) {
        super(ms);
    }

    public SmartExtendAndRollIn() {
    }

    @Override
    protected void atInit() {
        if (Elevator.getInstance().isFloorLevel()) {
            system.extend();
            system.rollIn();
        }
    }

}
