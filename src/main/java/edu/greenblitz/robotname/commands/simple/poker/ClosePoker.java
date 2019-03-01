package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.utils.command.TimedSubsystemCommand;
import edu.greenblitz.utils.sm.PokerState;
import edu.greenblitz.utils.sm.State;

public class ClosePoker extends TimedSubsystemCommand<Poker> {
    private static final long POKER_CLOSE_TIMEOUT = 1000;

    public ClosePoker() {
        super(POKER_CLOSE_TIMEOUT, Poker.getInstance());
    }

    @Override
    protected void initialize() {
        system.fullClose();
    }

    @Override
    protected boolean isFinished() {
        return system.isFullyClosed() && super.isFinished();
    }

    @Override
    public State getDeltaState() {
        return new State(null, null, PokerState.UNPOKING, null);
    }

}
