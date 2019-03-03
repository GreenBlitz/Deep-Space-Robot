package edu.greenblitz.robotname.commands.simple;

import edu.greenblitz.utils.command.GBCommand;
import edu.greenblitz.utils.command.dynamic.DynamicCommand;
import edu.greenblitz.utils.sm.State;

import java.util.Optional;

public class DelayCommand extends GBCommand {

    public DelayCommand(long ms){
        super(ms);
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.empty();
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }
}
