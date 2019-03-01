package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.utils.sm.PokerState;

public class ExtendPoker extends PokerBaseCommand {
    private static final long POKER_EXTEND_TIMEOUT = 1000;

    public ExtendPoker() {
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

}
