package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.utils.sm.PokerState;

public class ExtendAndHold extends PokerBaseCommand {

    @Override
    protected void atInit() {
        system.extend(true);
        system.hold(true);
    }

    @Override
    protected PokerState getNextState() {
        return PokerState.POKING;
    }
}