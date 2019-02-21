package edu.greenblitz.robotname.commands.simple.roller;

import edu.greenblitz.utils.command.SubsystemCommand;
import edu.greenblitz.robotname.subsystems.Roller;

public class CloseAndCollectCargo extends SubsystemCommand<Roller> {
    private static final long timeout = 1000;

    CloseAndCollectCargo() {
        super(Roller.getInstance());
        setTimeout(timeout);
    }

    @Override
    protected void initialize() {
        system.retract();
    }

    @Override
    protected void execute() {
        system.setPower(1);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        system.setPower(0);
    }
}