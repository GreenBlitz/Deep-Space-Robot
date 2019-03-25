package edu.greenblitz.robotname.commands.simple.roller;

import edu.greenblitz.utils.sm.RollerState;

public class ExtendAndRollOut extends RollerBaseCommand {

    public ExtendAndRollOut(long ms) {
        super(ms);
    }

    public ExtendAndRollOut() {

    }

    @Override
    protected RollerState getNextState() {
        return RollerState.EXTENDED;
    }

    @Override
    protected void atInit() {
        system.extend();
        system.rollOut();
    }
}
