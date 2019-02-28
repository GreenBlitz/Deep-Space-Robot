package edu.greenblitz.robotname.commands.simple.elevator;

import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.SubsystemCommand;

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
}
