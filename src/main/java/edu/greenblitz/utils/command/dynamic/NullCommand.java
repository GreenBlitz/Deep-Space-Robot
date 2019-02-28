package edu.greenblitz.utils.command.dynamic;

import edu.greenblitz.utils.command.GBCommand;
import edu.wpi.first.wpilibj.command.Scheduler;

public class NullCommand extends GBCommand {

    @Override
    public synchronized void start() {
        Scheduler.getInstance().add(this);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {

    }
}
