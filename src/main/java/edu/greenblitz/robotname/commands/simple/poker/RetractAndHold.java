package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.utils.sm.PokerState;

public class RetractAndHold extends PokerBaseCommand {
    public RetractAndHold() {

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
    protected void atEnd() {
        system.extend(false);
    }
}