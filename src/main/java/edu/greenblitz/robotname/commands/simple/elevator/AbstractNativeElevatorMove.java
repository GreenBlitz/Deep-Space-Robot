package edu.greenblitz.robotname.commands.simple.elevator;

import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.SubsystemCommand;

public abstract class AbstractNativeElevatorMove extends SubsystemCommand<Elevator> {
    protected final double m_destination;
    private final int m_loopIdx;
    protected final long m_timeOnTarget;
    private long m_firstOnTarget;


    public AbstractNativeElevatorMove(double destination, int loopIdx, long timeOnTarget) {
        super(Elevator.getInstance());
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
    protected final void execute() {
        if (system.getHeight() == m_destination) {
            if (m_firstOnTarget == -1) {
                m_firstOnTarget = System.currentTimeMillis();
            }
        } else {
            m_firstOnTarget = -1;
        }
    }

    @Override
    protected final boolean isFinished() {
        return m_firstOnTarget + m_timeOnTarget >= System.currentTimeMillis();
    }

    @Override
    protected final void end() {
        system.stop();
    }
}
