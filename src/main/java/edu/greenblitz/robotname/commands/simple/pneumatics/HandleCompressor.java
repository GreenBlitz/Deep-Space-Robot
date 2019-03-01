package edu.greenblitz.robotname.commands.simple.pneumatics;

import edu.greenblitz.robotname.subsystems.Pneumatics;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.greenblitz.robotname.data.sm.State;

import java.util.Optional;

public class HandleCompressor extends SubsystemCommand<Pneumatics> {

    public HandleCompressor() {
        super(Pneumatics.getInstance());
    }

    @Override
    protected void initialize() {
        system.setCompressor(false);
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.empty();
    }

    @Override
    protected void execute() {
        if (system.isGameMode()) {
            if (system.getPressure() < system.getMinPressureReleased()) {
                system.setCompressor(true);
            } else if (system.getPressure() >= system.getMaxPressureReleased()) {
                system.setCompressor(false);
            }
        } else {
            system.setCompressor(true);
        }

    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}