package edu.greenblitz.robotname.commands.simple.roller;

public class ExtendAndRollIn extends RollerBaseCommand {

    public ExtendAndRollIn(long ms) {
        super(ms);
    }

    public ExtendAndRollIn() {

    }

    @Override
    protected void atInit() {
        system.extend();
        system.rollIn();
    }
}
