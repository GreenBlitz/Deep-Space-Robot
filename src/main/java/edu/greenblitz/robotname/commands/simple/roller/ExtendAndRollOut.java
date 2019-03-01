package edu.greenblitz.robotname.commands.simple.roller;

import edu.greenblitz.utils.sm.RollerState;

//TODO: NO! DO NOT DO THIS! IF YOU DO NOT UNDERSTAND WHY, CALL ME IMMEDIATELY -Atsmon-
public class ExtendAndRollOut extends RollerBaseCommand {
    private static final long ROLLER_EXTENSION_TIMEOUT = 2000;

    public ExtendAndRollOut(long ms) {
        super(ms);
    }

    public ExtendAndRollOut() {
        this(ROLLER_EXTENSION_TIMEOUT);
    }

    @Override
    protected RollerState getNextState() {
        return RollerState.EXTENDED;
    }

    @Override
    protected void initialize() {
        system.extend();
        system.rollOut();
    }
}
