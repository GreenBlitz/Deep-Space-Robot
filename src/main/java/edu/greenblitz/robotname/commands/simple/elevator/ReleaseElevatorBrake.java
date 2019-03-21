package edu.greenblitz.robotname.commands.simple.elevator;

import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.base.SubsystemCommand;
import edu.greenblitz.utils.sm.State;

import java.util.Optional;

public class ReleaseElevatorBrake extends SubsystemCommand<Elevator> {
    public ReleaseElevatorBrake() {
        super(Elevator.getInstance());
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.empty();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void atInit() {
        system.brake(false);
    }
}
