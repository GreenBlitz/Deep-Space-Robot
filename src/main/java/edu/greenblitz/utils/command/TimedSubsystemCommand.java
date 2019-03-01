package edu.greenblitz.utils.command;

import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class TimedSubsystemCommand<S extends Subsystem> extends SubsystemCommand<S> {
    public TimedSubsystemCommand(long ms, S system) {
        super(ms, system);
    }

    public TimedSubsystemCommand(String name, long ms, S system) {
        super(name, ms, system);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }
}
