package edu.greenblitz.robotname.commands.simple.shifter;

import edu.greenblitz.robotname.subsystems.Shifter;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.greenblitz.robotname.data.sm.State;

import java.util.Optional;

public class ToSpeed extends SubsystemCommand<Shifter> {

    public ToSpeed() {
        super(Shifter.getInstance());
    }

    @Override
    protected void execute() {
        system.setShift(Shifter.Gear.SPEED);
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.empty();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
