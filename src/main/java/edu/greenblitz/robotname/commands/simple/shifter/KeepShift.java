package edu.greenblitz.robotname.commands.simple.shifter;

import edu.greenblitz.robotname.subsystems.Shifter;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.greenblitz.utils.sm.State;

import java.util.Optional;

/**
 * Orr karl hates anons.
 * <p>to have auto shift change not fuck up manual shift change</p>
 */
public class KeepShift extends SubsystemCommand<Shifter> {

    public KeepShift() {
        super(Shifter.getInstance());
    }

    @Override
    protected void atInit(){
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.empty();
    }
}
