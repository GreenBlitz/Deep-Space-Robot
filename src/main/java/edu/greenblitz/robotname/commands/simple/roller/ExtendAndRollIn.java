package edu.greenblitz.robotname.commands.simple.roller;

import edu.greenblitz.utils.sm.RollerState;

public class ExtendAndRollIn extends RollerBaseCommand {

    public ExtendAndRollIn(long ms) {
        super(ms);
    }

    public ExtendAndRollIn() {

    }

    @Override
    protected RollerState getNextState() {
        return RollerState.EXTENDED;
    }

    @Override
    protected void atInit() {
        system.extend();
        system.rollIn();
    }
}
