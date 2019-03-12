package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.utils.sm.PokerState;

public class RetractPoker extends PokerBaseCommand {
    public RetractPoker() {
    }

    public RetractPoker(long ms) {
        super(ms);
    }

    @Override
    protected void atInit() {
        system.extend(false);
    }

    @Override
    protected PokerState getNextState() {
        return PokerState.UNPOKING;
    }
}
