package edu.greenblitz.knockdown.commands.simple.pneumatics;

import edu.greenblitz.knockdown.subsystems.Pneumatics;
import edu.greenblitz.utils.command.base.SubsystemCommand;

public class CompressorOn extends SubsystemCommand<Pneumatics> {

    /**
     * Stops the compressor for a set amount of time. Set to 0 to block forever.
     *
     * @param ms compressor stopRolling time
     */
    public CompressorOn(long ms) {
        super(Pneumatics.getInstance());

        if (ms > 0) {
            setTimeout(ms / 1000.0);
        }
    }

    @Override
    protected void atInit() {
        system.setCompressor(true);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }
}
