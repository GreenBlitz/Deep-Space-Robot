package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.utils.command.TimedSubsystemCommand;
import edu.greenblitz.utils.sm.PokerState;
import edu.greenblitz.utils.sm.State;

public class ExtendPoker extends TimedSubsystemCommand<Poker> {
    private static final long POKER_EXTEND_TIMEOUT = 1000;

    public ExtendPoker() {
        super(POKER_EXTEND_TIMEOUT, Poker.getInstance());
    }

    @Override
    protected void initialize() {
        system.extend(true);
    }

    @Override
    protected boolean isFinished() {
        return system.isRetracted();
    }

    @Override
    public State getDeltaState() {
        return new State(null, null, PokerState.POKING, null);
    }

}
