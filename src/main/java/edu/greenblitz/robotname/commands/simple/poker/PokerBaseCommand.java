package edu.greenblitz.robotname.commands.simple.poker;

import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.utils.command.TimedSubsystemCommand;
import edu.greenblitz.utils.sm.PokerState;
import edu.greenblitz.utils.sm.State;

import java.util.Optional;

public abstract class PokerBaseCommand extends TimedSubsystemCommand<Poker> {
    public PokerBaseCommand(long ms) {
        super(ms, Poker.getInstance());
    }

    public PokerBaseCommand(String name, long ms) {
        super(name, ms, Poker.getInstance());
    }

    protected abstract PokerState getNextState();

    @Override
    public Optional<State> getDeltaState() {
        return Optional.of(new State(null, null, getNextState(), null));
    }
}
