package edu.greenblitz.robotname.commands.roller;

import edu.greenblitz.robotname.subsystems.Roller;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.wpi.first.wpilibj.command.Scheduler;

public class OpenAndCollectCargo extends SubsystemCommand<Roller> {

    public OpenAndCollectCargo() {
        super(Roller.getInstance());
    }

    @Override
    protected void initialize() {
        system.extend();
    }

    @Override
    protected void execute() {
        system.setPower(0.4);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Scheduler.getInstance().add(new CloseAndCollectCargo());
    }
}