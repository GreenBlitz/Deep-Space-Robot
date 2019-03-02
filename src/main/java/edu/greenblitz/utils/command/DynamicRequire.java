package edu.greenblitz.utils.command;

import edu.greenblitz.utils.sm.State;
import edu.wpi.first.wpilibj.command.Subsystem;

import java.util.Optional;

public class DynamicRequire extends GBCommand {

    public DynamicRequire(Subsystem... subsystem) {
        for (Subsystem s : subsystem)
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