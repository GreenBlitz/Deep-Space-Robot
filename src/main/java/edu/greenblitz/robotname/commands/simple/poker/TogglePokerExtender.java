package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.robotname.Robot;
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
        if (Robot.getInstance().getStateMachine().getCurrentState().getPokerState() == PokerState.UNPOKING)
            return PokerState.POKING;
        else
            return PokerState.UNPOKING;
    }
}
