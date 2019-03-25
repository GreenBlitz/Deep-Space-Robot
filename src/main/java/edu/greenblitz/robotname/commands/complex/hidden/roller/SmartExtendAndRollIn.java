package edu.greenblitz.robotname.commands.complex.hidden.roller;

import edu.greenblitz.robotname.commands.simple.roller.RollerBaseCommand;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.sm.RollerState;

public class SmartExtendAndRollIn extends RollerBaseCommand {

    public SmartExtendAndRollIn(long ms) {
        super(ms);
    }

    public SmartExtendAndRollIn() {
    }

    @Override
    protected RollerState getNextState() {
        return RollerState.EXTENDED;
    }

    @Override
    protected void atInit() {
        if (Elevator.getInstance().isFloorLevel()) {
            system.extend();
            system.rollIn();
        }
    }

}
