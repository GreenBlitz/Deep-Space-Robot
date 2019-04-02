package edu.greenblitz.knockdown.commands.simple.roller;

public class RetractAndStopRoller extends RollerBaseCommand {
    public RetractAndStopRoller(long ms) {
        super(ms);
    }

    public RetractAndStopRoller() {

    }

    @Override
    protected void atInit() {
        system.retract();
    }

    @Override
    protected void atEnd(){
        system.stopRolling();
    }
}