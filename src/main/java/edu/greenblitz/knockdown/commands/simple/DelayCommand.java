package edu.greenblitz.knockdown.commands.simple;

import edu.greenblitz.utils.command.base.GBCommand;

public class DelayCommand extends GBCommand {

    public DelayCommand(long ms){
        super(ms);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }
}
