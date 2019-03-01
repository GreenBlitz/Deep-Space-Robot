package edu.greenblitz.utils.command.dynamic;

import edu.greenblitz.utils.command.GBCommand;
import edu.greenblitz.utils.sm.State;

public class NullCommand extends GBCommand {
    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    public State getDeltaState() {
        return new State(null, null, null, null);
    }
}
