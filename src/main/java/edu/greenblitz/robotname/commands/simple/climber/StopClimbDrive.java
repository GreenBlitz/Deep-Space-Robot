package edu.greenblitz.robotname.commands.simple.climber;

import edu.greenblitz.robotname.subsystems.Climber;
import edu.greenblitz.utils.command.GBCommand;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.greenblitz.utils.sm.State;

import java.util.Optional;

public class StopClimbDrive extends SubsystemCommand<Climber.Wheels> {
    public StopClimbDrive() {
        super(Climber.getInstance().getWheels());
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.empty();
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
