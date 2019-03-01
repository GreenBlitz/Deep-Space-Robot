package edu.greenblitz.robotname.commands.simple.kicker;

import edu.greenblitz.robotname.subsystems.Kicker;
import edu.greenblitz.utils.command.TimedSubsystemCommand;
import edu.greenblitz.robotname.data.sm.KickerState;
import edu.greenblitz.robotname.data.sm.State;

import java.util.Optional;

public abstract class KickerBaseCommand extends TimedSubsystemCommand<Kicker> {
    public KickerBaseCommand(long ms) {
        super(ms, Kicker.getInstance());
    }

    public KickerBaseCommand(String name, long ms) {
        super(name, ms, Kicker.getInstance());
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.of(new State(null, null, null, getNextState()));
    }

    protected abstract KickerState getNextState();
}
