package edu.greenblitz.robotname.commands.simple.pneumatics;

import edu.greenblitz.robotname.subsystems.Pneumatics;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.greenblitz.robotname.data.sm.State;

import java.util.Optional;

public class CompressorOn extends SubsystemCommand<Pneumatics> {

    /**
     * Stops the compressor for a set amount of time. Set to 0 to block forever.
     * @param ms compressor stop time
     */
    public CompressorOn(long ms) {
        super(Pneumatics.getInstance());

        if (ms > 0) {
            setTimeout(ms / 1000.0);
        }
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.empty();
    }
    @Override
    protected void initialize() {
        system.setCompressor(true);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }
}
