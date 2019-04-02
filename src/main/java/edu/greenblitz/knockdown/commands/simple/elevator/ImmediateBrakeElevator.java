package edu.greenblitz.knockdown.commands.simple.elevator;

import edu.greenblitz.knockdown.subsystems.Elevator;
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