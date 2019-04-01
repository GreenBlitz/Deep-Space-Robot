package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.utils.command.base.TimedSubsystemCommand;
import edu.greenblitz.utils.sm.PokerState;

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
    protected boolean isFinished() {
        return super.isFinished() || m_isDifferent;
    }

    /**
     * @return the command's desired poker state
     */
    protected abstract PokerState getNextState();

}
