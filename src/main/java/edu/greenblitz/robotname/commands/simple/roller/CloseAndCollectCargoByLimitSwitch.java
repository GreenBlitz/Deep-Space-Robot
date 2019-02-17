package edu.greenblitz.robotname.commands.simple.roller;

import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.robotname.subsystems.Roller;
import edu.greenblitz.utils.command.SubsystemCommand;

public class CloseAndCollectCargoByLimitSwitch extends SubsystemCommand<Roller> {
    public CloseAndCollectCargoByLimitSwitch() {
        super(Roller.getInstance());
    }

    @Override
    protected void initialize() {
        system.retract();
    }

    @Override
    protected void execute() {
        system.setPower(0.4);
    }

    @Override
    protected boolean isFinished() {
        return Elevator.getInstance().isBallFullyIn();
    }

    @Override
    protected void end() {
        system.setPower(0);
    }
}