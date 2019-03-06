package edu.greenblitz.robotname.commands.simple.roller;

import edu.greenblitz.utils.sm.RollerState;

public class RetractAndStop extends RollerBaseCommand {
    public RetractAndStop(long ms) {
        super(ms);
    }

    public RetractAndStop() {

    }

    @Override
    protected RollerState getNextState() {
        return RollerState.RETRACTED;
    }

    @Override
    protected void initialize() {
        system.retract();
        system.stopRolling();
    }
}