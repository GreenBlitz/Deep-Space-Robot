package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.utils.sm.PokerState;

public class TogglePokerExtender extends PokerBaseCommand {

    public TogglePokerExtender() {

    }

    @Override
    protected void atInit() {
        system.extend(!system.isExtended());
    }

    @Override
    protected PokerState getNextState() {
        return null;
    }
}
