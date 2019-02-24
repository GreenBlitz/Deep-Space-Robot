package edu.greenblitz.utils.command;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class SubsystemCommand<S extends Subsystem> extends Command {
    protected final S system;

    public SubsystemCommand(S system) {
        super(system);
        this.system = system;
    }
}
