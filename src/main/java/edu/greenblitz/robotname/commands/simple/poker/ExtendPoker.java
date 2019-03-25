package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.utils.sm.PokerState;

public class ExtendPoker extends PokerBaseCommand {
    public ExtendPoker() {
    }

    public ExtendPoker(long ms) {
        super(ms);
    }

    @Override
    protected void atInit() {
        system.extend(true);
    }

    @Override
    protected PokerState getNextState() {
        return PokerState.POKING;
    }
}
