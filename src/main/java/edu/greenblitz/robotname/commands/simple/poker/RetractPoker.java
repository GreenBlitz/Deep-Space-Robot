package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.utils.sm.PokerState;

public class RetractPoker extends PokerBaseCommand {
    private static final long POKER_RETRACT_TIMEOUT = 2000;

    public RetractPoker() {
        super(POKER_RETRACT_TIMEOUT);
    }

    @Override
    protected void initialize() {
        system.extend(false);
    }

    @Override
    protected PokerState getNextState() {
        return PokerState.UNPOKING;
    }
}
