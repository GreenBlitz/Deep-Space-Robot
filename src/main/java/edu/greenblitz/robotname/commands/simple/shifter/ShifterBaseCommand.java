package edu.greenblitz.robotname.commands.simple.shifter;

import edu.greenblitz.robotname.subsystems.Shifter;
import edu.greenblitz.utils.command.base.SubsystemCommand;
import edu.greenblitz.utils.sm.State;

import java.util.Optional;

public abstract class ShifterBaseCommand extends SubsystemCommand<Shifter> {
    public ShifterBaseCommand() {
        super(Shifter.getInstance());
    }

    public ShifterBaseCommand(String name) {
        super(name, Shifter.getInstance());
    }

    public ShifterBaseCommand(long ms) {
        super(ms, Shifter.getInstance());
    }

    public ShifterBaseCommand(String name, long ms) {
        super(name, ms, Shifter.getInstance());
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.empty();
    }
}