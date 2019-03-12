package edu.greenblitz.robotname.commands.simple.roller;

import edu.greenblitz.utils.sm.RollerState;

public class RetractAndRollIn extends RollerBaseCommand {
    public RetractAndRollIn(long ms) {
        super(ms);
    }

    public RetractAndRollIn() {

    }

    @Override
    protected RollerState getNextState() {
        return RollerState.RETRACTED;
    }

    @Override
    protected void atInit() {
        system.retract();
        system.rollIn();
    }
}
