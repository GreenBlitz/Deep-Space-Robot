package edu.greenblitz.robotname.commands.simple.elevator;

import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.base.SubsystemCommand;

public class ReleaseElevatorBrake extends SubsystemCommand<Elevator> {
    public ReleaseElevatorBrake() {
        super(Elevator.getInstance());
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
