package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.utils.command.TimedSubsystemCommand;
import edu.greenblitz.utils.sm.PokerState;
import edu.greenblitz.utils.sm.State;

public class RetractPoker extends TimedSubsystemCommand<Poker> {
    private static final long POKER_RETRACT_TIMEOUT = 1000;

    public RetractPoker() {
        super(POKER_RETRACT_TIMEOUT, Poker.getInstance());
    }

    @Override
    protected void initialize() {
        system.extend(false);
    }

    @Override
    public State getDeltaState() {
        return new State(null, null, PokerState.UNPOKING, null);
    }

    @Override
    protected boolean isFinished() {
        return system.isRetracted();
    }
}
