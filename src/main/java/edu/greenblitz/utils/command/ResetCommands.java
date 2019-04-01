package edu.greenblitz.utils.command;

import edu.greenblitz.utils.command.base.GBCommand;
import edu.wpi.first.wpilibj.command.Scheduler;

public class ResetCommands extends GBCommand {

    @Override
    protected void atInit() {
        Scheduler.getInstance().removeAll();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
