package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.utils.sm.PokerState;

public class ExtendAndRelease extends PokerBaseCommand {
    public ExtendAndRelease() {

    }

    @Override
    protected void atInitialize() {
        system.extend(true);
    }

    @Override
    protected PokerState getNextState() {
        return PokerState.POKING;
    }

    @Override
    protected void atEnd() {
        system.hold(false);
    }
}