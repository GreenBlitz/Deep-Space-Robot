package edu.greenblitz.utils.command;

import edu.wpi.first.wpilibj.command.Subsystem;

public class RequireCommand extends GBCommand {

    public RequireCommand(Subsystem subsystem) {
        requires(subsystem);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}