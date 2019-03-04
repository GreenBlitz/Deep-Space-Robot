package edu.greenblitz.robotname.commands.simple.roller;

import edu.greenblitz.utils.sm.RollerState;

public class RetractAndStop extends RollerBaseCommand {
    private static final long ROLLER_RETRACTION_TIMEOUT = 2000;

    public RetractAndStop(long ms) {
        super(ms);
    }

    public RetractAndStop() {
        this(ROLLER_RETRACTION_TIMEOUT);
    }

    @Override
    protected RollerState getNextState() {
        return RollerState.RETRACTED;
    }

    @Override
    protected void initialize() {
        system.retract();
        system.stop();
    }
}