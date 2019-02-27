package edu.greenblitz.utils.command;

import edu.greenblitz.robotname.subsystems.TimedSubsystem;

public abstract class TimedSubsystemCommand<S extends TimedSubsystem> extends SubsystemCommand<S> {

    private long startTime, duration, waitDuration;

    private boolean isFirstRun;

    protected TimedSubsystemCommand(S system, long duration){
        this(system, duration, duration);
    }

    protected TimedSubsystemCommand(S system, long duration, long waitDuration) {
        super(system);
        this.duration = duration;
        this.waitDuration = waitDuration;
    }

    protected abstract TimedSubsystem[] requirements();

    /**
     * OVERRIDE ONLY, DON'T CALL
     */
    protected void timedInitialize() {
    }

    /**
     * OVERRIDE ONLY, DON'T CALL
     */
    protected void rawExecute() {
    }

    @Override
    protected final void initialize() {
        startTime = System.currentTimeMillis();
        this.system.setEndOfOperationsTime(startTime + duration);
        for (TimedSubsystem s : requirements())
            startTime = Math.max(startTime, s.getEndOfOperationsTime());
        for (TimedSubsystem s : requirements())
            s.setEndOfOperationsTime(startTime + waitDuration);
        isFirstRun = true;
    }

    @Override
    protected final void execute() {
        if (isFirstRun)
            timedInitialize();
        rawExecute();
    }

    @Override
    protected boolean isFinished() {
        return System.currentTimeMillis() > startTime + duration;
    }
}
