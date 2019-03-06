package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.robotname.Robot;
import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.utils.command.TimedSubsystemCommand;
import edu.greenblitz.utils.sm.PokerState;
import edu.greenblitz.utils.sm.State;

import java.util.Optional;

import static edu.greenblitz.robotname.subsystems.Poker.POKER_EXTEND_TIMEOUT;

public abstract class PokerBaseCommand extends TimedSubsystemCommand<Poker> {
    private boolean m_isDifferent;

    public PokerBaseCommand(long ms) {
        super(ms, Poker.getInstance());
    }

    public PokerBaseCommand() {
        this(POKER_EXTEND_TIMEOUT);
    }

    public PokerBaseCommand(String name, long ms) {
        super(name, ms, Poker.getInstance());
    }

    @Override
    protected final void initialize() {
        m_isDifferent = getNextState() == Robot.getInstance().getCurrentState().getPokerState();
        atInitialize();
    }

    protected void atInitialize() {}

    @Override
    protected boolean isFinished() {
        return super.isFinished() || m_isDifferent;
    }

    protected abstract PokerState getNextState();

    @Override
    public Optional<State> getDeltaState() {
        return Optional.of(new State(null, null, getNextState(), null));
    }
}
