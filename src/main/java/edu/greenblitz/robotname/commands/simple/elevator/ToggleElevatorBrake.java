package edu.greenblitz.robotname.commands.simple.elevator;

import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.greenblitz.robotname.data.sm.State;

import java.util.Optional;

public class ToggleElevatorBrake extends SubsystemCommand<Elevator> {
    public ToggleElevatorBrake() {
        super(Elevator.getInstance());
    }

    @Override
    protected void initialize() {
        system.brake(!system.isBraking());
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.empty();
    }
}
