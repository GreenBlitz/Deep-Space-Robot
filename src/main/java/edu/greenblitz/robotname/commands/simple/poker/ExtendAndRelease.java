package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.utils.sm.PokerState;

public class ExtendAndRelease extends PokerBaseCommand {
    private static final long POKER_EXTEND_TIMEOUT = 1000;

    public ExtendAndRelease() {
        super(POKER_EXTEND_TIMEOUT);
    }

    @Override
    protected void initialize() {
        system.extend(true);
    }

    @Override
    protected PokerState getNextState() {
        return PokerState.POKING;
    }

    @Override
    protected void end() {
        system.hold(false);
    }
}