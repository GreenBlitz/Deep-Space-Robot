package edu.greenblitz.robotname.commands.simple.elevator;

import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.TimedSubsystemCommand;
import edu.greenblitz.utils.sm.ElevatorState;
import edu.greenblitz.utils.sm.State;

import java.util.Optional;

public abstract class AbstractNativeElevatorMove extends TimedSubsystemCommand<Elevator> {
    protected final double destination;
    private final int m_loopIdx;
    protected final long m_timeOnTarget;

    public AbstractNativeElevatorMove(double destination, int loopIdx, long timeOnTarget) {
        super(timeOnTarget, Elevator.getInstance());
        this.destination = destination;
        this.m_loopIdx = loopIdx;
        this.m_timeOnTarget = timeOnTarget;
    }

    abstract void startNativeMove();

    @Override
    protected final void initialize() {
        system.setMainLoopIdx(m_loopIdx);
        system.brake(false);
        startNativeMove();
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.of(new State(ElevatorState.closestTo(destination), null, null, null));
    }

    @Override
    protected final void end() {
        system.stop();
    }
}
