package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.utils.sm.PokerState;
import edu.greenblitz.utils.sm.State;

public class ClosePoker extends PokerBaseCommand {
    private static final long POKER_CLOSE_TIMEOUT = 1000;

    public ClosePoker() {
        super(POKER_CLOSE_TIMEOUT);
    }

    @Override
    protected void initialize() {
        system.fullClose();
    }

    @Override
    protected PokerState getNextState() {
        return PokerState.UNPOKING;
    }
}
