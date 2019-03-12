package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.utils.sm.PokerState;

public class RetractAndHold extends PokerBaseCommand {
    public RetractAndHold(long ms) {
        super(ms);
    }

    public RetractAndHold() {
    }

    @Override
    protected void atInit() {
        system.extend(false);
        system.hold(true);
    }

    @Override
    protected PokerState getNextState() {
        return PokerState.UNPOKING;
    }
}