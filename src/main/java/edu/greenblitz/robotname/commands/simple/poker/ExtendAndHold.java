package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.utils.sm.PokerState;

public class ExtendAndHold extends PokerBaseCommand {
    public ExtendAndHold(long ms) {
        super(ms);
    }

    public ExtendAndHold() {}

    @Override
    protected void atInit() {
        system.extend(true);
        system.hold(true);
    }

    @Override
    protected PokerState getNextState() {
        return PokerState.UNPOKING;
    }
}