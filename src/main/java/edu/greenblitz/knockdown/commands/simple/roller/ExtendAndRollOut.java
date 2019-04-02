package edu.greenblitz.knockdown.commands.simple.roller;

public class ExtendAndRollOut extends RollerBaseCommand {

    public ExtendAndRollOut(long ms) {
        super(ms);
    }

    public ExtendAndRollOut() {

    }

    @Override
    protected void atInit() {
        system.extend();
        system.rollOut();
    }
}
