package edu.greenblitz.robotname.commands.simple.pneumatics;

import edu.greenblitz.robotname.subsystems.Pneumatics;
import edu.greenblitz.utils.command.base.SubsystemCommand;
import edu.greenblitz.utils.sm.State;

import java.util.Optional;

public class HandleCompressor extends SubsystemCommand<Pneumatics> {

    public HandleCompressor() {
        super(Pneumatics.getInstance());
    }

    @Override
    protected void atInit() {
        system.setCompressor(false);
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.empty();
    }

    @Override
    protected void execute() {
        if (system.getPressure() < 70) {
            system.setCompressor(true);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}