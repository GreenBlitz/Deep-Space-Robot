package edu.greenblitz.robotname.commands.simple.elevator;

import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.greenblitz.utils.sm.State;

import java.util.Optional;

public class ImmediateBrakeElevator extends SubsystemCommand<Elevator> {

    public ImmediateBrakeElevator() {
        super(Elevator.getInstance());
    }

    @Override
    protected void atInit() {
        system.brake(true);
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.empty();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}