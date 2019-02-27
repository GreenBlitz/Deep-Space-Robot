package edu.greenblitz.robotname.commands.simple.elevator;

import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.robotname.subsystems.Roller;
import edu.greenblitz.robotname.subsystems.TimedSubsystem;
import edu.greenblitz.utils.command.TimedSubsystemCommand;

public abstract class AbstractNativeElevatorMove extends TimedSubsystemCommand<Elevator> {
    protected final double m_destination;
    private final int m_loopIdx;
    protected final long m_timeOnTarget;
    private long m_firstOnTarget;

    private static final long ELEVATOR_MOVE_TIMEOUT = 5000;
    private static final long ELEVATOR_MOVE_DELAY = 5000;


    public AbstractNativeElevatorMove(double destination, int loopIdx, long timeOnTarget) {
        super(Elevator.getInstance(), ELEVATOR_MOVE_TIMEOUT, ELEVATOR_MOVE_DELAY);
        this.m_destination = destination;
        this.m_loopIdx = loopIdx;
        this.m_timeOnTarget = timeOnTarget;
    }

    @Override
    protected TimedSubsystem[] requirements() {
        return new TimedSubsystem[]{Roller.getInstance()};
    }

    abstract void startNativeMove();

    @Override
    protected final void timedInitialize() {
        system.setMainLoopIdx(m_loopIdx);
        startNativeMove();
    }

    @Override
    protected final void rawExecute() {
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
