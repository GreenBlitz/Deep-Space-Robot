package edu.greenblitz.utils.command.dynamic;

import edu.greenblitz.utils.command.GBCommand;
import edu.greenblitz.robotname.data.sm.State;

import java.util.Optional;

public class NullCommand extends GBCommand {
    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.empty();
    }
}
