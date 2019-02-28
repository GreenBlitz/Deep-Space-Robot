package edu.greenblitz.utils.command.dynamic;

import edu.greenblitz.utils.command.GBCommand;

public class NullCommand extends GBCommand {
    @Override
    protected boolean isFinished() {
        return true;
    }
}
