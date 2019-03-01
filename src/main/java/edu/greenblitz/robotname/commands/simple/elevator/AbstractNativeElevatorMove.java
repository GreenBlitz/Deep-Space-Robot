package edu.greenblitz.robotname.commands.simple.elevator;

import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.TimedSubsystemCommand;
import edu.greenblitz.utils.sm.ElevatorState;
import edu.greenblitz.utils.sm.State;

import java.util.Optional;

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
    protected final void initialize() {
        system.setMainLoopIdx(m_loopIdx);
        system.brake(false);
        startNativeMove(m_destination);
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.of(new State(ElevatorState.closestTo(m_destination), null, null, null));
    }

    @Override
    protected final void end() {
        system.stop();
    }
}
