package edu.greenblitz.robotname.commands.simple.elevator;

import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.TimedSubsystemCommand;

public abstract class AbstractNativeElevatorMove extends TimedSubsystemCommand<Elevator> {
    protected final double m_destination;
    private final int m_loopIdx;
    protected final long m_timeOnTarget;
    private long m_firstOnTarget;

    public AbstractNativeElevatorMove(double destination, int loopIdx, long timeOnTarget) {
        super(timeOnTarget, Elevator.getInstance());
        this.m_destination = destination;
        this.m_loopIdx = loopIdx;
        this.m_timeOnTarget = timeOnTarget;
    }

    abstract void startNativeMove();

    @Override
    protected final void initialize() {
        system.setMainLoopIdx(m_loopIdx);
        startNativeMove();
    }

    @Override
    protected final void end() {
        system.stop();
    }
}
