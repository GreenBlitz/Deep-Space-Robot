package edu.greenblitz.robotname.commands.simple.roller;

import edu.greenblitz.utils.sm.RollerState;

public class RetractAndRollIn extends RollerBaseCommand {
    private static final long ROLLER_RETRACTION_TIMEOUT = 2000;

    public RetractAndRollIn(long ms) {
        super(ms);
    }

    public RetractAndRollIn() {
        this(ROLLER_RETRACTION_TIMEOUT);
    }

    @Override
    protected RollerState getNextState() {
        return RollerState.RETRACTED;
    }

    @Override
    protected void initialize() {
        system.retract();
        system.rollIn();
    }
}
