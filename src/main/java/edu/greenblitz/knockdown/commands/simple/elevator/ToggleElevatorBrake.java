package edu.greenblitz.knockdown.commands.simple.elevator;

import edu.greenblitz.knockdown.subsystems.Elevator;
import edu.greenblitz.utils.command.base.SubsystemCommand;

public class ToggleElevatorBrake extends SubsystemCommand<Elevator> {
    public ToggleElevatorBrake() {
        super(Elevator.getInstance());
    }

    @Override
    protected void atInit() {
        system.brake(!system.isBraking());
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}
