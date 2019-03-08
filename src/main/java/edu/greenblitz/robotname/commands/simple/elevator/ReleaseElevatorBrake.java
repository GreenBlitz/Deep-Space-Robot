package edu.greenblitz.robotname.commands.simple.elevator;

import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.greenblitz.utils.sm.State;
import edu.wpi.first.wpilibj.command.Command;

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
    protected void initialize() {
        system.brake(false);
    }
}
