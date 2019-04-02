package edu.greenblitz.knockdown.commands.simple.climber;

import edu.greenblitz.knockdown.subsystems.Climber;
import edu.greenblitz.utils.command.base.SubsystemCommand;

public class StopClimbDrive extends SubsystemCommand<Climber.Wheels> {
    public StopClimbDrive() {
        super(Climber.getInstance().getWheels());
    }

    @Override
    protected void atInit() {
        system.stop();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
