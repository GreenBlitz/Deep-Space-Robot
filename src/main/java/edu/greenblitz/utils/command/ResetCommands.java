package edu.greenblitz.utils.command;

import edu.greenblitz.utils.command.base.GBCommand;
import edu.greenblitz.utils.sm.State;
import edu.wpi.first.wpilibj.command.Scheduler;

import java.util.Optional;

public class ResetCommands extends GBCommand {

    @Override
    protected void atInit() {
        Scheduler.getInstance().removeAll();
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.empty();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
