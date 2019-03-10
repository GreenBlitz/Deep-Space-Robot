package edu.greenblitz.robotname.commands.simple.pneumatics;

import edu.greenblitz.robotname.subsystems.Pneumatics;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.greenblitz.utils.sm.State;

import java.util.Optional;

public class HandleCompressor extends SubsystemCommand<Pneumatics> {

    public HandleCompressor() {
        super(Pneumatics.getInstance());
        setRunWhenDisabled(true);
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
            if (system.getPressure() < 50) {
                system.setCompressor(true);
            } else if (system.getPressure() > 80) {
                system.setCompressor(false);
            }
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}