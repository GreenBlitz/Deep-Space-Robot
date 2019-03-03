package edu.greenblitz.utils.command;

import edu.greenblitz.utils.command.dynamic.DynamicCommand;
import edu.greenblitz.utils.sm.State;
import edu.wpi.first.wpilibj.command.Subsystem;

import java.util.Arrays;
import java.util.Optional;

public class DynamicRequire extends GBCommand {

    public DynamicRequire(Subsystem... subsystems) {
        super(DynamicCommand.class.getSimpleName() + ": " + Arrays.toString(subsystems));
        for (Subsystem s : subsystems)
            requires(s);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.empty();
    }
}