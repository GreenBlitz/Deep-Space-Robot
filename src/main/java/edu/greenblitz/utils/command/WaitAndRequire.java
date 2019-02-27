package edu.greenblitz.utils.command;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

public class WaitAndRequire extends CommandGroup {
    public WaitAndRequire(Subsystem subsystem) {
        addSequential(new WaitUntilClear(subsystem));
        addSequential(new DynamicRequire(subsystem));
    }
}
