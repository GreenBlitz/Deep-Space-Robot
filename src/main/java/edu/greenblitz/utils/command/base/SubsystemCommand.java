package edu.greenblitz.utils.command.base;

import edu.greenblitz.utils.command.GBSubsystem;

public abstract class SubsystemCommand<S extends GBSubsystem> extends GBCommand {
    protected final S system;

    public SubsystemCommand(S system) {
        super(system);
        this.system = system;
    }

    public SubsystemCommand(String name, S system) {
        super(name, system);
        this.system = system;
    }

    public SubsystemCommand(long ms, S system) {
        super(ms / 1000.0, system);
        this.system = system;
    }

    public SubsystemCommand(String name, long ms, S system) {
        super(name, ms / 1000.0, system);
        this.system = system;
    }
}
