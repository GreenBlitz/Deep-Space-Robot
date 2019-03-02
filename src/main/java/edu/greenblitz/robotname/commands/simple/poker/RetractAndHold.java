package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.utils.sm.PokerState;

public class RetractAndHold extends PokerBaseCommand {
    private static final long POKER_EXTEND_TIMEOUT = 1000;

    public RetractAndHold() {
        super(POKER_EXTEND_TIMEOUT);
    }

    @Override
    protected void initialize() {
        system.hold(true);
    }

    @Override
    protected PokerState getNextState() {
        return PokerState.UNPOKING;
    }

    @Override
    protected void end() {
        system.extend(false);
    }
}