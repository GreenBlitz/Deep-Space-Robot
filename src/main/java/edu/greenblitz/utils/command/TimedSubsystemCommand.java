package edu.greenblitz.utils.command;

public abstract class TimedSubsystemCommand<S extends GBSubsystem> extends SubsystemCommand<S> {
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
