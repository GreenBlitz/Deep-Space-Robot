package edu.greenblitz.knockdown.commands.simple.elevator;

import edu.greenblitz.knockdown.subsystems.Elevator;
import edu.greenblitz.utils.command.base.TimedSubsystemCommand;

public abstract class AbstractNativeElevatorMove extends TimedSubsystemCommand<Elevator> {
    private final double m_destination;
    private final int m_loopIdx;
    protected final long m_timeOnTarget;

    public AbstractNativeElevatorMove(double destination, int loopIdx, long timeOnTarget) {
        super(timeOnTarget, Elevator.getInstance());
        this.m_destination = destination;
        this.m_loopIdx = loopIdx;
        this.m_timeOnTarget = timeOnTarget;
    }

    abstract void startNativeMove(double level);

    @Override
    protected final void atInit() {
        system.setMainLoopIdx(m_loopIdx);
        system.brake(false);
        startNativeMove(m_destination);
    }

    @Override
    protected final void atEnd() {
        system.stop();
    }
}
