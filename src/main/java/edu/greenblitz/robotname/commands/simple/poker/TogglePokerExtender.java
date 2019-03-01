package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.robotname.Robot;
import edu.greenblitz.robotname.data.sm.PokerState;

public class TogglePokerExtender extends PokerBaseCommand {
    private static final long POKER_TOGGLE_TIMEOUT = 1000;

    public TogglePokerExtender() {
        super(POKER_TOGGLE_TIMEOUT);
    }

    @Override
    protected void initialize() {
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
