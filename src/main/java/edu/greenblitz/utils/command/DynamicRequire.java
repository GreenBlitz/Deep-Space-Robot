package edu.greenblitz.utils.command;

import edu.wpi.first.wpilibj.command.Subsystem;

public class DynamicRequire extends GBCommand {

    public DynamicRequire(Subsystem... subsystem) {
        for (Subsystem s : subsystem)
            requires(s);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}