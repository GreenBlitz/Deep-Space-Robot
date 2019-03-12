package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.utils.sm.PokerState;

public class ClosePoker extends PokerBaseCommand {
    public ClosePoker() {

    }

    @Override
    protected void atInit() {
        system.fullClose();
    }

    @Override
    protected PokerState getNextState() {
        return PokerState.UNPOKING;
    }
}
