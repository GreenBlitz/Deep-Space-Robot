package edu.greenblitz.robotname.commands.simple.roller;

import edu.greenblitz.utils.sm.RollerState;

public class RetractAndStopRoller extends RollerBaseCommand {
    public RetractAndStopRoller(long ms) {
        super(ms);
    }

    public RetractAndStopRoller() {

    }

    @Override
    protected RollerState getNextState() {
        return RollerState.RETRACTED;
    }

    @Override
    protected void initialize() {
        system.retract();
    }

    @Override
    protected void atEnd(){
        system.stopRolling();
    }
}