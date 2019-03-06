package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.utils.sm.PokerState;

public class ExtendPoker extends PokerBaseCommand {
    public ExtendPoker() {

    }

    @Override
    protected void atInitialize() {
        system.extend(true);
    }

    @Override
    protected PokerState getNextState() {
        return PokerState.POKING;
    }
}
