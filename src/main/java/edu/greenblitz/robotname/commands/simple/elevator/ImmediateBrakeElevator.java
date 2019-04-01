package edu.greenblitz.robotname.commands.simple.elevator;

import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.base.SubsystemCommand;

public class ImmediateBrakeElevator extends SubsystemCommand<Elevator> {

    public ImmediateBrakeElevator() {
        super(Elevator.getInstance());
    }

    @Override
    protected void atInit() {
        system.brake(true);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}