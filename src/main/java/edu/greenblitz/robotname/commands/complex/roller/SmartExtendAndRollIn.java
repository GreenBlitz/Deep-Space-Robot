package edu.greenblitz.robotname.commands.complex.roller;

import edu.greenblitz.robotname.commands.simple.roller.RollerBaseCommand;
import edu.greenblitz.robotname.subsystems.Elevator;

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
