package edu.greenblitz.robotname.commands.simple.roller;

import edu.greenblitz.robotname.subsystems.Roller;
import edu.greenblitz.utils.command.TimedSubsystemCommand;
import edu.greenblitz.utils.sm.RollerState;
import edu.greenblitz.utils.sm.State;

import java.util.Optional;

import static edu.greenblitz.robotname.subsystems.Roller.ROLLER_STATE_TIMEOUT;

public abstract class RollerBaseCommand extends TimedSubsystemCommand<Roller> {

    public RollerBaseCommand() { this(ROLLER_STATE_TIMEOUT); }

    public RollerBaseCommand(long ms) {
        super(ms, Roller.getInstance());
    }

    public RollerBaseCommand(String name, long ms) {
        super(name, ms, Roller.getInstance());
    }

    protected abstract RollerState getNextState();

    @Override
    public Optional<State> getDeltaState() {
        return Optional.of(new State(null, getNextState(), null, null));
    }
}
