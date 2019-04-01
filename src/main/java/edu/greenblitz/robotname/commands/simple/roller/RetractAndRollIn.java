package edu.greenblitz.robotname.commands.simple.roller;

public class RetractAndRollIn extends RollerBaseCommand {
    public RetractAndRollIn(long ms) {
        super(ms);
    }

    public RetractAndRollIn() {

    }

    @Override
    protected void atInit() {
        system.retract();
        system.rollIn();
    }
}
