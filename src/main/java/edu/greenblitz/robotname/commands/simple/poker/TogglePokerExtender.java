package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.robotname.Robot;
import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.utils.command.TimedSubsystemCommand;
import edu.greenblitz.utils.sm.PokerState;
import edu.greenblitz.utils.sm.State;

public class TogglePokerExtender extends TimedSubsystemCommand<Poker> {
    private static final long POKER_TOGGLE_TIMEOUT = 1000;

    public TogglePokerExtender() {
        super(POKER_TOGGLE_TIMEOUT, Poker.getInstance());
    }

    @Override
    protected void initialize() {
        system.extend(!system.isExtended());
    }

    @Override
    public State getDeltaState() {
        return new State(null, null,
                Robot.getInstance().getStatus().getCurrentState().getM_PokerState() == PokerState.UNPOKING
                        ? PokerState.POKING : PokerState.UNPOKING,
                null);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
