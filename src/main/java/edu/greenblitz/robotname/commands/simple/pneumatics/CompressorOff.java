package edu.greenblitz.robotname.commands.simple.pneumatics;

import edu.greenblitz.robotname.subsystems.Pneumatics;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.greenblitz.utils.sm.PokerState;
import edu.greenblitz.utils.sm.State;

public class CompressorOff extends SubsystemCommand<Pneumatics> {

    /**
     * Stops the compressor for a set amount of time. Set to 0 to block forever.
     * @param ms compressor stop time
     */
    public CompressorOff(long ms) {
        super(Pneumatics.getInstance());

        if (ms > 0) {
            setTimeout(ms / 1000.0);
        }
    }


    @Override
    public State getDeltaState() {
        return new State(null, null, null, null);
    }

    @Override
    protected void initialize() {
        system.setCompressor(false);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }
}
